package br.com.estoque.padroes.comportamental.command;

import br.com.estoque.dominio.Categoria;
import br.com.estoque.padroes.estrutural.EstoqueFacade;
import br.com.estoque.util.Teclado;

/**
 * @note Papel no Padrão: Concrete Command
 */
public class CadastrarCategoriaCommand implements Command {

    private EstoqueFacade facade;

    public CadastrarCategoriaCommand(EstoqueFacade facade) {
        this.facade = facade;
    }

    @Override
    public String getNome() {
        return "Cadastrar categoria";
    }

    @Override
    public void executar() {
        // mostra as que já existem para o usuário não repetir
        System.out.println("Categorias atuais: " + facade.listarCategorias());
        String nome = Teclado.lerTexto("Nome da nova categoria: ");
        Categoria categoria = facade.cadastrarCategoria(nome); // só o administrador consegue (proxy)
        System.out.println("Categoria cadastrada: " + categoria);
    }
}
