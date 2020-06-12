package servicos;

import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.regex.Matcher;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import builders.FilmeBuilder;
import builders.UsuarioBuilder;
import daos.LocacaoDAO;
import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import utils.DataUtils;

public class LocacaoServiceTest {
	
	@InjectMocks
	private LocacaoService service ;
	
	@Mock
	private LocacaoDAO dao;
	
	@Mock
	private SPCService spcService;
	
	@Mock
	private EmailService emailService;
	
	@Before
	public void inicializa() {
		
		System.out.println("@Before");
		MockitoAnnotations.initMocks(this);
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
	
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		Filme filme = FilmeBuilder.umFilme().build();
		
		//Ação
		Locacao	locacao = service.alugarFilme(usuario, filme);
			
		//Verificação
		Assert.assertTrue(locacao.getValor() == 5);	
	}
	
	//Tratamento de exceção esperada.
	@Test(expected=Exception.class)
	public void deveChecarFilmeSemEstoque() throws Exception {
		
		//Cenário
		
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		Filme filme = FilmeBuilder.umFilme().comEstoque(0).build();
		
		//Ação
		Locacao	locacao = service.alugarFilme(usuario, filme);
			
		//Verificação
		Assert.assertTrue(locacao.getValor() == 5);		
	}
	
	//Tratamento de exceção esperada.
	@Test
	public void deveChecarFilmeSemEstoque_() {
		
		//Cenário
				
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		
		Filme filme = FilmeBuilder.umFilme().comEstoque(0).build();
		
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
		
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		Filme filme = FilmeBuilder.umFilme().build();
		
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
		
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		Filme filme = FilmeBuilder.umFilme().build();
		
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
		Filme filme = FilmeBuilder.umFilme().comPrecoLocacao(60.0).build();
		
		//Ação
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		//Verificação
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(45.0));
		
	}
	
	@Test(expected=Exception.class)
	public void naoDeveAlugarFilmeParaUsuarioNegativado() throws Exception {
		//cenario
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		Filme filme = FilmeBuilder.umFilme().build();

		Mockito.when(spcService.possuiNegativacao(usuario)).thenReturn(true);

		//acao
		service.alugarFilme(usuario, filme);	
	
	}
	
	@Test(expected=Exception.class)
	public void deveEnviarEmailParaUsuarioNegativado() throws Exception {
		//cenario
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		Filme filme = FilmeBuilder.umFilme().build();

		Mockito.when(spcService.possuiNegativacao(usuario)).thenReturn(true);

		//acao
		service.alugarFilme(usuario, filme);
		
		//Verificação
		Mockito.verify(emailService).enviaEmailUsuarioNegativado(usuario);
	
	}
	
	@Test
	public void naoDeveEnviarEmailParaUsuarioNaoNegativado() throws Exception {
		//cenario
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		Filme filme = FilmeBuilder.umFilme().build();
		
		//acao
		service.alugarFilme(usuario, filme);
		
		//Verificação
		Mockito.verify(emailService, Mockito.never()).enviaEmailUsuarioNegativado(usuario);
	
	}
	
	@Test
	public void deveAlugarFilmeEscolhido() throws Exception {
		
		//cenario
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		Filme filme = FilmeBuilder.umFilme().comNome("Se beber nao case").build();
		
		//acao
		service.alugarFilme(usuario, filme);
		
		ArgumentCaptor<Locacao> argCaptLocacao = ArgumentCaptor.forClass(Locacao.class);
		Mockito.verify(dao).salvar(argCaptLocacao.capture());
		
		Locacao locacaoRetornada = argCaptLocacao.getValue();
		
		assertThat(locacaoRetornada.getFilme().getNome(), CoreMatchers.is("Se beber nao case"));
	}
	
}