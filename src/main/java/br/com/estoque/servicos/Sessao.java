package br.com.estoque.servicos;

import br.com.estoque.dominio.Usuario;

// guarda quem está logado. A Facade troca o usuário e o Proxy consulta daqui
public class Sessao {

    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
