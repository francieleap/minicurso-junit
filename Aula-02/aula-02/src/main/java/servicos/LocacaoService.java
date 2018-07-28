package servicos;

import static utils.DataUtils.adicionarDias;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import utils.DataUtils;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) {
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
				
		return locacao;
	}
	
	@Test
	public void deveFazerUmaLocacao() {
		
		//Cenário 

		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuário 01");
		Filme filme = new Filme("Filme", 10, 5.0);
		
		//Ação
		
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		//Verificação
		
		Assert.assertTrue(locacao.getValor() == 5);
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.adicionarDias(new Date(), 1)));
		
		
	}
}