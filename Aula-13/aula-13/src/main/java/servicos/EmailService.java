package servicos;

import entidades.Usuario;

public interface EmailService {
	
	public boolean enviaEmailUsuarioNegativado(Usuario usuario);
	
}
