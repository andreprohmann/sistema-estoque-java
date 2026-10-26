package br.com.estoque.padroes.comportamental.command;

import br.com.estoque.padroes.estrutural.EstoqueFacade;
import br.com.estoque.util.Teclado;

/**
 * @note Papel no Padrão: Concrete Command
 */
public class RegistrarSaidaCommand implements Command {

    private EstoqueFacade facade;

    public RegistrarSaidaCommand(EstoqueFacade facade) {
        this.facade = facade;
    }

    @Override
    public String getNome() {
        return "Registrar saída";
    }

    @Override
    public void executar() {
        String codigo = Teclado.lerTexto("Código do produto: ");
        int quantidade = Teclado.lerInt("Quantidade: ");
        facade.registrarSaida(codigo, quantidade);
        System.out.println("Saída registrada.");
    }
}
