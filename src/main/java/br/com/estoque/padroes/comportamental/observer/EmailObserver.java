package br.com.estoque.padroes.comportamental.observer;

import br.com.estoque.dominio.Movimentacao;
import br.com.estoque.dominio.Produto;

/**
 * Simula um e-mail para o setor de compras pedindo reposição.
 *
 * @note Papel no Padrão: Concrete Observer
 */
public class EmailObserver implements EstoqueObserver {

    private String email; // endereço que "recebe" o e-mail

    public EmailObserver(String email) {
        this.email = email;
    }

    @Override
    public String getNome() {
        return "E-mail para compras";
    }

    @Override
    public void atualizar(Movimentacao movimentacao) {
        Produto produto = movimentacao.getProduto();
        // não envia e-mail de verdade, só mostra na tela o que seria enviado
        if (produto.isEstoqueBaixo()) {
            System.out.println("[E-mail para " + email + "] Repor o produto " + produto.getCodigo()
                    + " - " + produto.getNome());
        }
    }
}
