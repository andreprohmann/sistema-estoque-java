package br.com.estoque.servicos;

import br.com.estoque.dominio.Produto;

import java.util.List;

/**
 * Interface comum ao serviço real e ao Proxy.
 *
 * @note Papel no Padrão: Subject (Proxy)
 */
public interface EstoqueService {

    String gerarCodigo();

    void cadastrar(Produto produto);

    List<Produto> listar();

    Produto buscar(String codigo);

    void entrada(String codigo, int quantidade);

    void saida(String codigo, int quantidade);

    void alterarPreco(String codigo, double preco);

    void remover(String codigo);
}
