package builders;

import entidades.Filme;

public class FilmeBuilder {

	private Filme filme;
	private FilmeBuilder() {};
	
	public static FilmeBuilder umFilme() {
		
		FilmeBuilder builder = new FilmeBuilder();
		builder.filme = new Filme();
		builder.filme.setNome("A freira");
		builder.filme.setEstoque(10);
		builder.filme.setPrecoLocacao(5.0);
		return builder;
	}
	
	public FilmeBuilder comEstoque(Integer valor) {
		filme.setEstoque(valor);
		return this;
	}
	
	public FilmeBuilder comPrecoLocacao(Double valor) {
		filme.setPrecoLocacao(valor);
		return this;
	}
	
	public FilmeBuilder comNome(String nome) {
		filme.setNome(nome);
		return this;
	}
	
	public Filme build() {
		return filme;
	}	
}


