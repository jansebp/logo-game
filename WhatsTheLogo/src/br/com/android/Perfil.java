
package br.com.android;


public class Perfil {

	private String        nome;
	private String        nomecompleto;
	private int           id;
	private int           pontuacao;
	private int           qtdAcertos;
	private int           qtdDicas;

	private static Perfil instance = null;


	/**
	 * Construtor. Cria um Perfil de Usu�rio do in�cio, ou seja, com os campos <i>pontua��o</i>, <i>quantidade de
	 * acertos</i> e <i>dicas</> zerados, e com o nome digitado pelo jogador
	 * 
	 * @param nomeCompleto O nome do Perfil de Usu�rio
	 */
	public Perfil( String nomeCompleto ) {
		setNomecompleto( nome );
		setNomeBD( nome );
		setPontuacao( 0 );
		setQtdAcertos( 0 );
		setQtdDicas( 0 );
	}


	/**
	 * Construtor
	 */
	public Perfil() {
		setPontuacao( 0 );
		setQtdAcertos( 0 );
		setQtdDicas( 0 );
	}


	/**
	 * M�todo que retorna uma inst�ncia de um Perfil de Usu�rio
	 * 
	 * @return Uma inst�ncia de um Perfil de Usu�rio
	 */
	public static Perfil getInstance() {
		if ( instance == null )
			instance = new Perfil();
		return instance;
	}


	/**
	 * M�todo que retorna a String de refer�ncia de um Perfil no Banco de Dados
	 * 
	 * @return A String correspondendo �quele perfil
	 */
	public String getNomeBD() {
		return nome;
	}


	/**
	 * M�todo que seta um Nome de Usu�rio no Banco de Dados <br>
	 * Transforma todas as letras em min�sculas e tira todos os espa�os
	 * 
	 * @param nome O nome a ser setado
	 */
	public void setNomeBD( String nome ) {
		this.nome = nome.toLowerCase().replaceAll( " ", "" ).trim();
	}


	/**
	 * M�todo que retorna o Nome do usu�rio de um Perfil
	 * 
	 * @return O Nome do Usu�rio
	 */
	public String getNomecompleto() {
		return nomecompleto;
	}


	/**
	 * M�todo que seta um Nome em um Perfil de Usu�rio
	 * 
	 * @param nomecompleto O nome do usu�rio
	 */
	public void setNomecompleto( String nomecompleto ) {
		this.nomecompleto = nomecompleto;
	}


	/**
	 * M�todo que retorna a ID do Perfil de um usu�rio
	 * 
	 * @return A ID do usu�rio
	 */
	public int getId() {
		return id;
	}


	/**
	 * M�todo que seta uma ID do Perfil de um usu�rio
	 * 
	 * @param id A ID a ser setada
	 */
	public void setId( int id ) {
		this.id = id;
	}


	/**
	 * M�todo que retorna a pontua��o de um Perfil de Usu�rio
	 * 
	 * @return A pontua��o do usu�rio
	 */
	public int getPontuacao() {
		return pontuacao;
	}


	/**
	 * M�todo que seta uma pontua��o em um Perfil de Usu�rio
	 * 
	 * @param pontuacao A pontua��o a ser setada
	 */
	public void setPontuacao( int pontuacao ) {
		this.pontuacao = pontuacao;
	}


	/**
	 * M�todo que retorna a quantidade de Acertos de um Perfil de Usu�rio
	 * 
	 * @return A quantidade de acertos do usu�rio
	 */
	public int getQtdAcertos() {
		return qtdAcertos;
	}


	/**
	 * M�todo que seta a quantidade de acertos em um Perfil de Usu�rio
	 * 
	 * @param qtdAcertos A quantidade de acertos a ser setada
	 */
	public void setQtdAcertos( int qtdAcertos ) {
		this.qtdAcertos = qtdAcertos;
	}


	/**
	 * M�todo que retorna a quantidade de dicas dispon�veis do Perfil de Usu�rio
	 * 
	 * @return A quantidade de dicas dispon�veis
	 */
	public int getQtdDicas() {
		return qtdDicas;
	}


	/**
	 * M�todo que seta a quantidade de dicas dispon�vel em um Perfil de Usu�rio
	 * 
	 * @param qtdDicas A quantidade de dicas a ser setada
	 */
	public void setQtdDicas( int qtdDicas ) {
		this.qtdDicas = qtdDicas;
	}


	/**
	 * Sobrescrita do m�todo toString para exibir corretamente apenas o nome do Perfil de Usu�rio na lista
	 */
	@Override
	public String toString() {
		return nomecompleto;
	}
}
