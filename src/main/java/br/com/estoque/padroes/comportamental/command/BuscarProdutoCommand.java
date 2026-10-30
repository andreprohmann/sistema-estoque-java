package br.com.estoque.padroes.comportamental.command;

import br.com.estoque.dominio.Produto;
import br.com.estoque.padroes.estrutural.EstoqueFacade;
import br.com.estoque.util.Teclado;

/**
 * @note Papel no Padrão: Concrete Command
 */
public class BuscarProdutoCommand implements Command {

    private EstoqueFacade facade;

    public BuscarProdutoCommand(EstoqueFacade facade) {
        this.facade = facade;
    }

    @Override
    public String getNome() {
        return "Buscar produto";
    }

    @Override
    public void executar() {
        String codigo = Teclado.lerTexto("Código do produto: ");
        Produto p = facade.buscarProduto(codigo);
        if (p == null) {
            System.out.println("Produto não encontrado.");
            return;
        }
        // mostra todos os dados do produto encontrado
        System.out.println("Código: " + p.getCodigo());
        System.out.println("Nome: " + p.getNome());
        System.out.println("Categoria: " + p.getCategoria());
        System.out.printf("Preço: R$ %.2f%n", p.getPreco());
        System.out.println("Quantidade: " + p.getQuantidade());
        System.out.println("Estoque mínimo: " + p.getEstoqueMinimo());
        System.out.println("Fornecedor: " + p.getFornecedor());
    }
}
