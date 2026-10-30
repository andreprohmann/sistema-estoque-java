package br.com.estoque.padroes.comportamental.command;

import br.com.estoque.dominio.Movimentacao;
import br.com.estoque.padroes.estrutural.EstoqueFacade;

/**
 * @note Papel no Padrão: Concrete Command
 */
public class HistoricoCommand implements Command {

    private EstoqueFacade facade;

    public HistoricoCommand(EstoqueFacade facade) {
        this.facade = facade;
    }

    @Override
    public String getNome() {
        return "Ver histórico";
    }

    // mostra o que o HistoricoObserver foi guardando
    @Override
    public void executar() {
        if (facade.getHistorico().isEmpty()) {
            System.out.println("Nenhuma movimentação registrada.");
            return;
        }
        for (Movimentacao m : facade.getHistorico()) {
            System.out.println(m);
        }
    }
}
