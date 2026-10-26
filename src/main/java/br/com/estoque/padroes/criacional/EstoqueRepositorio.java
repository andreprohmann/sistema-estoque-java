package br.com.estoque.padroes.criacional;

import br.com.estoque.dominio.Produto;

import java.util.ArrayList;
import java.util.List;

/**
 * Guarda a lista de produtos. Existe uma única instância para todo o sistema.
 *
 * @note Papel no Padrão: Singleton
 */
public class EstoqueRepositorio {

    private static EstoqueRepositorio instancia;

    private List<Produto> produtos = new ArrayList<>();
    private int contador = 0;

    private EstoqueRepositorio() {
    }

    public static EstoqueRepositorio getInstancia() {
        if (instancia == null) {
            instancia = new EstoqueRepositorio();
            System.out.println("[Singleton] Instância única do EstoqueRepositorio criada.");
        }
        return instancia;
    }

    public String gerarCodigo() {
        contador++;
        return String.format("P%03d", contador);
    }

    public void adicionar(Produto produto) {
        produtos.add(produto);
    }

    public Produto buscar(String codigo) {
        for (Produto p : produtos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    public void remover(Produto produto) {
        produtos.remove(produto);
    }

    public List<Produto> listar() {
        return produtos;
    }
}
