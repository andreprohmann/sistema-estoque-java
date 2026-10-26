package br.com.estoque.padroes.comportamental.command;

import br.com.estoque.dominio.Produto;
import br.com.estoque.padroes.estrutural.EstoqueFacade;

/**
 * @note Papel no Padrão: Concrete Command
 */
public class ListarProdutosCommand implements Command {

    private EstoqueFacade facade;

    public ListarProdutosCommand(EstoqueFacade facade) {
        this.facade = facade;
    }

    @Override
    public String getNome() {
        return "Listar produtos";
    }

    @Override
    public void executar() {
        System.out.printf("%-6s %-20s %-12s %11s %5s %5s  %s%n",
                "Código", "Nome", "Categoria", "Preço", "Qtd", "Mín", "Situação");
        for (Produto p : facade.listarProdutos()) {
            System.out.println(p);
        }
    }
}
