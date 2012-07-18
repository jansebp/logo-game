
package br.com.android.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import br.com.android.R;


public class ImageAdapter extends BaseAdapter {

	private Context  context;
	/*
	 * Arrays com as imagens
	 */
	public Integer[] nivel1 = { R.drawable.carrefour, R.drawable.volkswagen, R.drawable.bb,
	        R.drawable.nike, R.drawable.cea, R.drawable.hsbc, R.drawable.flamengo,
	        R.drawable.windows, R.drawable.bradesco, R.drawable.msn, R.drawable.eua,
	        R.drawable.yahoo, R.drawable.bic, R.drawable.ibm, R.drawable.sonyericsson,
	        R.drawable.bmw, R.drawable.tim, R.drawable.chevrolet, R.drawable.dell,
	        R.drawable.elmachips, R.drawable.tam, R.drawable.brasil, R.drawable.pizzahut,
	        R.drawable.mastercard, R.drawable.mcdonalds, R.drawable.directv, R.drawable.espn,
	        R.drawable.pepsi, R.drawable.bestbuy, R.drawable.adidas, R.drawable.bobs,
	        R.drawable.canada };


	/**
	 * Construtor da classe
	 * 
	 * @param c O contexto da aplicação
	 */
	public ImageAdapter( Context c ) {
		context = c;
	}


	public int getCount() {
		return nivel1.length;
	}


	public Object getItem( int posicao ) {
		return nivel1[posicao];
	}


	public long getItemId( int posicao ) {
		return 0;
	}


	public View getView( int posicao, View convertView, ViewGroup parent ) {
		ImageView imageView = new ImageView( context );
		imageView.setImageResource( nivel1[posicao] );
		imageView.setScaleType( ImageView.ScaleType.FIT_CENTER );
		imageView.setLayoutParams( new GridView.LayoutParams( 50, 50 ) );

		return imageView;
	}

}
