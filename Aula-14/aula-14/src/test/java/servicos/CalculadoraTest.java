package servicos;

import static org.junit.Assert.assertThat;

import java.awt.List;
import java.util.ArrayList;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class CalculadoraTest {
	
	Calculadora calculadora;
	
	@Spy
	Calculadora calculadoraSpy;
	@Mock
	Calculadora calculadoraMock;
	
	@Before
	public void inicializa() {
		calculadora =  new Calculadora();
		MockitoAnnotations.initMocks(this);
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
	
	@Test
	public void deveDividirDoisNumeros() {
		//cenário
		String a = "9";
		String b = "3";
		
		//ação
		int resultado = calculadora.divide(a, b);
		
		//verificacao
		Assert.assertThat(resultado, CoreMatchers.is(3));
		
	}
	
	@Test
	public void deveChecarValorParametros() {
		//cenário
		int a = 9;
		int b = 3;
		
		//acao
		Calculadora calculdora = Mockito.mock(Calculadora.class);
		ArgumentCaptor<Integer> argCaptInt = ArgumentCaptor.forClass(Integer.class);
		
		Mockito.when(calculdora.somar(argCaptInt.capture(), argCaptInt.capture())).thenReturn(12);
		
		int resultado = calculdora.somar(a, b);
		
		assertThat(resultado, CoreMatchers.is(12));
		assertThat(argCaptInt.getAllValues().get(0),CoreMatchers.is(a));
		assertThat(argCaptInt.getAllValues().get(1),CoreMatchers.is(b));		
	}
	
	@Test
	public void deveChecarValorParametrosMetodoVoid() {
		
		//cenário
		int a = 9;
		
		Calculadora calculdora = Mockito.mock(Calculadora.class);
		ArgumentCaptor<Integer> argCaptInt = ArgumentCaptor.forClass(Integer.class);
		
		//acao	 	
		Mockito.doNothing().when(calculdora).imprime(argCaptInt.capture());
		
		calculdora.imprime(a);
		
		//verificacao
		assertThat(argCaptInt.getValue(),CoreMatchers.is(a));
	}
	
	@Test
	public void deveTestarDiferencaoMockSpy() {
		//cenário
		int a = 5;
		int b = 3;
		int c = 10;
		
		Mockito.when(calculadoraMock.somar(a, b)).thenReturn(8);
		Mockito.when(calculadoraSpy.somar(a, b)).thenReturn(8);
		
		//ação
		int resultadoM1 = calculadoraMock.somar(a,b);
		int resultadoS1 = calculadoraSpy.somar(a,b);
		
		int resultadoM2 = calculadoraMock.somar(a,c);
		int resultadoS2 = calculadoraSpy.somar(a,c);
		//verificacao
		
		System.out.println("Resultado mock1: " + resultadoM1);
		System.out.println("Resultado spy1: " + resultadoS1);
		System.out.println("Resultado mock2: " + resultadoM2);
		System.out.println("Resultado spy2: " + resultadoS2);		
	}
	

	@Test
	public void deveTestarProblemaSpy() {
		//cenário
		int a = 5;
		int b = 3;
		
		//Mockito.when(calculadoraSpy.somar(a, b)).thenReturn(8);
		Mockito.doReturn(8).when(calculadoraSpy).somar(a, b);
		
		//ação
		int resultadoS1 = calculadoraSpy.somar(a,b);
		
		//verificacao
		
		assertThat(resultadoS1, CoreMatchers.is(8));
			
	}
}
