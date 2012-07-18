
package br.com.android.activity;


import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import br.com.android.Perfil;
import br.com.android.R;
import br.com.android.DAO.PerfilDAO;
import br.com.android.commons.Utils.TipoDeDialog;


public class PerfilActivity extends Activity {
	/*
	 * Declara��o dos Componentes Visuais
	 */
	private Button                btVoltar;
	private Button                btCriarPerfil;
	private ListView              lista;

	private static PerfilActivity instance = null;


	/**
	 * M�todo inicial da Aplica��o
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.perfil );

		instance = this;

		initComponents();
		loadListeners();
	}


	/**
	 * M�todo que retorna uma inst�ncia da Aplica��o
	 * 
	 * @return Uma inst�ncia da aplica��o
	 */
	public static PerfilActivity getInstance() {
		if ( instance == null )
			new PerfilActivity().getApplication().onCreate();
		return PerfilActivity.instance;
	}


	/**
	 * M�todo que cria e seta atributos dos Componentes Visuais e os associa ao seu correspondente no XML
	 */
	private void initComponents() {
		btVoltar = (Button) findViewById( R.id.btVoltar );
		btCriarPerfil = (Button) findViewById( R.id.btCriarPerfil );
		lista = (ListView) findViewById( R.id.lstPerfis );

	}


	/**
	 * M�todo que declara, cria e trata eventos dos componentes visuais
	 */
	private void loadListeners() {
		/*
		 * Bot�o "Voltar"
		 */
		btVoltar.setOnClickListener( new OnClickListener() {
			public void onClick( View v ) {
				finish();
			}
		} );
		/*
		 * Bot�o "Criar novo Perfil
		 */
		btCriarPerfil.setOnClickListener( new OnClickListener() {
			public void onClick( View v ) {
				startActivity( new Intent( PerfilActivity.this, CriarPerfilActivity.class ) );
			}
		} );
	}


	/**
	 * M�todo que carrega a lista de itens de uma Banco de Dados em um objeto do tipo ListView
	 * 
	 * @param lista A ListView onde os dados ser�o mostrados
	 */
	public void carregarLista( final ListView lista ) {
		PerfilDAO dao = new PerfilDAO( this );
		final List<Perfil> perfis = dao.getLista();

		ArrayAdapter<Perfil> adapter = new ArrayAdapter<Perfil>( this,
		        android.R.layout.simple_list_item_1, perfis );
		if ( adapter.isEmpty() ) {
			dao.delete(); // Deleta o Banco de Dados por seguran�a, j� que a remo��o de Perfis n�o exclui tamb�m a linha
			              // completa no Banco de Dados
			getSharedPreferences( "perfilSelecionado", MODE_PRIVATE ).edit().clear().commit(); // Deleta as prefer�ncias
																							   // de Perfil
		}
		dao.close();

		lista.setAdapter( adapter );
		lista.setClickable( true );
		lista.setOnItemClickListener( new OnItemClickListener() {

			public void onItemClick( AdapterView<?> adapter, View view, int posicao, long id ) {
				SharedPreferences preferences = getSharedPreferences( "perfilSelecionado", MODE_PRIVATE );
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString( "nome", perfis.get( posicao ).getNomeBD() );
				editor.commit();

				Toast.makeText( getApplicationContext(), "Voc� selecionou o Perfil:\nNome: "
				        + perfis.get( posicao ).getNomecompleto() + "\nPontua��o: "
				        + perfis.get( posicao ).getPontuacao(), Toast.LENGTH_SHORT ).show();
				// showMessageDialog( "Voc� selecionou o Perfil:\nNome: "
				// + perfis.get( posicao ).getNomecompleto() + "\nPontua��o: "
				// + perfis.get( posicao ).getPontuacao(), TipoDeDialog.INFO_F );
				finish();
			}
		} );
		lista.setLongClickable( true );
		lista.setOnItemLongClickListener( new OnItemLongClickListener() {
			public boolean
			        onItemLongClick( AdapterView<?> adapter, View view, int posicao, long id ) {
				SharedPreferences preferences = getSharedPreferences( "perfilSelecionado", MODE_PRIVATE );
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString( "nome", perfis.get( posicao ).getNomeBD() );
				editor.commit();
				registerForContextMenu( lista );
				return false;
			}
		} );
	}


	/**
	 * Cria um Menu de Contexto
	 * 
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu, android.view.View,
	 *      android.view.ContextMenu.ContextMenuInfo)
	 */
	@Override
	public void onCreateContextMenu( ContextMenu menu, View v, ContextMenuInfo menuInfo ) {
		menu.add( 0, 0, 0, "Deletar" );

		super.onCreateContextMenu( menu, v, menuInfo );
	}


	/**
	 * Trata a intera��o com o Menu de Contexto
	 * 
	 * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onContextItemSelected( MenuItem item ) {
		SharedPreferences preferences = getSharedPreferences( "perfilSelecionado", MODE_PRIVATE );
		final String nome = preferences.getString( "nome", null );
		PerfilDAO dao = new PerfilDAO( this );
		if ( item.getItemId() == 0 ) {
			dao.removePerfil( dao.getPerfilByNome( nome ) );
			carregarLista( lista );
		}
		return super.onContextItemSelected( item );
	}


	/**
	 * M�todo que exibe na tela do usu�rio uma tela de di�logo
	 * 
	 * @param mensagem A mensagem que deseja que apare�a na tela
	 * @param tipo O tipo de mensagem: <br>
	 *            <i>SAIDA</i> Exibe uma mensagem de sa�da<br>
	 *            <i>INFO</i> Exibe uma mensagem de informa��o<br>
	 *            <i>INFO_F</i> Exibe uma mensagem de informa��o e finaliza a Activity<br>
	 *            <i>ERRO</i> Exibe uma mensagem de erro
	 */
	public void showMessageDialog( String mensagem, TipoDeDialog tipo ) {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder( PerfilActivity.this );
		alertDialog.setTitle( tipo.name().substring( 0, 4 ) );

		switch ( tipo ) {
			case SAIR :
				alertDialog.setMessage( "Voc� realmente deseja sair?" ).setCancelable( false )
				        .setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
					        public void onClick( DialogInterface dialog, int which ) {
						        finish();
					        }
				        } ).setNegativeButton( "No", new DialogInterface.OnClickListener() {
					        public void onClick( DialogInterface dialog, int which ) {
						        dialog.cancel();
					        }
				        } );
				alertDialog.setIcon( R.drawable.ic_sair );
				break;

			case INFO :
				alertDialog.setMessage( mensagem ).setCancelable( false )
				        .setNeutralButton( "OK", new DialogInterface.OnClickListener() {
					        public void onClick( DialogInterface dialog, int which ) {
						        dialog.cancel();
					        }
				        } );
				alertDialog.setIcon( R.drawable.ic_info );
				break;

			case INFO_F :
				alertDialog.setMessage( mensagem ).setCancelable( false )
				        .setNeutralButton( "OK", new DialogInterface.OnClickListener() {
					        public void onClick( DialogInterface dialog, int which ) {
						        finish();
					        }
				        } );
				alertDialog.setIcon( R.drawable.ic_info );
				break;
			case ERRO :
				alertDialog.setMessage( mensagem ).setCancelable( false )
				        .setNeutralButton( "OK", new DialogInterface.OnClickListener() {
					        public void onClick( DialogInterface dialog, int which ) {
						        dialog.cancel();
					        }
				        } );
				alertDialog.setIcon( R.drawable.ic_erro );
				break;
			case ERRO_F :
				alertDialog.setMessage( mensagem ).setCancelable( false )
				        .setNeutralButton( "OK", new DialogInterface.OnClickListener() {
					        public void onClick( DialogInterface dialog, int which ) {
						        finish();
					        }
				        } );
				alertDialog.setIcon( R.drawable.ic_erro );
				break;
			default :
				break;
		}
		alertDialog.show();
	}


	/*
	 * M�todo executado quando a Activity � resumida
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		carregarLista( lista );
		super.onResume();
	}

}
