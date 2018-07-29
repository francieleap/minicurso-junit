package servicos;


import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import servicos.LocacaoService;
import utils.DataUtils;

public class LocacaoServiceTest {
	
	@Test
	public void deveFazerUmaLocacao() {
		
		//Cenário 

		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuário 01");
		Filme filme = new Filme("Filme", 10, 5.0);
		
		//Ação
		
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		//Verificação
		
		Assert.assertTrue(locacao.getValor() == 4);
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.adicionarDias(new Date(), 1)));
		
		
	}
}