package br.com.estoque.padroes.estrutural;

import br.com.estoque.dominio.Categoria;
import br.com.estoque.dominio.Perfil;
import br.com.estoque.dominio.Produto;
import br.com.estoque.servicos.EstoqueService;
import br.com.estoque.servicos.Sessao;

import java.util.List;

/**
 * Verifica o perfil do usuário logado antes de repassar a chamada ao serviço
 * real. O que cada perfil pode fazer está definido no enum Perfil.
 *
 * @note Papel no Padrão: Proxy
 */
public class EstoqueServiceProxy implements EstoqueService {

    private EstoqueService servicoReal; // objeto que faz o trabalho de verdade
    private Sessao sessao;              // de onde o proxy descobre quem está logado

    public EstoqueServiceProxy(EstoqueService servicoReal, Sessao sessao) {
        this.servicoReal = servicoReal;
        this.sessao = sessao;
    }

    // todos os métodos seguem a mesma ideia: primeiro verifica a permissão,
    // se estiver tudo certo repassa para o serviço real

    @Override
    public void cadastrar(Produto produto) {
        verificar(perfil().podeGerenciarProdutos(), "cadastrar produto");
        servicoReal.cadastrar(produto);
    }

    // consultar é liberado para todos, então só repassa
    @Override
    public List<Produto> listar() {
        return servicoReal.listar();
    }

    @Override
    public Produto buscar(String codigo) {
        return servicoReal.buscar(codigo);
    }

    @Override
    public void entrada(String codigo, int quantidade) {
        verificar(perfil().podeMovimentar(), "registrar entrada");
        servicoReal.entrada(codigo, quantidade);
    }

    @Override
    public void saida(String codigo, int quantidade) {
        verificar(perfil().podeMovimentar(), "registrar saída");
        servicoReal.saida(codigo, quantidade);
    }

    @Override
    public void alterarPreco(String codigo, double preco) {
        verificar(perfil().podeGerenciarProdutos(), "alterar preço");
        servicoReal.alterarPreco(codigo, preco);
    }

    @Override
    public void remover(String codigo) {
        verificar(perfil().podeGerenciarProdutos(), "remover produto");
        servicoReal.remover(codigo);
    }

    @Override
    public Categoria cadastrarCategoria(String nome) {
        verificar(perfil().podeGerenciarProdutos(), "cadastrar categoria");
        return servicoReal.cadastrarCategoria(nome);
    }

    // ver as categorias é liberado para todos
    @Override
    public List<Categoria> listarCategorias() {
        return servicoReal.listarCategorias();
    }

    private Perfil perfil() {
        return sessao.getUsuario().getPerfil();
    }

    // se não tiver permissão lança uma exceção, que é tratada no MenuInvoker
    private void verificar(boolean permitido, String operacao) {
        if (!permitido) {
            System.out.println("[Proxy] " + perfil() + " tentou " + operacao + ": acesso negado.");
            throw new RuntimeException("Acesso negado: o perfil " + perfil() + " não pode " + operacao + ".");
        }
        System.out.println("[Proxy] " + perfil() + " pode " + operacao + ": acesso liberado.");
    }
}
