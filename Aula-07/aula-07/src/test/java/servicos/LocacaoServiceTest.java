package servicos;


import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import utils.DataUtils;

public class LocacaoServiceTest {
	//Tratamento exceção não esperada
	@Test
	public void deveChecarValorLocacao() throws Exception {	
		//Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuário 01");
		Filme filme = new Filme("Filme", 1, 5.0);
		
		//Ação
		Locacao	locacao = service.alugarFilme(usuario, filme);
			
		//Verificação
		Assert.assertTrue(locacao.getValor() == 5);	
	}
	
	//Tratamento de exceção esperada.
	@Test(expected=Exception.class)
	public void deveChecarFilmeSemEstoque() throws Exception {
		
		//Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuário 01");
		Filme filme = new Filme("Filme", 0, 5.0);
		
		//Ação
		Locacao	locacao = service.alugarFilme(usuario, filme);
			
		//Verificação
		Assert.assertTrue(locacao.getValor() == 5);		
	}
	
	//Tratamento de exceção esperada.
	@Test
	public void deveChecarFilmeSemEstoque_() {
		
		//Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuário 01");
		Filme filme = new Filme("Filme", 0, 5.0);
		
		//Ação
		Locacao locacao;
		try {
			locacao = service.alugarFilme(usuario, filme);
			
			//Verificação
			Assert.assertTrue(locacao.getValor() == 5);	
			
			Assert.fail("Deveria ter lançado uma exceção!");
		} catch (Exception e) {
			
			Assert.assertThat(e.getMessage(), CoreMatchers.is("Filme sem estoque."));
		}
	}
	
	@Test
	public void deveChecarDataLocacao() {
		
		//Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuário 01");
		Filme filme = new Filme("Filme", 10, 5.0);
		
		//Ação
		Locacao locacao;
		try {
			locacao = service.alugarFilme(usuario, filme);
			
			//Verificação
			Assert.assertTrue(DataUtils.
			isMesmaData(locacao.getDataLocacao(), new Date()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@Test
	public void deveChecarDataRetornoLocacao() {
		
		//Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuário 01");
		Filme filme = new Filme("Filme", 10, 5.0);
		
		//Ação
		Locacao locacao;
		try {
			
			locacao = service.alugarFilme(usuario, filme);
			//Verificação
			Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), 
					DataUtils.adicionarDias(new Date(), 1)));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}