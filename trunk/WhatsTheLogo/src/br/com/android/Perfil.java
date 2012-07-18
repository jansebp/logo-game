
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
	 * Construtor. Cria um Perfil de Usuário do início, ou seja, com os campos <i>pontuação</i>, <i>quantidade de
	 * acertos</i> e <i>dicas</> zerados, e com o nome digitado pelo jogador
	 * 
	 * @param nomeCompleto O nome do Perfil de Usuário
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
	 * Método que retorna uma instância de um Perfil de Usuário
	 * 
	 * @return Uma instância de um Perfil de Usuário
	 */
	public static Perfil getInstance() {
		if ( instance == null )
			instance = new Perfil();
		return instance;
	}


	/**
	 * Método que retorna a String de referência de um Perfil no Banco de Dados
	 * 
	 * @return A String correspondendo àquele perfil
	 */
	public String getNomeBD() {
		return nome;
	}


	/**
	 * Método que seta um Nome de Usuário no Banco de Dados <br>
	 * Transforma todas as letras em minúsculas e tira todos os espaços
	 * 
	 * @param nome O nome a ser setado
	 */
	public void setNomeBD( String nome ) {
		this.nome = nome.toLowerCase().replaceAll( " ", "" ).trim();
	}


	/**
	 * Método que retorna o Nome do usuário de um Perfil
	 * 
	 * @return O Nome do Usuário
	 */
	public String getNomecompleto() {
		return nomecompleto;
	}


	/**
	 * Método que seta um Nome em um Perfil de Usuário
	 * 
	 * @param nomecompleto O nome do usuário
	 */
	public void setNomecompleto( String nomecompleto ) {
		this.nomecompleto = nomecompleto;
	}


	/**
	 * Método que retorna a ID do Perfil de um usuário
	 * 
	 * @return A ID do usuário
	 */
	public int getId() {
		return id;
	}


	/**
	 * Método que seta uma ID do Perfil de um usuário
	 * 
	 * @param id A ID a ser setada
	 */
	public void setId( int id ) {
		this.id = id;
	}


	/**
	 * Método que retorna a pontuação de um Perfil de Usuário
	 * 
	 * @return A pontuação do usuário
	 */
	public int getPontuacao() {
		return pontuacao;
	}


	/**
	 * Método que seta uma pontuação em um Perfil de Usuário
	 * 
	 * @param pontuacao A pontuação a ser setada
	 */
	public void setPontuacao( int pontuacao ) {
		this.pontuacao = pontuacao;
	}


	/**
	 * Método que retorna a quantidade de Acertos de um Perfil de Usuário
	 * 
	 * @return A quantidade de acertos do usuário
	 */
	public int getQtdAcertos() {
		return qtdAcertos;
	}


	/**
	 * Método que seta a quantidade de acertos em um Perfil de Usuário
	 * 
	 * @param qtdAcertos A quantidade de acertos a ser setada
	 */
	public void setQtdAcertos( int qtdAcertos ) {
		this.qtdAcertos = qtdAcertos;
	}


	/**
	 * Método que retorna a quantidade de dicas disponíveis do Perfil de Usuário
	 * 
	 * @return A quantidade de dicas disponíveis
	 */
	public int getQtdDicas() {
		return qtdDicas;
	}


	/**
	 * Método que seta a quantidade de dicas disponível em um Perfil de Usuário
	 * 
	 * @param qtdDicas A quantidade de dicas a ser setada
	 */
	public void setQtdDicas( int qtdDicas ) {
		this.qtdDicas = qtdDicas;
	}


	/**
	 * Sobrescrita do método toString para exibir corretamente apenas o nome do Perfil de Usuário na lista
	 */
	@Override
	public String toString() {
		return nomecompleto;
	}
}
