package br.com.estoque.padroes.criacional;

import br.com.estoque.dominio.Categoria;
import br.com.estoque.dominio.Produto;

/**
 * Monta o produto aos poucos. Estoque mínimo e fornecedor são opcionais e
 * recebem um valor padrão se não forem informados.
 *
 * @note Papel no Padrão: Builder
 */
public class ProdutoBuilder {

    // campos que vão formar o produto. Os opcionais já começam com valor padrão
    private String nome;
    private Categoria categoria;
    private double preco;
    private int quantidade;
    private int estoqueMinimo = 5;
    private String fornecedor = "Não informado";

    // cada método guarda um valor e devolve o próprio builder (return this),
    // assim dá para encadear: new ProdutoBuilder().nome(...).preco(...).build()
    public ProdutoBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    public ProdutoBuilder categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public ProdutoBuilder preco(double preco) {
        this.preco = preco;
        return this;
    }

    public ProdutoBuilder quantidade(int quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public ProdutoBuilder estoqueMinimo(int estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
        return this;
    }

    // se vier vazio mantém o valor padrão
    public ProdutoBuilder fornecedor(String fornecedor) {
        if (fornecedor != null && !fornecedor.isEmpty()) {
            this.fornecedor = fornecedor;
        }
        return this;
    }

    // valida os dados e só então cria o produto
    public Produto build() {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        if (categoria == null) {
            throw new IllegalArgumentException("A categoria é obrigatória.");
        }
        if (preco < 0 || quantidade < 0 || estoqueMinimo < 0) {
            throw new IllegalArgumentException("Preço e quantidades não podem ser negativos.");
        }
        System.out.println("[Builder] Produto " + nome + " montado (categoria: "
                + categoria + ", mínimo: " + estoqueMinimo + ", fornecedor: " + fornecedor + ").");
        return new Produto(nome, categoria, preco, quantidade, estoqueMinimo, fornecedor);
    }
}
