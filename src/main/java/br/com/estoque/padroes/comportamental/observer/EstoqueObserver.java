package br.com.estoque.padroes.comportamental.observer;

import br.com.estoque.dominio.Produto;

/**
 * @note Papel no Padrão: Observer
 */
public interface EstoqueObserver {

    String getNome();

    void atualizar(Produto produto, String mensagem);
}
