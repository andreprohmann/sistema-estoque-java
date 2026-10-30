package br.com.estoque.dominio;

/**
 * Produto guardado no estoque. É criado pelo ProdutoBuilder.
 *
 * @note Papel no Padrão: Product (Builder)
 */
public class Produto {

    private String codigo;
    private String nome;
    private Categoria categoria;
    private double preco;
    private int quantidade;      // quantidade atual em estoque
    private int estoqueMinimo;   // abaixo ou igual a isso o estoque é considerado baixo
    private String fornecedor;

    // o código não vem no construtor: ele é gerado pelo serviço na hora do cadastro
    public Produto(String nome, Categoria categoria, double preco,
                   int quantidade, int estoqueMinimo, String fornecedor) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidade = quantidade;
        this.estoqueMinimo = estoqueMinimo;
        this.fornecedor = fornecedor;
    }

    // usado pelos observers para saber se precisam dar alerta
    public boolean isEstoqueBaixo() {
        return quantidade <= estoqueMinimo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public Categoria getCategoria() {
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

    // linha usada na listagem de produtos
    @Override
    public String toString() {
        String situacao = quantidade == 0 ? "ESGOTADO" : (isEstoqueBaixo() ? "BAIXO" : "OK");
        return String.format("%-6s %-20s %-12s R$ %8.2f %5d %5d  %s",
                codigo, nome, categoria, preco, quantidade, estoqueMinimo, situacao);
    }
}
