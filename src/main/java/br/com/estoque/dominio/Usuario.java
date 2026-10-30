package br.com.estoque.dominio;

// pessoa que está usando o sistema
public class Usuario {

    private String nome;
    private Perfil perfil;

    public Usuario(String nome, Perfil perfil) {
        this.nome = nome;
        this.perfil = perfil;
    }

    public String getNome() {
        return nome;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    @Override
    public String toString() {
        return nome + " (" + perfil + ")";
    }
}
