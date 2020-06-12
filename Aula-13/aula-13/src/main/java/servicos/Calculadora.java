package servicos;

public class Calculadora {

	public int somar(int a, int b) {
		
		System.out.println("Executando operacao de soma!");
		
		return a + b;
	}
	
	public int divide(String a, String b) {
		return Integer.valueOf(a)/Integer.valueOf(b);
	}
	
	public void imprime(int resultado) {
		
		System.out.println("O resultado da soma eh: " +  resultado);
	}

}
