
package br.com.android.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.android.R;


public class HelpActivity extends Activity {
	/*
	 * Declaração dos Componentes Visuais
	 */
	private Button              btVoltar;

	private static HelpActivity instance = null;


	/**
	 * Método inicial da Aplicação
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.help );

		instance = this;

		initComponents();
		loadListeners();
	}


	/**
	 * Método que retorna uma instância da Aplicação
	 * 
	 * @return Uma instância da aplicação
	 */
	public static HelpActivity getInstance() {
		if ( instance == null )
			new HelpActivity().getApplication().onCreate();
		return HelpActivity.instance;
	}


	/**
	 * Método que cria e seta atributos dos Componentes Visuais e os associa ao seu correspondente no XML
	 */
	private void initComponents() {
		btVoltar = (Button) findViewById( R.id.btVoltar );
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
	}
}