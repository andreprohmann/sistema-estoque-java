package br.com.estoque.repositorio;

import br.com.estoque.dominio.Categoria;

import java.util.ArrayList;
import java.util.List;

// guarda as categorias cadastradas. Assim como o repositório de produtos,
// só guarda e devolve dados, as regras ficam no serviço
public class CategoriaRepositorio {

    private List<Categoria> categorias = new ArrayList<>();

    public void adicionar(Categoria categoria) {
        categorias.add(categoria);
    }

    // procura pelo nome sem diferenciar maiúscula e minúscula
    public Categoria buscar(String nome) {
        for (Categoria c : categorias) {
            if (c.getNome().equalsIgnoreCase(nome)) {
                return c;
            }
        }
        return null; // não achou
    }

    // devolve uma cópia para ninguém mexer na lista por fora
    public List<Categoria> listar() {
        return List.copyOf(categorias);
    }
}
