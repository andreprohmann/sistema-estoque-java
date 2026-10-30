package br.com.estoque.padroes.comportamental.command;

import br.com.estoque.dominio.Categoria;
import br.com.estoque.dominio.Produto;
import br.com.estoque.padroes.estrutural.EstoqueFacade;
import br.com.estoque.util.Teclado;

import java.util.List;

/**
 * @note Papel no Padrão: Concrete Command
 */
public class CadastrarProdutoCommand implements Command {

    private EstoqueFacade facade;

    public CadastrarProdutoCommand(EstoqueFacade facade) {
        this.facade = facade;
    }

    @Override
    public String getNome() {
        return "Cadastrar produto";
    }

    @Override
    public void executar() {
        String nome = Teclado.lerTexto("Nome: ");
        Categoria categoria = escolherCategoria();
        double preco = Teclado.lerDouble("Preço: ");
        int quantidade = Teclado.lerInt("Quantidade: ");
        int minimo = Teclado.lerInt("Estoque mínimo: ");
        String fornecedor = Teclado.lerTexto("Fornecedor (opcional): ");

        // a facade usa o Builder para montar o produto e o proxy confere se o
        // usuário é administrador. O código (P005, P006...) é gerado pelo serviço
        Produto produto = facade.cadastrarProduto(nome, categoria, preco, quantidade, minimo, fornecedor);
        System.out.println("Produto cadastrado: " + produto.getCodigo() + " - " + produto.getNome());
    }

    // mostra as categorias numeradas e repete a pergunta até escolher uma que existe
    private Categoria escolherCategoria() {
        List<Categoria> categorias = facade.listarCategorias();
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println("   " + (i + 1) + " - " + categorias.get(i));
        }
        int opcao = Teclado.lerInt("Categoria: ");
        while (opcao < 1 || opcao > categorias.size()) {
            System.out.println("Categoria inválida.");
            opcao = Teclado.lerInt("Categoria: ");
        }
        return categorias.get(opcao - 1); // -1 porque a lista começa no índice 0
    }
}
