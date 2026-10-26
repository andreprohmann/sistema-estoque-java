package br.com.estoque.padroes.comportamental.observer;

import br.com.estoque.dominio.Produto;

/**
 * Mostra um alerta na tela quando o estoque de um produto fica baixo ou acaba.
 *
 * @note Papel no Padrão: Concrete Observer
 */
public class AlertaConsoleObserver implements EstoqueObserver {

    @Override
    public String getNome() {
        return "Alerta na tela";
    }

    @Override
    public void atualizar(Produto produto, String mensagem) {
        if (produto.getQuantidade() == 0) {
            System.out.println("*** ALERTA: " + produto.getNome() + " está ESGOTADO! ***");
        } else if (produto.isEstoqueBaixo()) {
            System.out.println("*** ALERTA: " + produto.getNome() + " está com estoque baixo ("
                    + produto.getQuantidade() + " un.) ***");
        }
    }
}
