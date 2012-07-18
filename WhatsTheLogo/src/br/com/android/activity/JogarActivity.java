
package br.com.android.activity;


import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.android.Perfil;
import br.com.android.R;
import br.com.android.DAO.PerfilDAO;
import br.com.android.adapter.ImageAdapter;


public class JogarActivity extends Activity {
	/*
	 * Declara��o dos Componentes Visuais
	 */
	private Button               btVoltar;
	private Button               btDicas;
	private Button               btQtdDicas;
	private TextView             txtPontuacao;
	private GridView             grid;

	private static JogarActivity instance = null;

	Perfil                       perfil;


	/**
	 * M�todo inicial da Aplica��o
	 */
	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.jogar );

		instance = this;

		SharedPreferences preferences = getSharedPreferences( "perfilSelecionado", MODE_PRIVATE );
		final String nome = preferences.getString( "nome", null );
		PerfilDAO dao = new PerfilDAO( this );
		perfil = dao.getPerfilByNome( nome );
		perfil.setPontuacao( 1000 );
		dao.alterarPerfil( perfil );
		dao.close();

		initComponents();
		loadListeners();
	}


	/**
	 * M�todo que retorna uma inst�ncia da Aplica��o
	 * 
	 * @return Uma inst�ncia da aplica��o
	 */
	public static JogarActivity getInstance() {
		if ( instance == null )
			new JogarActivity().getApplication().onCreate();
		return JogarActivity.instance;
	}


	/**
	 * M�todo que cria e seta atributos dos Componentes Visuais e os associa ao seu correspondente no XML
	 */
	private void initComponents() {
		btVoltar = (Button) findViewById( R.id.btVoltar );
		btDicas = (Button) findViewById( R.id.btDicas );
		btQtdDicas = (Button) findViewById( R.id.btQtdDicas );
		txtPontuacao = (TextView) findViewById( R.id.txtPontuacao );
		grid = (GridView) findViewById( R.id.grid );

		grid.setAdapter( new ImageAdapter( this ) );
		btDicas.setTextColor( Color.RED );
		btDicas.setEnabled( false );
		btQtdDicas.setEnabled( false );

		atualizaTela();
	}


	/**
	 * M�todo que declara, cria e trata eventos dos componentes visuais
	 */
	private void loadListeners() {
		/*
		 * Bot�o "Voltar"
		 */
		btVoltar.setOnClickListener( new OnClickListener() {
			public void onClick( View arg0 ) {
				finish();
			}
		} );
		//
		// /*
		// * Bot�o "Perfil"
		// */
		// btPerfil.setOnClickListener( new OnClickListener() {
		// public void onClick( View v ) {
		// startActivity( new Intent( AppWhatsTheLogo.this, PerfilActivity.class ) );
		// }
		// } );
		//
		// /*
		// * Bot�o "Help"
		// */
		// btHelp.setOnClickListener( new OnClickListener() {
		// public void onClick( View arg0 ) {
		// startActivity( new Intent( AppWhatsTheLogo.this, HelpActivity.class ) );
		// }
		// } );
		//
		// /*
		// * Bot�o "Sair"
		// */
		// btSair.setOnClickListener( new OnClickListener() {
		// public void onClick( View arg0 ) {
		// showMessageDialog( null, Utils.TipoDeDialog.SAIR );
		// }
		// } );
	}


	/**
	 * M�todo que atualiza os dados da tela quando a aplica��o � resumida
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		atualizaTela();

		super.onResume();
	}


	/**
	 * M�todo que atualiza a pontua��o e a quantidade de dicas de acordo com o progresso do jogador
	 */
	private void atualizaTela() {
		btQtdDicas.setText( "" + perfil.getQtdDicas() );
		txtPontuacao.setText( "" + perfil.getPontuacao() );
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


	/**
	 * M�todo que verifica se o jogador desbloqueou um n�vel
	 */
	public void verificaNivel() {
		carregaPreferencias();

		if ( perfil.getQtdAcertos() == 24 )
			Toast.makeText( getApplicationContext(), "Parab�ns!\nVoc� acaba de desbloquear um n�vel", Toast.LENGTH_SHORT );
		if ( perfil.getQtdAcertos() == 50 )
			Toast.makeText( getApplicationContext(), "Parab�ns!\nVoc� acaba de desbloquear um n�vel", Toast.LENGTH_SHORT );
		if ( perfil.getQtdAcertos() == 96 )
			Toast.makeText( getApplicationContext(), "Parab�ns!\nVoc� acaba de desbloquear um n�vel", Toast.LENGTH_SHORT );
	}
}
