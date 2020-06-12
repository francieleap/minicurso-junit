package builders;

import java.util.Date;

import entidades.Locacao;
import utils.DataUtils;

public class LocacaoBuilder {
	
	private Locacao locacao;
	private LocacaoBuilder() {};
	
	public static LocacaoBuilder umaLocacao() {
		
		LocacaoBuilder builder = new LocacaoBuilder();
		builder.locacao.setDataLocacao(new Date());
		builder.locacao.setDataRetorno(DataUtils.adicionarDias(new Date(), 7));
		builder.locacao.setUsuario(UsuarioBuilder.umUsuario().build());
		builder.locacao.setFilme(FilmeBuilder.umFilme().build());
		builder.locacao.setValor(30.00);	
		return builder;
	}
	
	public LocacaoBuilder comDataRetorno(Date data) {
		locacao.setDataRetorno(data);
		return this;
	}
	
	public Locacao build() {
		return locacao;
	}

}

