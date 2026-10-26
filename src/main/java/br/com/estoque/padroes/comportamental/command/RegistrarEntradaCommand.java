package br.com.estoque.padroes.comportamental.command;

import br.com.estoque.padroes.estrutural.EstoqueFacade;
import br.com.estoque.util.Teclado;

/**
 * @note Papel no Padrão: Concrete Command
 */
public class RegistrarEntradaCommand implements Command {

    private EstoqueFacade facade;

    public RegistrarEntradaCommand(EstoqueFacade facade) {
        this.facade = facade;
    }

    @Override
    public String getNome() {
        return "Registrar entrada";
    }

    @Override
    public void executar() {
        String codigo = Teclado.lerTexto("Código do produto: ");
        int quantidade = Teclado.lerInt("Quantidade: ");
        facade.registrarEntrada(codigo, quantidade);
        System.out.println("Entrada registrada.");
    }
}
