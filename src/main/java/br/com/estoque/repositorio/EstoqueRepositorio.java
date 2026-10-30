package br.com.estoque.repositorio;

import br.com.estoque.dominio.Produto;

import java.util.ArrayList;
import java.util.List;

/**
 * Guarda a lista de produtos em memória. Existe uma única instância para todo
 * o sistema. Só guarda e devolve dados, as regras ficam no serviço.
 *
 * @note Papel no Padrão: Singleton
 */
public class EstoqueRepositorio implements ProdutoRepositorio {

    // guarda a única instância da classe
    private static EstoqueRepositorio instancia;

    private List<Produto> produtos = new ArrayList<>();

    // construtor privado: ninguém de fora consegue dar new nesta classe
    private EstoqueRepositorio() {
    }

    // único jeito de pegar o repositório. Na primeira chamada ele é criado,
    // nas próximas devolve o mesmo objeto
    public static EstoqueRepositorio getInstancia() {
        if (instancia == null) {
            instancia = new EstoqueRepositorio();
            System.out.println("[Singleton] Instância única do EstoqueRepositorio criada.");
        }
        return instancia;
    }

    @Override
    public void adicionar(Produto produto) {
        produtos.add(produto);
    }

    // procura pelo código sem diferenciar maiúscula e minúscula (p001 = P001)
    @Override
    public Produto buscar(String codigo) {
        for (Produto p : produtos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null; // não achou
    }

    @Override
    public void remover(Produto produto) {
        produtos.remove(produto);
    }

    // devolve uma cópia que não pode ser alterada, assim ninguém mexe
    // na lista sem passar pelo serviço
    @Override
    public List<Produto> listar() {
        return List.copyOf(produtos);
    }
}
