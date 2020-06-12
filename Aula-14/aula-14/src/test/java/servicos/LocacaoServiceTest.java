package servicos;

import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import builders.FilmeBuilder;
import builders.LocacaoBuilder;
import builders.UsuarioBuilder;
import daos.LocacaoDAO;
import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import utils.DataUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LocacaoService.class)
public class LocacaoServiceTest {
	
	@InjectMocks
	private LocacaoService service ;
	
	@Mock
	private LocacaoDAO dao;
	
	@Mock
	private SPCService spcService;
	
	@Mock
	private EmailService emailService;
	
	@Captor
	private ArgumentCaptor<Locacao> argCaptLocacao;
	
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
	
	@Test
	public void deveMockarConstrutorDate() throws Exception {

		//cenario
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		Filme filme = FilmeBuilder.umFilme().comNome("Se beber nao case").build();
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date dataEsperada = format.parse("09/08/1996");
		
		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(dataEsperada);
		
		//acao
		service.alugarFilme(usuario, filme);
		
		//verificacao
		
		Mockito.verify(dao).salvar(argCaptLocacao.capture());	
		assertThat(argCaptLocacao.getValue().getDataLocacao(), CoreMatchers.is(dataEsperada));
		
	}
	
	@Test
	public void deveMockarConstrutorLocacaoComParametros() throws Exception {

		//cenario
		Usuario usuario = UsuarioBuilder.umUsuario().comNome("Ezequiel").build();
		Filme filme = FilmeBuilder.umFilme().comNome("Se beber nao case").build();
		
		Usuario usuario2 = UsuarioBuilder.umUsuario().comNome("Carlos").build();
		Filme filme2 = FilmeBuilder.umFilme().comNome("Quarteto Fantastico").build();
		
		Locacao locacaoEsperada = new Locacao(usuario, filme);
		
		PowerMockito.whenNew(Locacao.class)
		.withArguments(Mockito.any(Usuario.class), Mockito.any(Filme.class))
		.thenReturn(locacaoEsperada);
		
		//acao
		service.alugarFilmeGratis(usuario2, filme2);
		
		//verificacao
		
		Mockito.verify(dao).salvar(argCaptLocacao.capture());	
		
		assertThat(argCaptLocacao.getValue().getUsuario().getNome(), CoreMatchers.is("Ezequiel"));
		assertThat(argCaptLocacao.getValue().getFilme().getNome(), CoreMatchers.is("Se beber nao case"));	
	}
	
	@Test
	public void deveMockarMetodoEstaticoVoid() {
		//cenario
		
		Locacao locacao = LocacaoBuilder.umaLocacao().comValor(500.00).build();
		
		PowerMockito.mockStatic(LocacaoService.class);
		PowerMockito.doNothing().when(LocacaoService.class);
		
		//acao
		LocacaoService.aplica10DescontoValorLocacao(locacao);
		
		//Verificacao
		
		assertThat(locacao.getValor(), CoreMatchers.is(500.00));
		
	}
	
	@Test
	public void deveMockarMetodoEstaticoComRetorno() {
		
		//cenario
		Locacao locacao = LocacaoBuilder.umaLocacao().comValor(500.00).build();
		
		PowerMockito.mockStatic(LocacaoService.class);
		PowerMockito.when(LocacaoService.get10DescontoValorLocacao(Mockito.any(Locacao.class)))
		.thenReturn(600.00);
		
		//acao
		Double novoValorLocacao = LocacaoService.get10DescontoValorLocacao(locacao);
		
		//Verificacao
		
		assertThat(novoValorLocacao, CoreMatchers.is(600.00));
		
		PowerMockito.verifyStatic();
		LocacaoService.get10DescontoValorLocacao(locacao);
	}	
	
	@Test
	public void deveMockarMetodoPrivado() throws Exception {
			
		//cenario
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		Filme filme = FilmeBuilder.umFilme().comPrecoLocacao(100.00).build();
		service = PowerMockito.spy(service);
		
		PowerMockito.doReturn(400.00).when(service, "calculaValorLocacao", Mockito.any(Double.class));
				
		//acao
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		//verificacao	
		assertThat(locacao.getValor(), CoreMatchers.is(400.0));
		
		PowerMockito.verifyPrivate(service).invoke("calculaValorLocacao", Mockito.any(Double.class));
	}
	
	@Test
	public void deveTestarMetodoPrivado() throws Exception {
			
		//cenario
		Double valorLocacao = 100.00;
		
		Double valorRetornado = org.powermock.reflect.Whitebox
				.invokeMethod(service, "calculaValorLocacao", valorLocacao);
		//acao
		assertThat(valorRetornado, CoreMatchers.is(75.0));
	}
	
	@Test
	public void deveTestarConstrutor() throws Exception {
			
		//cenario
		Usuario usuario = UsuarioBuilder.umUsuario().comNome("Franciele").build();
		Filme filme = FilmeBuilder.umFilme().comNome("Minha mae eh uma peca").build();
		
		Locacao locacao = org.powermock.reflect.Whitebox
				.invokeConstructor(Locacao.class, usuario, filme);
		//acao
		assertThat(locacao.getUsuario().getNome(), CoreMatchers.is("Franciele"));
		assertThat(locacao.getFilme().getNome(), CoreMatchers.is("Minha mae eh uma peca"));
	}
}