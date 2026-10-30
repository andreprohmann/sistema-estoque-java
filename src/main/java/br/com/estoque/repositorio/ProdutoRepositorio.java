package br.com.estoque.repositorio;

import br.com.estoque.dominio.Produto;

import java.util.List;

// o serviço depende desta interface e não da classe concreta do repositório
public interface ProdutoRepositorio {

    void adicionar(Produto produto);

    Produto buscar(String codigo);

    void remover(Produto produto);

    List<Produto> listar();
}
