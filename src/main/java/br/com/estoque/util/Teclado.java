package br.com.estoque.util;

import java.util.Scanner;

// classe de apoio para ler o que o usuário digita no terminal
public class Teclado {

    // um único Scanner para o programa inteiro
    private static Scanner scanner = new Scanner(System.in);

    public static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim(); // trim tira os espaços das pontas
    }

    // fica pedindo de novo até o usuário digitar um número inteiro válido
    public static int lerInt(String mensagem) {
        while (true) {
            try {
                return Integer.parseInt(lerTexto(mensagem));
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido, digite um número inteiro.");
            }
        }
    }

    // igual ao lerInt, mas aceita vírgula ou ponto (10,50 ou 10.50)
    public static double lerDouble(String mensagem) {
        while (true) {
            try {
                return Double.parseDouble(lerTexto(mensagem).replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido, digite um número (ex: 10,50).");
            }
        }
    }
}
