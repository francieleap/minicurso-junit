package servicos;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdemExecucaoTest {
	
	public static int contador = 0;
	
	@Test
	public void teste1() {
		contador = contador + 1;
		Assert.assertThat(contador, CoreMatchers.is(1));
	}
	
	@Test
	public void teste2() {
		contador = contador + 1;
		Assert.assertThat(contador, CoreMatchers.is(2));
	}
	
	@Test
	public void teste3() {
		contador = contador + 1;
		Assert.assertThat(contador, CoreMatchers.is(3));
	}

}
