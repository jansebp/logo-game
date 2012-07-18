
package br.com.android.activity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import br.com.android.Perfil;
import br.com.android.R;
import br.com.android.DAO.PerfilDAO;


public class NivelActivity extends Activity {
	/*
	 * Declara��o dos Componentes Visuais
	 */
	private Button               btNivel1;
	private Button               btNivel2;
	private Button               btNivel3;
	private Button               btNivel4;
	private Button               btVoltar;

	private static NivelActivity instance = null;

	private Perfil               perfil;


	/**
	 * M�todo inicial da Aplica��o
	 */
	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.nivel );

		instance = this;

		initComponents();
		loadListeners();

		if ( perfil != null )
			atualizaNivel();
	}


	/**
	 * M�todo que retorna uma inst�ncia da Aplica��o
	 * 
	 * @return Uma inst�ncia da aplica��o
	 */
	public static NivelActivity getInstance() {
		if ( instance == null )
			new NivelActivity().getApplication().onCreate();
		return NivelActivity.instance;
	}


	/**
	 * M�todo que cria e seta atributos dos Componentes Visuais e os associa ao seu correspondente no XML
	 */
	private void initComponents() {
		btNivel1 = (Button) findViewById( R.id.btNivel1 );
		btNivel2 = (Button) findViewById( R.id.btNivel2 );
		btNivel3 = (Button) findViewById( R.id.btNivel3 );
		btNivel4 = (Button) findViewById( R.id.btNivel4 );
		btVoltar = (Button) findViewById( R.id.btVoltar );
	}


	/**
	 * M�todo que atualiza os n�veis que o jogador tem acesso
	 */
	private void atualizaNivel() {
		Toast.makeText( getApplicationContext(), perfil.getQtdAcertos() + "\n"
		        + perfil.getNomecompleto(), Toast.LENGTH_SHORT ).show();
		if ( perfil.getQtdAcertos() < 24 )
			btNivel2.setEnabled( false );
		if ( perfil.getQtdAcertos() < 50 )
			btNivel3.setEnabled( false );
		if ( perfil.getQtdAcertos() < 96 )
			btNivel4.setEnabled( false );
	}


	/**
	 * M�todo que declara, cria e trata eventos dos componentes visuais
	 */
	private void loadListeners() {
		/*
		 * Bot�o "N�vel 1"
		 */
		btNivel1.setOnClickListener( new OnClickListener() {
			public void onClick( View v ) {
				SharedPreferences preferences = getSharedPreferences( "nivelSelecionado", MODE_PRIVATE );
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString( "nivel", "nivel1" );
				editor.commit();

				startActivity( new Intent( NivelActivity.this, JogarActivity.class ) );
			}
		} );

		/*
		 * Bot�o "N�vel 2"
		 */
		btNivel2.setOnClickListener( new OnClickListener() {
			public void onClick( View v ) {
				SharedPreferences preferences = getSharedPreferences( "nivelSelecionado", MODE_PRIVATE );
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString( "nivel", "nivel2" );
				editor.commit();
				// startActivity( new Intent( NivelActivity.this, JogarActivity.class ) );
			}
		} );

		/*
		 * Bot�o "N�vel 3"
		 */
		btNivel3.setOnClickListener( new OnClickListener() {
			public void onClick( View arg0 ) {
				SharedPreferences preferences = getSharedPreferences( "nivelSelecionado", MODE_PRIVATE );
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString( "nivel", "nivel3" );
				editor.commit();
				// startActivity( new Intent( NivelActivity.this, JogarActivity.class ) );
			}
		} );

		/*
		 * Bot�o "N�vel 4"
		 */
		btNivel3.setOnClickListener( new OnClickListener() {
			public void onClick( View arg0 ) {
				SharedPreferences preferences = getSharedPreferences( "nivelSelecionado", MODE_PRIVATE );
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString( "nivel", "nivel4" );
				editor.commit();
				// startActivity( new Intent( NivelActivity.this, JogarActivity.class ) );
			}
		} );

		/*
		 * Bot�o "Voltar"
		 */
		btVoltar.setOnClickListener( new OnClickListener() {
			public void onClick( View arg0 ) {
				finish();
			}
		} );
	}


	/**
	 * M�todo que carrega as prefer�ncias de perfil e retorna um perfil correspondente
	 * 
	 * @return Um perfil correspondente ao selecionado nas prefer�ncias de perfil
	 */
	private Perfil carregaPreferencias() {
		SharedPreferences preferences = getSharedPreferences( "perfilSelecionado", MODE_PRIVATE );
		final String nome = preferences.getString( "nome", null );
		PerfilDAO dao = new PerfilDAO( this );
		perfil = dao.getPerfilByNome( nome );
		dao.close();

		return perfil;
	}


	/*
	 * M�todo executado quando a Activity � resumida
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {

		carregaPreferencias();
		atualizaNivel();

		super.onResume();
	}
}
