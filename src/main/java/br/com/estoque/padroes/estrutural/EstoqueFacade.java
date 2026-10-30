package br.com.estoque.padroes.estrutural;

import br.com.estoque.dominio.Categoria;
import br.com.estoque.dominio.Movimentacao;
import br.com.estoque.dominio.Perfil;
import br.com.estoque.dominio.Produto;
import br.com.estoque.dominio.Usuario;
import br.com.estoque.padroes.comportamental.observer.AlertaConsoleObserver;
import br.com.estoque.padroes.comportamental.observer.EmailObserver;
import br.com.estoque.padroes.comportamental.observer.EstoqueObserver;
import br.com.estoque.padroes.comportamental.observer.HistoricoObserver;
import br.com.estoque.padroes.comportamental.observer.NotificadorEstoque;
import br.com.estoque.padroes.criacional.ProdutoBuilder;
import br.com.estoque.repositorio.CategoriaRepositorio;
import br.com.estoque.repositorio.EstoqueRepositorio;
import br.com.estoque.servicos.EstoqueService;
import br.com.estoque.servicos.EstoqueServiceImpl;
import br.com.estoque.servicos.Sessao;

import java.util.List;

/**
 * Única classe que o menu usa. Monta as peças do sistema e esconde o Builder,
 * o Proxy, o serviço e os observers por trás de métodos simples.
 *
 * @note Papel no Padrão: Facade
 * @note Papel no Padrão: Receiver (Command)
 */
public class EstoqueFacade {

    // Observer: o notificador e os três canais de notificação
    private NotificadorEstoque notificador = new NotificadorEstoque();
    private HistoricoObserver historico = new HistoricoObserver();
    private List<EstoqueObserver> canais = List.of(
            new AlertaConsoleObserver(),
            new EmailObserver("compras@empresa.com"),
            historico);

    private Sessao sessao = new Sessao();

    // o serviço real recebe os repositórios e o proxy "embrulha" o serviço real.
    // Os dois campos usam a interface EstoqueService
    private EstoqueService servicoReal = new EstoqueServiceImpl(EstoqueRepositorio.getInstancia(),
            new CategoriaRepositorio(), notificador);
    private EstoqueService servico = new EstoqueServiceProxy(servicoReal, sessao);

    public EstoqueFacade() {
        System.out.println("[Facade] Montando o sistema (serviço, proxy e observers).");
        carregarExemplos();
        // começa com o alerta na tela e o histórico ligados. O e-mail o usuário liga pelo menu
        notificador.adicionar(canais.get(0));
        notificador.adicionar(historico);
    }

    // os exemplos são cadastrados direto no serviço real, sem passar pelo proxy,
    // porque nesse momento ainda não tem nenhum usuário logado
    private void carregarExemplos() {
        Categoria eletronico = servicoReal.cadastrarCategoria("Eletrônico");
        Categoria alimento = servicoReal.cadastrarCategoria("Alimento");
        Categoria vestuario = servicoReal.cadastrarCategoria("Vestuário");
        Categoria limpeza = servicoReal.cadastrarCategoria("Limpeza");

        servicoReal.cadastrar(new ProdutoBuilder().nome("Mouse sem fio").categoria(eletronico)
                .preco(89.90).quantidade(25).fornecedor("TechParts").build());
        servicoReal.cadastrar(new ProdutoBuilder().nome("Arroz 5kg").categoria(alimento)
                .preco(27.50).quantidade(40).estoqueMinimo(10).build());
        servicoReal.cadastrar(new ProdutoBuilder().nome("Camiseta").categoria(vestuario)
                .preco(39.90).quantidade(4).build());
        servicoReal.cadastrar(new ProdutoBuilder().nome("Detergente").categoria(limpeza)
                .preco(2.99).quantidade(12).estoqueMinimo(10).build());
    }

    // troca o usuário da sessão. O proxy passa a usar o novo perfil
    public void entrar(String nome, Perfil perfil) {
        sessao.setUsuario(new Usuario(nome, perfil));
    }

    public Usuario getUsuario() {
        return sessao.getUsuario();
    }

    // usa o Builder para montar o produto e depois manda cadastrar pelo proxy
    public Produto cadastrarProduto(String nome, Categoria categoria, double preco,
                                    int quantidade, int estoqueMinimo, String fornecedor) {
        System.out.println("[Facade] Cadastrando produto.");
        Produto produto = new ProdutoBuilder()
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

    // os métodos abaixo só repassam a chamada para o proxy

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

    public Categoria cadastrarCategoria(String nome) {
        System.out.println("[Facade] Cadastrando categoria.");
        return servico.cadastrarCategoria(nome);
    }

    public List<Categoria> listarCategorias() {
        return servico.listarCategorias();
    }

    // métodos usados pela opção de ligar/desligar notificações

    public List<EstoqueObserver> getCanais() {
        return canais;
    }

    public boolean isCanalAtivo(EstoqueObserver canal) {
        return notificador.contem(canal);
    }

    // se o canal estiver ligado desliga, se estiver desligado liga
    public void alternarCanal(EstoqueObserver canal) {
        if (notificador.contem(canal)) {
            notificador.remover(canal);
        } else {
            notificador.adicionar(canal);
        }
    }

    public List<Movimentacao> getHistorico() {
        return historico.getMovimentacoes();
    }
}
