
package br.com.android.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import br.com.android.R;
import br.com.android.commons.Utils;
import br.com.android.commons.Utils.TipoDeDialog;


public class AppActivity extends Activity {
	/*
	 * Declara��o dos Componentes Visuais
	 */
	private Button                 btJogar;
	private Button                 btPerfil;
	private Button                 btSair;
	private ImageView              btHelp;

	private static AppActivity instance = null;


	/**
	 * M�todo inicial da Aplica��o
	 */
	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.main );

		instance = this;

		initComponents();
		loadListeners();
	}


	/**
	 * M�todo que retorna uma inst�ncia da Aplica��o
	 * 
	 * @return Uma inst�ncia da aplica��o
	 */
	public static AppActivity getInstance() {
		if ( instance == null )
			new AppActivity().getApplication().onCreate();
		return AppActivity.instance;
	}


	/**
	 * M�todo que cria e seta atributos dos Componentes Visuais e os associa ao seu correspondente no XML
	 */
	private void initComponents() {
		btJogar = (Button) findViewById( R.id.btJogar );
		btPerfil = (Button) findViewById( R.id.btPerfil );
		btSair = (Button) findViewById( R.id.btSair );
		btHelp = (ImageView) findViewById( R.id.btHelp );
	}


	/**
	 * M�todo que declara, cria e trata eventos dos componentes visuais
	 */
	private void loadListeners() {
		/*
		 * Bot�o "Jogar"
		 */
		btJogar.setOnClickListener( new OnClickListener() {
			public void onClick( View v ) {
				if ( existePerfilSelecionado() )
					startActivity( new Intent( AppActivity.this, NivelActivity.class ) );
				else {
					Toast.makeText( getApplicationContext(), "Voc� deve criar um Perfil de Usu�rio antes de come�ar a jogar", Toast.LENGTH_LONG )
					        .show();
					startActivity( new Intent( AppActivity.this, CriarPerfilActivity.class ) );
				}
			}
		} );

		/*
		 * Bot�o "Perfil"
		 */
		btPerfil.setOnClickListener( new OnClickListener() {
			public void onClick( View v ) {
				startActivity( new Intent( AppActivity.this, PerfilActivity.class ) );
			}
		} );

		/*
		 * Bot�o "Help"
		 */
		btHelp.setOnClickListener( new OnClickListener() {
			public void onClick( View arg0 ) {
				startActivity( new Intent( AppActivity.this, HelpActivity.class ) );
			}
		} );

		/*
		 * Bot�o "Sair"
		 */
		btSair.setOnClickListener( new OnClickListener() {
			public void onClick( View arg0 ) {
				showMessageDialog( null, Utils.TipoDeDialog.SAIR );
			}
		} );
	}


	/**
	 * M�todo que trata o bot�o de "Voltar" do Celular
	 */
	@Override
	public boolean onKeyDown( int keyCode, KeyEvent event ) {

		if ( keyCode == KeyEvent.KEYCODE_BACK )
			showMessageDialog( null, Utils.TipoDeDialog.SAIR );

		return super.onKeyDown( keyCode, event );
	}


	/**
	 * Verifica se algum Perfil de Usu�rio foi selecionado
	 * 
	 * @return <i>true</i> Se algum Perfil de Usu�rio foi selecionado<br>
	 *         <i>false</i> Caso contr�rio
	 */
	private boolean existePerfilSelecionado() {
		SharedPreferences preferences = getSharedPreferences( "perfilSelecionado", MODE_PRIVATE );
		if ( (preferences.getString( "nome", null )) == null )
			return false;
		return true;

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

		AlertDialog.Builder alertDialog = new AlertDialog.Builder( AppActivity.this );
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
}