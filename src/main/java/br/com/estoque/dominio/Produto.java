package br.com.estoque.dominio;

/**
 * @note Papel no Padrão: Product (Builder)
 */
public class Produto {

    private String codigo;
    private String nome;
    private String categoria;
    private double preco;
    private int quantidade;
    private int estoqueMinimo;
    private String fornecedor;

    public Produto(String codigo, String nome, String categoria, double preco,
                   int quantidade, int estoqueMinimo, String fornecedor) {
        this.codigo = codigo;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidade = quantidade;
        this.estoqueMinimo = estoqueMinimo;
        this.fornecedor = fornecedor;
    }

    public boolean isEstoqueBaixo() {
        return quantidade <= estoqueMinimo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    @Override
    public String toString() {
        String situacao = quantidade == 0 ? "ESGOTADO" : (isEstoqueBaixo() ? "BAIXO" : "OK");
        return String.format("%-6s %-20s %-12s R$ %8.2f %5d %5d  %s",
                codigo, nome, categoria, preco, quantidade, estoqueMinimo, situacao);
    }
}
