package br.com.estoque.padroes.comportamental.command;

import br.com.estoque.padroes.estrutural.EstoqueFacade;
import br.com.estoque.util.Teclado;

/**
 * @note Papel no Padrão: Concrete Command
 */
public class RemoverProdutoCommand implements Command {

    private EstoqueFacade facade;

    public RemoverProdutoCommand(EstoqueFacade facade) {
        this.facade = facade;
    }

    @Override
    public String getNome() {
        return "Remover produto";
    }

    @Override
    public void executar() {
        String codigo = Teclado.lerTexto("Código do produto: ");
        String confirma = Teclado.lerTexto("Tem certeza? (S/N): ");
        if (!confirma.equalsIgnoreCase("S")) {
            System.out.println("Cancelado.");
            return;
        }
        facade.removerProduto(codigo);
        System.out.println("Produto removido.");
    }
}
