package servicos;

import static utils.DataUtils.adicionarDias;

import java.util.Date;

import daos.LocacaoDAO;
import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;

public class LocacaoService {
	
	private LocacaoDAO dao;
	private SPCService spcService;
	private EmailService emailService;
	 
	public Locacao alugarFilme(Usuario usuario, Filme filme) throws Exception {
		//Validação filme sem estoque
		
		if (filme.getEstoque() == 0) {
			throw new Exception("Filme sem estoque.");
		}
		
		if (spcService.possuiNegativacao(usuario)) {
			
			emailService.enviaEmailUsuarioNegativado(usuario);
			
			throw new Exception("Usuário Negativado.");
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
		
		//Salvando a locação
		dao.salvar(locacao);
				
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