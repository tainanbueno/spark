package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Series implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String nome;
	private String sinopse;
	private String genero;
	private String lancamento;
	private int temporadas;
	private int episodios;
	
	public Produto() {
		id = -1;
		nome = "";
		sinopse = "";
		genero = "";
		lancamento = "";
		temporadas = 0;
		episodios = 0;
	}

	public Produto(int id, String nome, String sinopse, String genero, String lancamento, int temporadas, int episodios) {
		setId(id);
		setNome(nome);
		setSinopse(sinopse);
		setGenero(genero);
		setLancamento(lancamento);
		setTemporadas(temporadas);
		setEpisodios(episodios);
	}		
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSinopse() {
		return sinopse;
	}
	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}
	
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	public String getLancamento() {
		return lancamento;
	}
	public void setLancamento(String lancamento) {
		this.lancamento = lancamento;
	}

	public int getTemporadas() {
		return temporadas;
	}
	public void setTemporadas(int temporadas) {
			this.temporadas = temporadas;
	}
	
	public int getEpisodios() {
		return episodios;
	}
	public void setEpisodios(int episodios) {
			this.episodios = episodios;
	}
	

	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Série: " + nome + " Sinopse: " + sinopse + " Genêro: " + genero + " Data Lançamento: "
				+ lancamento  + " Número de temporadas: " + temporadas + " Número de episódios: " + episodios;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Series) obj).getId());
	}	
}