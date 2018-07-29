package servicos;


import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import utils.DataUtils;

public class LocacaoServiceTest {
	
	@Test
	public void deveChecarValorLocacao() {
		
		//Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuário 01");
		Filme filme = new Filme("Filme", 10, 5.0);
		
		//Ação
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		//Verificação
		Assert.assertTrue(locacao.getValor() == 5);
	}
	
	@Test
	public void deveChecarDataLocacao() {
		
		//Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuário 01");
		Filme filme = new Filme("Filme", 10, 5.0);
		
		//Ação
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		//Verificação
		Assert.assertTrue(DataUtils.
		isMesmaData(locacao.getDataLocacao(), new Date()));
	}
	
	@Test
	public void deveChecarDataRetornoLocacao() {
		
		//Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuário 01");
		Filme filme = new Filme("Filme", 10, 5.0);
		
		//Ação
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		//Verificação
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), 
				DataUtils.adicionarDias(new Date(), 1)));
	}
	
}