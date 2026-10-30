package br.com.estoque.dominio;

// categoria do produto (Eletrônico, Alimento...). É uma classe e não um enum
// porque o administrador pode cadastrar categorias novas pelo menu
public class Categoria {

    private String nome;

    public Categoria(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
