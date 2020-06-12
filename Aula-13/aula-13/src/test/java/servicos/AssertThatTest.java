package servicos;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.describedAs;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;

import org.junit.Assert;
import org.junit.Test;

import entidades.Usuario;

public class AssertThatTest {

	@Test
	public void testeAssertivaThat() {
		
		Assert.assertThat("123",is("123"));	
		
		Assert.assertThat(123, any(Integer.class));
		
		Assert.assertThat(123, describedAs("Inteiro igual a %0", equalTo(123), 123));
		
		Assert.assertThat("123", allOf(isA(String.class),equalTo("123")));
		
		Assert.assertThat("123", anyOf(isA(String.class),equalTo("111")));
		
		Usuario usuario = new Usuario();
		
		Assert.assertThat(usuario, instanceOf(Usuario.class));
		
		String texto = "texto";
		Assert.assertThat(texto, notNullValue(String.class));
		
		Assert.assertThat(usuario, sameInstance(usuario));
		
		//Customizando matchers
		Assert.assertThat("Aluno", CustomMatcher.startWithLetter("A"));
	}
}
