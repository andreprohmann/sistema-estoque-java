package br.com.estoque.padroes.comportamental.command;

/**
 * Cada opção do menu é um comando.
 *
 * @note Papel no Padrão: Command
 */
public interface Command {

    // texto que aparece no menu
    String getNome();

    // o que a opção faz quando é escolhida
    void executar();
}
