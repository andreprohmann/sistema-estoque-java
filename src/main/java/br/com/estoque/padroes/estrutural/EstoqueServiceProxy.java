package br.com.estoque.padroes.estrutural;

import br.com.estoque.dominio.Perfil;
import br.com.estoque.dominio.Produto;
import br.com.estoque.servicos.EstoqueService;

import java.util.List;

/**
 * Verifica o perfil do usuário antes de repassar a chamada ao serviço real.
 * Visitante só consulta, operador também movimenta o estoque e o
 * administrador pode tudo.
 *
 * @note Papel no Padrão: Proxy
 */
public class EstoqueServiceProxy implements EstoqueService {

    private EstoqueService servicoReal;
    private Perfil perfil = Perfil.VISITANTE;

    public EstoqueServiceProxy(EstoqueService servicoReal) {
        this.servicoReal = servicoReal;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public String gerarCodigo() {
        somenteAdministrador("cadastrar produto");
        return servicoReal.gerarCodigo();
    }

    @Override
    public void cadastrar(Produto produto) {
        somenteAdministrador("cadastrar produto");
        servicoReal.cadastrar(produto);
    }

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
        naoPodeSerVisitante("registrar entrada");
        servicoReal.entrada(codigo, quantidade);
    }

    @Override
    public void saida(String codigo, int quantidade) {
        naoPodeSerVisitante("registrar saída");
        servicoReal.saida(codigo, quantidade);
    }

    @Override
    public void alterarPreco(String codigo, double preco) {
        somenteAdministrador("alterar preço");
        servicoReal.alterarPreco(codigo, preco);
    }

    @Override
    public void remover(String codigo) {
        somenteAdministrador("remover produto");
        servicoReal.remover(codigo);
    }

    private void somenteAdministrador(String operacao) {
        if (perfil != Perfil.ADMINISTRADOR) {
            System.out.println("[Proxy] " + perfil + " tentou " + operacao + ": acesso negado.");
            throw new RuntimeException("Acesso negado: apenas o administrador pode fazer isso.");
        }
        System.out.println("[Proxy] " + perfil + " pode " + operacao + ": acesso liberado.");
    }

    private void naoPodeSerVisitante(String operacao) {
        if (perfil == Perfil.VISITANTE) {
            System.out.println("[Proxy] " + perfil + " tentou " + operacao + ": acesso negado.");
            throw new RuntimeException("Acesso negado: visitante só pode consultar.");
        }
        System.out.println("[Proxy] " + perfil + " pode " + operacao + ": acesso liberado.");
    }
}
