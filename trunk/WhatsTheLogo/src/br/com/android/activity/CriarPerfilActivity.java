
package br.com.android.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.com.android.Perfil;
import br.com.android.R;
import br.com.android.DAO.PerfilDAO;
import br.com.android.commons.Utils;
import br.com.android.commons.Utils.TipoDeDialog;


public class CriarPerfilActivity extends Activity {
	/*
	 * Declaração dos Componentes Visuais
	 */
	private Button                     btVoltar;
	private Button                     btAddPerfil;
	private EditText                   edTxtAddPerfil;

	private static CriarPerfilActivity instance = null;


	/**
	 * Método inicial da Aplicação
	 */
	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.add_perfil );

		instance = this;

		initComponents();
		loadListeners();
	}


	/**
	 * Método que retorna uma instância da Aplicação
	 * 
	 * @return Uma instância da aplicação
	 */
	public static CriarPerfilActivity getInstance() {
		if ( instance == null )
			new CriarPerfilActivity().getApplication().onCreate();
		return CriarPerfilActivity.instance;
	}


	/**
	 * Método que cria e seta atributos dos Componentes Visuais e os associa ao seu correspondente no XML
	 */
	private void initComponents() {
		btVoltar = (Button) findViewById( R.id.btVoltar );
		btAddPerfil = (Button) findViewById( R.id.btAddPerfil );
		edTxtAddPerfil = (EditText) findViewById( R.id.edTxtAddPerfil );

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
		 * Botão "Adicionar Perfil"
		 */
		btAddPerfil.setOnClickListener( new OnClickListener() {
			public void onClick( View v ) {
				if ( adicionaPerfil() )
					finish();
				else
					;
			}
		} );
	}


	/**
	 * Método que cria um Perfil de Usuário e retorna uma instância
	 * 
	 * @param nome O nome do usuário que está criando o Perfil
	 * @return Uma instância do Perfil com todos os dados preenchidos
	 */
	private Perfil criarPerfil( String nome ) {
		Perfil perfil = new Perfil();
		perfil.setNomecompleto( nome );
		perfil.setNomeBD( nome );
		perfil.setPontuacao( 0 );
		perfil.setQtdAcertos( 0 );
		perfil.setQtdDicas( 0 );

		return perfil;
	}


	/**
	 * Método que adiciona um perfil no Banco de Dados
	 */
	private boolean adicionaPerfil() {
		PerfilDAO dao = new PerfilDAO( getApplicationContext() );
		String nome = edTxtAddPerfil.getText().toString();
		int retorno = dao.addPerfil( criarPerfil( nome ) );
		limparTela();

		if ( retorno == 0 ) {
			SharedPreferences preferences = getSharedPreferences( "perfilSelecionado", MODE_PRIVATE );
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString( "nome", nome.replaceAll( " ", "" ).toLowerCase().trim() );
			editor.commit();

			dao.close();

			return true;
		} else if ( retorno == 1 ) {
			showMessageDialog( "Não foi possível adicionar o Perfil.\nVerifique se o campo está vazio ou se o nome ("
			        + nome + ") já existe.", Utils.TipoDeDialog.ERRO );

			return false;
		} else {
			showMessageDialog( "Ocorreu um problema ao criar o Perfil de Usuário. Tente novamente.\nEm caso de reincidência, procure o desenvolvedor.", Utils.TipoDeDialog.ERRO_F );

			return false;
		}
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

		AlertDialog.Builder alertDialog = new AlertDialog.Builder( CriarPerfilActivity.this );
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


	/**
	 * Método que limpa a tela de criação de Perfil de Usuário
	 */
	private void limparTela() {
		edTxtAddPerfil.setText( "" );
	}
}