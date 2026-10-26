package br.com.estoque.padroes.comportamental.observer;

import br.com.estoque.dominio.Produto;

import java.util.ArrayList;
import java.util.List;

/**
 * Guarda os observers inscritos e avisa todos quando o estoque muda.
 *
 * @note Papel no Padrão: Subject
 */
public class NotificadorEstoque {

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

    public void notificar(Produto produto, String mensagem) {
        if (observadores.isEmpty()) {
            return;
        }
        System.out.println("[Observer] Avisando " + observadores.size() + " observer(s): " + mensagem);
        for (EstoqueObserver o : observadores) {
            o.atualizar(produto, mensagem);
        }
    }
}
