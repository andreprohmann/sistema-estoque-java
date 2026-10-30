package br.com.estoque.servicos;

import br.com.estoque.dominio.Categoria;
import br.com.estoque.dominio.Movimentacao;
import br.com.estoque.dominio.Produto;
import br.com.estoque.padroes.comportamental.observer.NotificadorEstoque;
import br.com.estoque.repositorio.CategoriaRepositorio;
import br.com.estoque.repositorio.ProdutoRepositorio;

import java.util.List;

/**
 * Regras de negócio do estoque. Não verifica permissões, isso fica com o Proxy.
 *
 * @note Papel no Padrão: Real Subject (Proxy)
 */
public class EstoqueServiceImpl implements EstoqueService {

    // recebe a interface pelo construtor, assim não fica preso a uma classe concreta
    private ProdutoRepositorio repositorio;
    private CategoriaRepositorio categorias;
    // usado para avisar os observers sempre que algo muda
    private NotificadorEstoque notificador;
    private int ultimoCodigo = 0; // usado para gerar os códigos P001, P002...

    public EstoqueServiceImpl(ProdutoRepositorio repositorio, CategoriaRepositorio categorias,
                              NotificadorEstoque notificador) {
        this.repositorio = repositorio;
        this.categorias = categorias;
        this.notificador = notificador;
    }

    @Override
    public void cadastrar(Produto produto) {
        ultimoCodigo++;
        produto.setCodigo(String.format("P%03d", ultimoCodigo)); // %03d completa com zeros: 1 vira 001
        repositorio.adicionar(produto);
        notificador.notificar(new Movimentacao(produto, "Produto cadastrado"));
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
        validarQuantidade(quantidade);
        // soma a quantidade recebida ao que já tinha
        produto.setQuantidade(produto.getQuantidade() + quantidade);
        notificador.notificar(new Movimentacao(produto, "Entrada de " + quantidade + " un."));
    }

    @Override
    public void saida(String codigo, int quantidade) {
        Produto produto = buscarObrigatorio(codigo);
        validarQuantidade(quantidade);
        // não deixa o estoque ficar negativo
        if (quantidade > produto.getQuantidade()) {
            throw new IllegalArgumentException("Estoque insuficiente. Disponível: " + produto.getQuantidade());
        }
        produto.setQuantidade(produto.getQuantidade() - quantidade);
        // é aqui que normalmente aparece o alerta de estoque baixo
        notificador.notificar(new Movimentacao(produto, "Saída de " + quantidade + " un."));
    }

    @Override
    public void alterarPreco(String codigo, double preco) {
        Produto produto = buscarObrigatorio(codigo);
        if (preco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        produto.setPreco(preco);
        notificador.notificar(new Movimentacao(produto, String.format("Preço alterado para R$ %.2f", preco)));
    }

    @Override
    public void remover(String codigo) {
        Produto produto = buscarObrigatorio(codigo);
        repositorio.remover(produto);
        notificador.notificar(new Movimentacao(produto, "Produto removido"));
    }

    @Override
    public Categoria cadastrarCategoria(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome da categoria é obrigatório.");
        }
        // não deixa cadastrar duas categorias com o mesmo nome
        if (categorias.buscar(nome) != null) {
            throw new IllegalArgumentException("Já existe a categoria " + nome + ".");
        }
        Categoria categoria = new Categoria(nome);
        categorias.adicionar(categoria);
        return categoria;
    }

    @Override
    public List<Categoria> listarCategorias() {
        return categorias.listar();
    }

    // busca o produto e já dá erro se ele não existir, para não repetir isso em todo método
    private Produto buscarObrigatorio(String codigo) {
        Produto produto = repositorio.buscar(codigo);
        if (produto == null) {
            throw new IllegalArgumentException("Produto não encontrado: " + codigo);
        }
        return produto;
    }

    private void validarQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
    }
}
