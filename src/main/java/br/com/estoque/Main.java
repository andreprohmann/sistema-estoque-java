package br.com.estoque;

import br.com.estoque.padroes.comportamental.command.*;
import br.com.estoque.padroes.estrutural.EstoqueFacade;
import br.com.estoque.util.Teclado;

/**
 * Classe principal. Monta o menu e fica repetindo até o usuário escolher sair.
 *
 * @note Papel no Padrão: Client (Command)
 */
public class Main {

    public static void main(String[] args) {
        // cria a facade, que já monta o resto do sistema por dentro
        EstoqueFacade facade = new EstoqueFacade();

        System.out.println("===== SISTEMA DE ESTOQUE =====");
        // antes do menu o usuário precisa dizer quem é e qual o perfil
        new TrocarUsuarioCommand(facade).executar();

        // registra cada opção do menu com o seu comando
        MenuInvoker menu = new MenuInvoker();
        menu.adicionar(1, new ListarProdutosCommand(facade));
        menu.adicionar(2, new BuscarProdutoCommand(facade));
        menu.adicionar(3, new CadastrarProdutoCommand(facade));
        menu.adicionar(4, new CadastrarCategoriaCommand(facade));
        menu.adicionar(5, new RegistrarEntradaCommand(facade));
        menu.adicionar(6, new RegistrarSaidaCommand(facade));
        menu.adicionar(7, new AlterarPrecoCommand(facade));
        menu.adicionar(8, new RemoverProdutoCommand(facade));
        menu.adicionar(9, new HistoricoCommand(facade));
        menu.adicionar(10, new GerenciarNotificacoesCommand(facade));
        menu.adicionar(11, new TrocarUsuarioCommand(facade));

        int opcao;
        do {
            // mostra quem está usando o sistema no momento
            System.out.println("\nUsuário: " + facade.getUsuario());
            menu.mostrarMenu();
            opcao = Teclado.lerInt("Opção: ");
            // 0 é sair, qualquer outro número vai para o invoker
            if (opcao != 0) {
                menu.executar(opcao);
            }
        } while (opcao != 0);

        System.out.println("Sistema encerrado.");
    }
}
