package br.com.estoque.padroes.criacional;

import br.com.estoque.dominio.Produto;

/**
 * Monta o produto aos poucos. Categoria, estoque mínimo e fornecedor são
 * opcionais e recebem um valor padrão se não forem informados.
 *
 * @note Papel no Padrão: Builder
 */
public class ProdutoBuilder {

    private String codigo;
    private String nome;
    private String categoria = "Geral";
    private double preco;
    private int quantidade;
    private int estoqueMinimo = 5;
    private String fornecedor = "Não informado";

    public ProdutoBuilder codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public ProdutoBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    public ProdutoBuilder categoria(String categoria) {
        if (categoria != null && !categoria.isEmpty()) {
            this.categoria = categoria;
        }
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

    public ProdutoBuilder fornecedor(String fornecedor) {
        if (fornecedor != null && !fornecedor.isEmpty()) {
            this.fornecedor = fornecedor;
        }
        return this;
    }

    public Produto build() {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        if (preco < 0 || quantidade < 0 || estoqueMinimo < 0) {
            throw new IllegalArgumentException("Preço e quantidades não podem ser negativos.");
        }
        System.out.println("[Builder] Produto " + codigo + " - " + nome + " montado (categoria: "
                + categoria + ", mínimo: " + estoqueMinimo + ", fornecedor: " + fornecedor + ").");
        return new Produto(codigo, nome, categoria, preco, quantidade, estoqueMinimo, fornecedor);
    }
}
