package br.com.estoque.dominio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// algo que aconteceu com um produto (cadastro, entrada, saída...).
// É o que o serviço envia para os observers
public class Movimentacao {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private Produto produto;
    private String descricao;
    private LocalDateTime dataHora;

    public Movimentacao(Produto produto, String descricao) {
        this.produto = produto;
        this.descricao = descricao;
        this.dataHora = LocalDateTime.now(); // registra o momento em que aconteceu
    }

    public Produto getProduto() {
        return produto;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    // linha mostrada no histórico
    @Override
    public String toString() {
        return dataHora.format(FORMATO) + " - " + produto.getCodigo() + " " + produto.getNome() + ": " + descricao;
    }
}
