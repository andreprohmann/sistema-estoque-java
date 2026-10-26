package br.com.estoque.padroes.comportamental.command;

import br.com.estoque.padroes.comportamental.observer.EstoqueObserver;
import br.com.estoque.padroes.estrutural.EstoqueFacade;
import br.com.estoque.util.Teclado;

import java.util.List;

/**
 * @note Papel no Padrão: Concrete Command
 */
public class GerenciarNotificacoesCommand implements Command {

    private EstoqueFacade facade;

    public GerenciarNotificacoesCommand(EstoqueFacade facade) {
        this.facade = facade;
    }

    @Override
    public String getNome() {
        return "Ligar/desligar notificações";
    }

    @Override
    public void executar() {
        List<EstoqueObserver> canais = facade.getCanais();
        for (int i = 0; i < canais.size(); i++) {
            String situacao = facade.isCanalAtivo(canais.get(i)) ? "ligado" : "desligado";
            System.out.println((i + 1) + " - " + canais.get(i).getNome() + " (" + situacao + ")");
        }
        int opcao = Teclado.lerInt("Escolha o canal (0 para voltar): ");
        if (opcao >= 1 && opcao <= canais.size()) {
            facade.alternarCanal(canais.get(opcao - 1));
        }
    }
}
