package br.com.estoque.padroes.comportamental.command;

import br.com.estoque.dominio.Perfil;
import br.com.estoque.padroes.estrutural.EstoqueFacade;
import br.com.estoque.util.Teclado;

/**
 * @note Papel no Padrão: Concrete Command
 */
public class TrocarUsuarioCommand implements Command {

    private EstoqueFacade facade;

    public TrocarUsuarioCommand(EstoqueFacade facade) {
        this.facade = facade;
    }

    @Override
    public String getNome() {
        return "Trocar usuário";
    }

    @Override
    public void executar() {
        String nome = Teclado.lerTexto("Seu nome: ");
        System.out.println("1 - Administrador");
        System.out.println("2 - Operador");
        System.out.println("3 - Visitante");
        int opcao = Teclado.lerInt("Perfil: ");

        Perfil perfil;
        if (opcao == 1) {
            perfil = Perfil.ADMINISTRADOR;
        } else if (opcao == 2) {
            perfil = Perfil.OPERADOR;
        } else {
            perfil = Perfil.VISITANTE;
        }
        facade.entrar(nome, perfil);
    }
}
