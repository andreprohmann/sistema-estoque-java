package br.com.estoque.padroes.comportamental.observer;

import br.com.estoque.dominio.Movimentacao;

import java.util.ArrayList;
import java.util.List;

/**
 * Registra todas as movimentações do estoque.
 *
 * @note Papel no Padrão: Concrete Observer
 */
public class HistoricoObserver implements EstoqueObserver {

    private List<Movimentacao> movimentacoes = new ArrayList<>();

    @Override
    public String getNome() {
        return "Histórico";
    }

    // diferente dos outros observers, este guarda todas as movimentações, não só os alertas
    @Override
    public void atualizar(Movimentacao movimentacao) {
        movimentacoes.add(movimentacao);
    }

    // devolve uma cópia para ninguém apagar o histórico por fora
    public List<Movimentacao> getMovimentacoes() {
        return List.copyOf(movimentacoes);
    }
}
