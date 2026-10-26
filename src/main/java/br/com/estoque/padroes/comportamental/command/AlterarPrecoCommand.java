package br.com.estoque.padroes.comportamental.command;

import br.com.estoque.padroes.estrutural.EstoqueFacade;
import br.com.estoque.util.Teclado;

/**
 * @note Papel no Padrão: Concrete Command
 */
public class AlterarPrecoCommand implements Command {

    private EstoqueFacade facade;

    public AlterarPrecoCommand(EstoqueFacade facade) {
        this.facade = facade;
    }

    @Override
    public String getNome() {
        return "Alterar preço";
    }

    @Override
    public void executar() {
        String codigo = Teclado.lerTexto("Código do produto: ");
        double preco = Teclado.lerDouble("Novo preço: ");
        facade.alterarPreco(codigo, preco);
        System.out.println("Preço alterado.");
    }
}
