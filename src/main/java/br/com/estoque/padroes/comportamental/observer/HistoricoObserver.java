package br.com.estoque.padroes.comportamental.observer;

import br.com.estoque.dominio.Produto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Registra todas as movimentações do estoque.
 *
 * @note Papel no Padrão: Concrete Observer
 */
public class HistoricoObserver implements EstoqueObserver {

    private List<String> registros = new ArrayList<>();
    private DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    public String getNome() {
        return "Histórico";
    }

    @Override
    public void atualizar(Produto produto, String mensagem) {
        registros.add(LocalDateTime.now().format(formato) + " - " + produto.getCodigo() + " "
                + produto.getNome() + ": " + mensagem);
    }

    public List<String> getRegistros() {
        return registros;
    }
}
