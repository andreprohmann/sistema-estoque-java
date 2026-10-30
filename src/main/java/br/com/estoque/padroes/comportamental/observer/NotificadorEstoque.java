package br.com.estoque.padroes.comportamental.observer;

import br.com.estoque.dominio.Movimentacao;

import java.util.ArrayList;
import java.util.List;

/**
 * Guarda os observers inscritos e avisa todos quando o estoque muda.
 *
 * @note Papel no Padrão: Subject
 */
public class NotificadorEstoque {

    // lista dos observers que estão ligados no momento
    private List<EstoqueObserver> observadores = new ArrayList<>();

    public void adicionar(EstoqueObserver observador) {
        observadores.add(observador);
        System.out.println("[Observer] " + observador.getNome() + " ligado.");
    }

    public void remover(EstoqueObserver observador) {
        observadores.remove(observador);
        System.out.println("[Observer] " + observador.getNome() + " desligado.");
    }

    public boolean contem(EstoqueObserver observador) {
        return observadores.contains(observador);
    }

    // percorre a lista e chama o atualizar de cada observer
    public void notificar(Movimentacao movimentacao) {
        if (observadores.isEmpty()) {
            return; // ninguém para avisar
        }
        System.out.println("[Observer] Avisando " + observadores.size() + " observer(s): " + movimentacao.getDescricao());
        for (EstoqueObserver o : observadores) {
            o.atualizar(movimentacao);
        }
    }
}
