package br.com.estoque.padroes.comportamental.command;

/**
 * Cada opção do menu é um comando.
 *
 * @note Papel no Padrão: Command
 */
public interface Command {

    String getNome();

    void executar();
}
