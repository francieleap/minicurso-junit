package servicos;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculadoraTest {
	
	Calculadora calculadora;
	
	@Before
	public void inicializa() {
		calculadora =  new Calculadora();
	}
	
	@Test
	public void deveSomarDoisNumeros() {
		//cenário
		int a = 5;
		int b = 3;
		
		//ação
		int resultado = calculadora.somar(a,b);
		
		//verificacao
		Assert.assertThat(resultado, CoreMatchers.is(8));
		
	}
}
