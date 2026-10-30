package br.com.estoque.padroes.comportamental.observer;

import br.com.estoque.dominio.Movimentacao;

/**
 * Todo canal de notificação implementa esta interface.
 *
 * @note Papel no Padrão: Observer
 */
public interface EstoqueObserver {

    // nome mostrado no menu de notificações
    String getNome();

    // chamado pelo NotificadorEstoque sempre que o estoque muda
    void atualizar(Movimentacao movimentacao);
}
