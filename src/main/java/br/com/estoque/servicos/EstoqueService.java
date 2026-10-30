package br.com.estoque.servicos;

import br.com.estoque.dominio.Categoria;
import br.com.estoque.dominio.Produto;

import java.util.List;

/**
 * Interface comum ao serviço real e ao Proxy. Como os dois implementam a
 * mesma interface, a Facade pode usar o Proxy sem saber que ele existe.
 *
 * @note Papel no Padrão: Subject (Proxy)
 */
public interface EstoqueService {

    // gera o código do produto e guarda no estoque
    void cadastrar(Produto produto);

    List<Produto> listar();

    Produto buscar(String codigo);

    void entrada(String codigo, int quantidade);

    void saida(String codigo, int quantidade);

    void alterarPreco(String codigo, double preco);

    void remover(String codigo);

    Categoria cadastrarCategoria(String nome);

    List<Categoria> listarCategorias();
}
