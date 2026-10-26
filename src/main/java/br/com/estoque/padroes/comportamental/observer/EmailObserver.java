package br.com.estoque.padroes.comportamental.observer;

import br.com.estoque.dominio.Produto;

/**
 * Simula um e-mail para o setor de compras pedindo reposição.
 *
 * @note Papel no Padrão: Concrete Observer
 */
public class EmailObserver implements EstoqueObserver {

    private String email;

    public EmailObserver(String email) {
        this.email = email;
    }

    @Override
    public String getNome() {
        return "E-mail para compras";
    }

    @Override
    public void atualizar(Produto produto, String mensagem) {
        if (produto.isEstoqueBaixo()) {
            System.out.println("[E-mail para " + email + "] Repor o produto " + produto.getCodigo()
                    + " - " + produto.getNome());
        }
    }
}
