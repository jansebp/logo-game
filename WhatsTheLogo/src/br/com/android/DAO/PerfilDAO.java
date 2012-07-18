
package br.com.android.DAO;


import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.android.Perfil;


public class PerfilDAO extends SQLiteOpenHelper {
	/*
	 * Constantes
	 */
	private static final int    VERSAO              = 1;
	private static final String NOME_TABELA         = "PERFIL";
	private static final String COLUNA_ID           = "ID";
	private static final String COLUNA_NOME         = "NOME";
	private static final String COLUNA_NOMECOMPLETO = "NOMECOMPLETO";
	private static final String COLUNA_PONTUACAO    = "PONTUACAO";
	private static final String COLUNA_QTDACERTOS   = "QTDACERTOS";
	private static final String COLUNA_QTDDICAS     = "QTDDICAS";
	private static final String COLUNAS[]           = { COLUNA_ID, COLUNA_NOME,
	        COLUNA_NOMECOMPLETO, COLUNA_PONTUACAO, COLUNA_QTDACERTOS, COLUNA_QTDDICAS };
	private static final String TABELA_PERFIL       = "CREATE TABLE IF NOT EXISTS " + NOME_TABELA
	                                                        + " (" + COLUNA_ID
	                                                        + " INTEGER PRIMARY KEY, "
	                                                        + COLUNA_NOME + " TEXT, "
	                                                        + COLUNA_NOMECOMPLETO + " TEXT, "
	                                                        + COLUNA_PONTUACAO + " INT, "
	                                                        + COLUNA_QTDACERTOS + " INT, "
	                                                        + COLUNA_QTDDICAS + " INT);";


	/**
	 * Construtor da classe. Chama o construtor da superclasse
	 * 
	 * @param context � o contexto da aplica��o
	 * @param name � o nome da tabela do Banco de Dados
	 * @param factory Usado para a cria��o de objetos Cursor. Setar <i>null</i> que � o default
	 * @param version � a vers�o do Banco de Dados
	 */
	public PerfilDAO( Context context, String name, CursorFactory factory, int version ) {
		super( context, name, factory, version );
	}


	/**
	 * Construtor da classe. Chama o construtor da superclasse com par�metros definidos no in�cio
	 * 
	 * @param context � o contexto da aplica��o
	 */
	public PerfilDAO( Context context ) {
		super( context, NOME_TABELA, null, VERSAO );
	}


	/**
	 * M�todo que cria uma tabela de um Banco de Dados
	 */
	@Override
	public void onCreate( SQLiteDatabase banco ) {
		banco.execSQL( TABELA_PERFIL );
	}


	/**
	 * M�todo que atualiza uma tabela de um Banco de Dados. Apaga a tabela j� existente e a atualiza com uma
	 * inteiramente nova.
	 */
	@Override
	public void onUpgrade( SQLiteDatabase banco, int oldVersion, int newVersion ) {
		banco.execSQL( "DROP TABLE IF EXISTS " + PerfilDAO.NOME_TABELA );
		this.onCreate( banco );
	}


	/**
	 * M�todo que deleta uma tabela de um Banco de Dados
	 */
	public void delete() {
		getWritableDatabase().delete( NOME_TABELA, null, null );
	}


	/**
	 * M�todo que pega os valores de um Perfil de Usu�rio
	 * 
	 * @param perfil De onde quer pegar os valores
	 * @return Um objeto com os valores pegos do Perfil de Usu�rio passado
	 */
	public ContentValues getValores( Perfil perfil ) {
		ContentValues valores = new ContentValues();
		valores.put( COLUNA_NOME, perfil.getNomeBD() );
		valores.put( COLUNA_NOMECOMPLETO, perfil.getNomecompleto() );
		valores.put( COLUNA_PONTUACAO, perfil.getPontuacao() );
		valores.put( COLUNA_QTDACERTOS, perfil.getQtdAcertos() );
		valores.put( COLUNA_QTDDICAS, perfil.getQtdDicas() );

		return valores;
	}


	/**
	 * M�todo que adiciona um Perfil de Usu�rio em uma tabela de um Banco de Dados
	 * 
	 * @param perfil O perfil a ser adicionado � tabela
	 * @return <i>0</i> se a inser��o ocorreu com sucesso. <br>
	 *         <i>1</i> se n�o foi poss�vel por j� existir outro perfil com o mesmo nome ou por passar um nome vazio<br>
	 *         <i>-1</i> se n�o foi poss�vel por qualquer outro motivo.
	 */
	public int addPerfil( Perfil perfil ) {
		ContentValues valores = getValores( perfil );
		if ( perfil.getNomeBD().equals( "" ) || existePerfil( perfil.getNomeBD() ) )
			return 1;
		else if ( getWritableDatabase().insert( NOME_TABELA, null, valores ) >= 0 )
			return 0;
		else
			return -1;
	}


	/**
	 * Met�do que remove um Perfil de Usu�rio de uma tabela do Banco de Dados
	 * 
	 * @param perfil O perfil a ser removido
	 * @return <i>true</i> se a remo��o ocorrer com sucesso<br>
	 *         <i>false</i> caso contr�rio
	 */
	public boolean removePerfil( Perfil perfil ) {
		if ( getWritableDatabase().delete( NOME_TABELA, COLUNA_NOME + "=?", new String[]{ ""
		        + perfil.getNomeBD() } ) == 0 )
			return false;
		return true;
	}


	/**
	 * M�todo que altera um Perfil de Usu�rio. Faz a busca no Banco de Dados pelo ID do Perfil de Usu�rio passado
	 * 
	 * @param perfil O perfil a ser alterado
	 */
	public void alterarPerfil( Perfil perfil ) {
		ContentValues valores = getValores( perfil );

		getWritableDatabase().update( NOME_TABELA, valores, COLUNA_ID + "=?", new String[]{ ""
		        + perfil.getId() } );
	}


	/**
	 * M�todo que retorna a lista de elementos de uma tabela de um Banco de Dados
	 * 
	 * @return a Lista de elementos
	 */
	public List<Perfil> getLista() {
		Cursor c = getWritableDatabase().query( NOME_TABELA, COLUNAS, null, null, null, null, null );
		List<Perfil> lista = new ArrayList<Perfil>();
		while ( c.moveToNext() ) {
			Perfil perfil = new Perfil();
			perfil.setId( c.getInt( 0 ) );
			perfil.setNomeBD( c.getString( 1 ) );
			perfil.setNomecompleto( c.getString( 2 ) );
			perfil.setPontuacao( c.getInt( 3 ) );
			perfil.setQtdAcertos( c.getInt( 4 ) );
			perfil.setQtdDicas( c.getInt( 5 ) );

			lista.add( perfil );
		}
		c.close();

		return lista;
	}


	/**
	 * M�todo que retorna um perfil de acordo com um nome
	 * 
	 * @param nome O nome do perfil a ser pesquisado
	 * @return O perfil correspondente �quele nome
	 */
	public Perfil getPerfilByNome( String nome ) {
		Cursor c = getReadableDatabase()
		        .query( NOME_TABELA, COLUNAS, COLUNA_NOME + " LIKE ?", new String[]{ "" + nome }, null, null, null );
		c.moveToFirst();
		Perfil p = new Perfil();
		p.setId( c.getInt( 0 ) );
		p.setNomeBD( c.getString( 1 ) );
		p.setNomecompleto( c.getString( 2 ) );
		p.setPontuacao( c.getInt( 3 ) );
		p.setQtdAcertos( c.getInt( 4 ) );
		p.setQtdDicas( c.getInt( 5 ) );

		c.close();

		return p;
	}


	/**
	 * M�todo que verifica se um Perfil de Usu�rio existe no Banco de Dados
	 * 
	 * @param nome O nome a ser pesquisado
	 * @return <i>true</i> se o Perfil de Usu�rio existir<br>
	 *         <i>false</i> caso contr�rio
	 */
	public boolean existePerfil( String nome ) {
		if ( getReadableDatabase()
		        .query( NOME_TABELA, COLUNAS, COLUNA_NOME + " LIKE ?", new String[]{ "" + nome }, null, null, null )
		        .moveToFirst() )
			return true;
		return false;
	}
}
