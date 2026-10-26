package br.com.estoque.servicos;

import br.com.estoque.dominio.Produto;
import br.com.estoque.padroes.comportamental.observer.NotificadorEstoque;
import br.com.estoque.padroes.criacional.EstoqueRepositorio;

import java.util.List;

/**
 * Regras de negócio do estoque. Não verifica permissões, isso fica com o Proxy.
 *
 * @note Papel no Padrão: Real Subject (Proxy)
 */
public class EstoqueServiceImpl implements EstoqueService {

    private EstoqueRepositorio repositorio = EstoqueRepositorio.getInstancia();
    private NotificadorEstoque notificador;

    public EstoqueServiceImpl(NotificadorEstoque notificador) {
        this.notificador = notificador;
    }

    @Override
    public String gerarCodigo() {
        return repositorio.gerarCodigo();
    }

    @Override
    public void cadastrar(Produto produto) {
        repositorio.adicionar(produto);
        notificador.notificar(produto, "Produto cadastrado");
    }

    @Override
    public List<Produto> listar() {
        return repositorio.listar();
    }

    @Override
    public Produto buscar(String codigo) {
        return repositorio.buscar(codigo);
    }

    @Override
    public void entrada(String codigo, int quantidade) {
        Produto produto = buscarObrigatorio(codigo);
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        produto.setQuantidade(produto.getQuantidade() + quantidade);
        notificador.notificar(produto, "Entrada de " + quantidade + " un.");
    }

    @Override
    public void saida(String codigo, int quantidade) {
        Produto produto = buscarObrigatorio(codigo);
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        if (quantidade > produto.getQuantidade()) {
            throw new IllegalArgumentException("Estoque insuficiente. Disponível: " + produto.getQuantidade());
        }
        produto.setQuantidade(produto.getQuantidade() - quantidade);
        notificador.notificar(produto, "Saída de " + quantidade + " un.");
    }

    @Override
    public void alterarPreco(String codigo, double preco) {
        Produto produto = buscarObrigatorio(codigo);
        if (preco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        produto.setPreco(preco);
        notificador.notificar(produto, String.format("Preço alterado para R$ %.2f", preco));
    }

    @Override
    public void remover(String codigo) {
        Produto produto = buscarObrigatorio(codigo);
        repositorio.remover(produto);
        notificador.notificar(produto, "Produto removido");
    }

    private Produto buscarObrigatorio(String codigo) {
        Produto produto = repositorio.buscar(codigo);
        if (produto == null) {
            throw new IllegalArgumentException("Produto não encontrado: " + codigo);
        }
        return produto;
    }
}
