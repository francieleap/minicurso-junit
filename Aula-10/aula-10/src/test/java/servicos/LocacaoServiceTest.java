package servicos;


import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import utils.DataUtils;

public class LocacaoServiceTest {
	
	LocacaoService service ;
	
	@Before
	public void inicializa() {
		System.out.println("@Before");
		service = new LocacaoService();
	}
	
	@After
	public void encerra() {
		System.out.println("@After");
	}
	
	@BeforeClass
	public static void inicializaClasse() {
		System.out.println("@BeforeClass");
	}
	
	@AfterClass
	public static void encerraClasse() {
		System.out.println("@AfterClass!");
	}
	
	//Tratamento exceção não esperada
	@Test
	public void deveChecarValorLocacao() throws Exception {	
		//Cenário
	
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
	
	@Test
	public void deveDescontar25PorCentoEmLocacaoAcimaDe50Reais() throws Exception {
		//Cenário
		Usuario usuario = new Usuario("Usuário 01");
		Filme filme = new Filme("Filme", 10, 60.0);
		
		//Ação
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		//Verificação
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(45.0));
		
	}
}