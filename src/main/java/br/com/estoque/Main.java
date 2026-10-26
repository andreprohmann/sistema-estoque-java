package br.com.estoque;

import br.com.estoque.padroes.comportamental.command.*;
import br.com.estoque.padroes.estrutural.EstoqueFacade;
import br.com.estoque.util.Teclado;

/**
 * @note Papel no Padrão: Client (Command)
 */
public class Main {

    public static void main(String[] args) {
        EstoqueFacade facade = new EstoqueFacade();

        System.out.println("===== SISTEMA DE ESTOQUE =====");
        new TrocarUsuarioCommand(facade).executar();

        MenuInvoker menu = new MenuInvoker();
        menu.adicionar(1, new ListarProdutosCommand(facade));
        menu.adicionar(2, new BuscarProdutoCommand(facade));
        menu.adicionar(3, new CadastrarProdutoCommand(facade));
        menu.adicionar(4, new RegistrarEntradaCommand(facade));
        menu.adicionar(5, new RegistrarSaidaCommand(facade));
        menu.adicionar(6, new AlterarPrecoCommand(facade));
        menu.adicionar(7, new RemoverProdutoCommand(facade));
        menu.adicionar(8, new HistoricoCommand(facade));
        menu.adicionar(9, new GerenciarNotificacoesCommand(facade));
        menu.adicionar(10, new TrocarUsuarioCommand(facade));

        int opcao;
        do {
            System.out.println("\nUsuário: " + facade.getUsuario() + " (" + facade.getPerfil() + ")");
            menu.mostrarMenu();
            opcao = Teclado.lerInt("Opção: ");
            if (opcao != 0) {
                menu.executar(opcao);
            }
        } while (opcao != 0);

        System.out.println("Sistema encerrado.");
    }
}
