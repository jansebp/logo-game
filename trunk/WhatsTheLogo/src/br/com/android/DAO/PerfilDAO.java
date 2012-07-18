
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
	 * @param context É o contexto da aplicação
	 * @param name É o nome da tabela do Banco de Dados
	 * @param factory Usado para a criação de objetos Cursor. Setar <i>null</i> que é o default
	 * @param version É a versão do Banco de Dados
	 */
	public PerfilDAO( Context context, String name, CursorFactory factory, int version ) {
		super( context, name, factory, version );
	}


	/**
	 * Construtor da classe. Chama o construtor da superclasse com parâmetros definidos no início
	 * 
	 * @param context É o contexto da aplicação
	 */
	public PerfilDAO( Context context ) {
		super( context, NOME_TABELA, null, VERSAO );
	}


	/**
	 * Método que cria uma tabela de um Banco de Dados
	 */
	@Override
	public void onCreate( SQLiteDatabase banco ) {
		banco.execSQL( TABELA_PERFIL );
	}


	/**
	 * Método que atualiza uma tabela de um Banco de Dados. Apaga a tabela já existente e a atualiza com uma
	 * inteiramente nova.
	 */
	@Override
	public void onUpgrade( SQLiteDatabase banco, int oldVersion, int newVersion ) {
		banco.execSQL( "DROP TABLE IF EXISTS " + PerfilDAO.NOME_TABELA );
		this.onCreate( banco );
	}


	/**
	 * Método que deleta uma tabela de um Banco de Dados
	 */
	public void delete() {
		getWritableDatabase().delete( NOME_TABELA, null, null );
	}


	/**
	 * Método que pega os valores de um Perfil de Usuário
	 * 
	 * @param perfil De onde quer pegar os valores
	 * @return Um objeto com os valores pegos do Perfil de Usuário passado
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
	 * Método que adiciona um Perfil de Usuário em uma tabela de um Banco de Dados
	 * 
	 * @param perfil O perfil a ser adicionado à tabela
	 * @return <i>0</i> se a inserção ocorreu com sucesso. <br>
	 *         <i>1</i> se não foi possível por já existir outro perfil com o mesmo nome ou por passar um nome vazio<br>
	 *         <i>-1</i> se não foi possível por qualquer outro motivo.
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
	 * Metódo que remove um Perfil de Usuário de uma tabela do Banco de Dados
	 * 
	 * @param perfil O perfil a ser removido
	 * @return <i>true</i> se a remoção ocorrer com sucesso<br>
	 *         <i>false</i> caso contrário
	 */
	public boolean removePerfil( Perfil perfil ) {
		if ( getWritableDatabase().delete( NOME_TABELA, COLUNA_NOME + "=?", new String[]{ ""
		        + perfil.getNomeBD() } ) == 0 )
			return false;
		return true;
	}


	/**
	 * Método que altera um Perfil de Usuário. Faz a busca no Banco de Dados pelo ID do Perfil de Usuário passado
	 * 
	 * @param perfil O perfil a ser alterado
	 */
	public void alterarPerfil( Perfil perfil ) {
		ContentValues valores = getValores( perfil );

		getWritableDatabase().update( NOME_TABELA, valores, COLUNA_ID + "=?", new String[]{ ""
		        + perfil.getId() } );
	}


	/**
	 * Método que retorna a lista de elementos de uma tabela de um Banco de Dados
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
	 * Método que retorna um perfil de acordo com um nome
	 * 
	 * @param nome O nome do perfil a ser pesquisado
	 * @return O perfil correspondente àquele nome
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
	 * Método que verifica se um Perfil de Usuário existe no Banco de Dados
	 * 
	 * @param nome O nome a ser pesquisado
	 * @return <i>true</i> se o Perfil de Usuário existir<br>
	 *         <i>false</i> caso contrário
	 */
	public boolean existePerfil( String nome ) {
		if ( getReadableDatabase()
		        .query( NOME_TABELA, COLUNAS, COLUNA_NOME + " LIKE ?", new String[]{ "" + nome }, null, null, null )
		        .moveToFirst() )
			return true;
		return false;
	}
}
