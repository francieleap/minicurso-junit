package servicos;

import static utils.DataUtils.adicionarDias;

import java.util.Date;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) throws Exception {
		//Validação filme sem estoque
		
		if (filme.getEstoque() == 0) {
			throw new Exception("Filme sem estoque.");
		}
		
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(calculaValorLocacao(filme.getPrecoLocacao()));
		
		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
				
		return locacao;
	}
	
	private Double calculaValorLocacao(Double precoFilme) {
		
		if (precoFilme > 50) {
			Double desconto = precoFilme * 0.25;
			return precoFilme-desconto;
		}
		
		return precoFilme;
	}
	
}