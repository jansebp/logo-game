
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
	 * Declaração dos Componentes Visuais
	 */
	private Button               btVoltar;
	private Button               btDicas;
	private Button               btQtdDicas;
	private TextView             txtPontuacao;
	private GridView             grid;

	private static JogarActivity instance = null;

	Perfil                       perfil;


	/**
	 * Método inicial da Aplicação
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
	 * Método que retorna uma instância da Aplicação
	 * 
	 * @return Uma instância da aplicação
	 */
	public static JogarActivity getInstance() {
		if ( instance == null )
			new JogarActivity().getApplication().onCreate();
		return JogarActivity.instance;
	}


	/**
	 * Método que cria e seta atributos dos Componentes Visuais e os associa ao seu correspondente no XML
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
	 * Método que declara, cria e trata eventos dos componentes visuais
	 */
	private void loadListeners() {
		/*
		 * Botão "Voltar"
		 */
		btVoltar.setOnClickListener( new OnClickListener() {
			public void onClick( View arg0 ) {
				finish();
			}
		} );
		//
		// /*
		// * Botão "Perfil"
		// */
		// btPerfil.setOnClickListener( new OnClickListener() {
		// public void onClick( View v ) {
		// startActivity( new Intent( AppWhatsTheLogo.this, PerfilActivity.class ) );
		// }
		// } );
		//
		// /*
		// * Botão "Help"
		// */
		// btHelp.setOnClickListener( new OnClickListener() {
		// public void onClick( View arg0 ) {
		// startActivity( new Intent( AppWhatsTheLogo.this, HelpActivity.class ) );
		// }
		// } );
		//
		// /*
		// * Botão "Sair"
		// */
		// btSair.setOnClickListener( new OnClickListener() {
		// public void onClick( View arg0 ) {
		// showMessageDialog( null, Utils.TipoDeDialog.SAIR );
		// }
		// } );
	}


	/**
	 * Método que atualiza os dados da tela quando a aplicação é resumida
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		atualizaTela();

		super.onResume();
	}


	/**
	 * Método que atualiza a pontuação e a quantidade de dicas de acordo com o progresso do jogador
	 */
	private void atualizaTela() {
		btQtdDicas.setText( "" + perfil.getQtdDicas() );
		txtPontuacao.setText( "" + perfil.getPontuacao() );
	}


	/**
	 * Método que carrega as preferências de perfil e retorna um perfil correspondente
	 * 
	 * @return Um perfil correspondente ao selecionado nas preferências de perfil
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
	 * Método que verifica se o jogador desbloqueou um nível
	 */
	public void verificaNivel() {
		carregaPreferencias();

		if ( perfil.getQtdAcertos() == 24 )
			Toast.makeText( getApplicationContext(), "Parabéns!\nVocê acaba de desbloquear um nível", Toast.LENGTH_SHORT );
		if ( perfil.getQtdAcertos() == 50 )
			Toast.makeText( getApplicationContext(), "Parabéns!\nVocê acaba de desbloquear um nível", Toast.LENGTH_SHORT );
		if ( perfil.getQtdAcertos() == 96 )
			Toast.makeText( getApplicationContext(), "Parabéns!\nVocê acaba de desbloquear um nível", Toast.LENGTH_SHORT );
	}
}
