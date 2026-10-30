package br.com.estoque.padroes.comportamental.command;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Guarda os comandos do menu e executa o que o usuário escolher.
 *
 * @note Papel no Padrão: Invoker
 */
public class MenuInvoker {

    // número da opção -> comando. O LinkedHashMap mantém a ordem em que foram adicionados
    private Map<Integer, Command> comandos = new LinkedHashMap<>();

    public void adicionar(int opcao, Command comando) {
        comandos.put(opcao, comando);
    }

    public void mostrarMenu() {
        for (Map.Entry<Integer, Command> item : comandos.entrySet()) {
            System.out.println(item.getKey() + " - " + item.getValue().getNome());
        }
        System.out.println("0 - Sair");
    }

    public void executar(int opcao) {
        Command comando = comandos.get(opcao);
        if (comando == null) {
            System.out.println("Opção inválida.");
            return;
        }
        System.out.println("[Command] Executando " + comando.getClass().getSimpleName());
        // os erros (acesso negado, produto não encontrado etc.) são tratados aqui
        // num lugar só, assim o programa não fecha e volta para o menu
        try {
            comando.executar();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
