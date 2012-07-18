
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
	 * Declaração dos Componentes Visuais
	 */
	private Button                btVoltar;
	private Button                btCriarPerfil;
	private ListView              lista;

	private static PerfilActivity instance = null;


	/**
	 * Método inicial da Aplicação
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
	 * Método que retorna uma instância da Aplicação
	 * 
	 * @return Uma instância da aplicação
	 */
	public static PerfilActivity getInstance() {
		if ( instance == null )
			new PerfilActivity().getApplication().onCreate();
		return PerfilActivity.instance;
	}


	/**
	 * Método que cria e seta atributos dos Componentes Visuais e os associa ao seu correspondente no XML
	 */
	private void initComponents() {
		btVoltar = (Button) findViewById( R.id.btVoltar );
		btCriarPerfil = (Button) findViewById( R.id.btCriarPerfil );
		lista = (ListView) findViewById( R.id.lstPerfis );

	}


	/**
	 * Método que declara, cria e trata eventos dos componentes visuais
	 */
	private void loadListeners() {
		/*
		 * Botão "Voltar"
		 */
		btVoltar.setOnClickListener( new OnClickListener() {
			public void onClick( View v ) {
				finish();
			}
		} );
		/*
		 * Botão "Criar novo Perfil
		 */
		btCriarPerfil.setOnClickListener( new OnClickListener() {
			public void onClick( View v ) {
				startActivity( new Intent( PerfilActivity.this, CriarPerfilActivity.class ) );
			}
		} );
	}


	/**
	 * Método que carrega a lista de itens de uma Banco de Dados em um objeto do tipo ListView
	 * 
	 * @param lista A ListView onde os dados serão mostrados
	 */
	public void carregarLista( final ListView lista ) {
		PerfilDAO dao = new PerfilDAO( this );
		final List<Perfil> perfis = dao.getLista();

		ArrayAdapter<Perfil> adapter = new ArrayAdapter<Perfil>( this,
		        android.R.layout.simple_list_item_1, perfis );
		if ( adapter.isEmpty() ) {
			dao.delete(); // Deleta o Banco de Dados por segurança, já que a remoção de Perfis não exclui também a linha
			              // completa no Banco de Dados
			getSharedPreferences( "perfilSelecionado", MODE_PRIVATE ).edit().clear().commit(); // Deleta as preferências
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

				Toast.makeText( getApplicationContext(), "Você selecionou o Perfil:\nNome: "
				        + perfis.get( posicao ).getNomecompleto() + "\nPontuação: "
				        + perfis.get( posicao ).getPontuacao(), Toast.LENGTH_SHORT ).show();
				// showMessageDialog( "Você selecionou o Perfil:\nNome: "
				// + perfis.get( posicao ).getNomecompleto() + "\nPontuação: "
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
	 * Trata a interação com o Menu de Contexto
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
	 * Método que exibe na tela do usuário uma tela de diálogo
	 * 
	 * @param mensagem A mensagem que deseja que apareça na tela
	 * @param tipo O tipo de mensagem: <br>
	 *            <i>SAIDA</i> Exibe uma mensagem de saída<br>
	 *            <i>INFO</i> Exibe uma mensagem de informação<br>
	 *            <i>INFO_F</i> Exibe uma mensagem de informação e finaliza a Activity<br>
	 *            <i>ERRO</i> Exibe uma mensagem de erro
	 */
	public void showMessageDialog( String mensagem, TipoDeDialog tipo ) {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder( PerfilActivity.this );
		alertDialog.setTitle( tipo.name().substring( 0, 4 ) );

		switch ( tipo ) {
			case SAIR :
				alertDialog.setMessage( "Você realmente deseja sair?" ).setCancelable( false )
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
	 * Método executado quando a Activity é resumida
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		carregarLista( lista );
		super.onResume();
	}

}
