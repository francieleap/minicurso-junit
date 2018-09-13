package servicos;

import org.junit.Assert;
import org.junit.Test;

import entidades.Usuario;

public class AssertTest {

	@Test
	public void testeAssertivas() {
		
		Assert.assertTrue(1 == 1 );
		Assert.assertFalse(1 == 2);
		
		Assert.assertEquals(1, 1);
		Assert.assertEquals("teste", "teste");
		
		int numerosPares[] = new int[3];
		numerosPares[0] = 2;
		numerosPares[1] = 4;
		numerosPares[2] = 6;
		
		int copiaNumerosPares[] = new int[3];
		copiaNumerosPares[0] = 2;
		copiaNumerosPares[1] = 4;
		copiaNumerosPares[2] = 6;
		
		Assert.assertArrayEquals(numerosPares, copiaNumerosPares);
		
		Assert.assertEquals(Math.PI, 3.14, 0.01);
		
		//Trabalhando com objetos
		
		Usuario usuario1 = new Usuario("Usuario 1");
		Usuario usuario2 = new Usuario("Usuario 1");
		
		Assert.assertEquals(usuario1, usuario2);
		
		Usuario usuario3 = usuario2;
		
		Assert.assertSame(usuario2, usuario3);
		
		Usuario usuario4 = null;
		
		Assert.assertNull(usuario4);
		
		//Customizando mensagem de exceção
		
		int a = 1; int b=1;
		String mensagem = "Falha: O valor esperado era %d mas foi obtido %d";
		
		Assert.assertTrue(String.format(mensagem, a, b), a == b);

		//Trabalhando com fail()
		
		try {
		    // faz um teste que deveria dar exception...
			Assert.assertTrue((2/0) == 1);
		    Assert.fail();   
		 
		}catch(Exception e) {
		    Assert.assertTrue(true);
		}
		
	}
}
