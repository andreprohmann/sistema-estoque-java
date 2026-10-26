package br.com.estoque.padroes.estrutural;

import br.com.estoque.dominio.Perfil;
import br.com.estoque.dominio.Produto;
import br.com.estoque.padroes.comportamental.observer.AlertaConsoleObserver;
import br.com.estoque.padroes.comportamental.observer.EmailObserver;
import br.com.estoque.padroes.comportamental.observer.EstoqueObserver;
import br.com.estoque.padroes.comportamental.observer.HistoricoObserver;
import br.com.estoque.padroes.comportamental.observer.NotificadorEstoque;
import br.com.estoque.padroes.criacional.ProdutoBuilder;
import br.com.estoque.servicos.EstoqueServiceImpl;

import java.util.List;

/**
 * Única classe que o menu usa. Esconde o Builder, o Proxy, o serviço e os
 * observers por trás de métodos simples.
 *
 * @note Papel no Padrão: Facade
 * @note Papel no Padrão: Receiver (Command)
 */
public class EstoqueFacade {

    private NotificadorEstoque notificador = new NotificadorEstoque();
    private HistoricoObserver historico = new HistoricoObserver();
    private List<EstoqueObserver> canais = List.of(
            new AlertaConsoleObserver(),
            new EmailObserver("compras@empresa.com"),
            historico);
    private EstoqueServiceImpl servicoReal = new EstoqueServiceImpl(notificador);
    private EstoqueServiceProxy servico = new EstoqueServiceProxy(servicoReal);
    private String usuario;
    private Perfil perfil;

    public EstoqueFacade() {
        System.out.println("[Facade] Montando o sistema (serviço, proxy e observers).");
        carregarExemplos();
        notificador.adicionar(canais.get(0));
        notificador.adicionar(historico);
    }

    // os exemplos são cadastrados direto no serviço real, sem passar pelo proxy
    private void carregarExemplos() {
        servicoReal.cadastrar(new ProdutoBuilder().codigo(servicoReal.gerarCodigo())
                .nome("Mouse sem fio").categoria("Eletrônico").preco(89.90)
                .quantidade(25).fornecedor("TechParts").build());
        servicoReal.cadastrar(new ProdutoBuilder().codigo(servicoReal.gerarCodigo())
                .nome("Arroz 5kg").categoria("Alimento").preco(27.50)
                .quantidade(40).estoqueMinimo(10).build());
        servicoReal.cadastrar(new ProdutoBuilder().codigo(servicoReal.gerarCodigo())
                .nome("Camiseta").categoria("Vestuário").preco(39.90)
                .quantidade(4).build());
        servicoReal.cadastrar(new ProdutoBuilder().codigo(servicoReal.gerarCodigo())
                .nome("Detergente").categoria("Limpeza").preco(2.99)
                .quantidade(12).estoqueMinimo(10).build());
    }

    public void entrar(String usuario, Perfil perfil) {
        this.usuario = usuario;
        this.perfil = perfil;
        servico.setPerfil(perfil);
    }

    public String getUsuario() {
        return usuario;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public String gerarCodigo() {
        return servico.gerarCodigo();
    }

    public Produto cadastrarProduto(String codigo, String nome, String categoria, double preco,
                                    int quantidade, int estoqueMinimo, String fornecedor) {
        System.out.println("[Facade] Cadastrando produto.");
        Produto produto = new ProdutoBuilder()
                .codigo(codigo)
                .nome(nome)
                .categoria(categoria)
                .preco(preco)
                .quantidade(quantidade)
                .estoqueMinimo(estoqueMinimo)
                .fornecedor(fornecedor)
                .build();
        servico.cadastrar(produto);
        return produto;
    }

    public List<Produto> listarProdutos() {
        return servico.listar();
    }

    public Produto buscarProduto(String codigo) {
        return servico.buscar(codigo);
    }

    public void registrarEntrada(String codigo, int quantidade) {
        System.out.println("[Facade] Registrando entrada.");
        servico.entrada(codigo, quantidade);
    }

    public void registrarSaida(String codigo, int quantidade) {
        System.out.println("[Facade] Registrando saída.");
        servico.saida(codigo, quantidade);
    }

    public void alterarPreco(String codigo, double preco) {
        System.out.println("[Facade] Alterando preço.");
        servico.alterarPreco(codigo, preco);
    }

    public void removerProduto(String codigo) {
        System.out.println("[Facade] Removendo produto.");
        servico.remover(codigo);
    }

    public List<EstoqueObserver> getCanais() {
        return canais;
    }

    public boolean isCanalAtivo(EstoqueObserver canal) {
        return notificador.contem(canal);
    }

    public void alternarCanal(EstoqueObserver canal) {
        if (notificador.contem(canal)) {
            notificador.remover(canal);
        } else {
            notificador.adicionar(canal);
        }
    }

    public List<String> getHistorico() {
        return historico.getRegistros();
    }
}
