package br.com.estoque.padroes.comportamental.command;

import br.com.estoque.dominio.Produto;
import br.com.estoque.padroes.estrutural.EstoqueFacade;

/**
 * @note Papel no Padrão: Concrete Command
 */
public class ListarProdutosCommand implements Command {

    private EstoqueFacade facade; // receiver: quem realmente faz o trabalho

    public ListarProdutosCommand(EstoqueFacade facade) {
        this.facade = facade;
    }

    @Override
    public String getNome() {
        return "Listar produtos";
    }

    @Override
    public void executar() {
        // cabeçalho da tabela
        System.out.printf("%-6s %-20s %-12s %11s %5s %5s  %s%n",
                "Código", "Nome", "Categoria", "Preço", "Qtd", "Mín", "Situação");
        // cada linha usa o toString do Produto
        for (Produto p : facade.listarProdutos()) {
            System.out.println(p);
        }
    }
}
