package br.com.estoque.padroes.comportamental.command;

import br.com.estoque.dominio.Produto;
import br.com.estoque.padroes.estrutural.EstoqueFacade;
import br.com.estoque.util.Teclado;

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
        String codigo = facade.gerarCodigo();
        System.out.println("Código do novo produto: " + codigo);

        String nome = Teclado.lerTexto("Nome: ");
        String categoria = Teclado.lerTexto("Categoria (ENTER para 'Geral'): ");
        double preco = Teclado.lerDouble("Preço: ");
        int quantidade = Teclado.lerInt("Quantidade: ");
        int minimo = Teclado.lerInt("Estoque mínimo: ");
        String fornecedor = Teclado.lerTexto("Fornecedor (opcional): ");

        Produto produto = facade.cadastrarProduto(codigo, nome, categoria, preco, quantidade, minimo, fornecedor);
        System.out.println("Produto cadastrado: " + produto.getCodigo() + " - " + produto.getNome());
    }
}
