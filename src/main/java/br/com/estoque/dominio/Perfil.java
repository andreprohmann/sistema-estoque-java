package br.com.estoque.dominio;

// perfis de acesso e o que cada um pode fazer. O Proxy usa esses métodos
// para liberar ou bloquear as operações
public enum Perfil {
    ADMINISTRADOR(true, true), // pode fazer tudo
    OPERADOR(true, false),     // consulta e registra entrada/saída
    VISITANTE(false, false);   // só consulta

    private boolean podeMovimentar;
    private boolean podeGerenciarProdutos;

    Perfil(boolean podeMovimentar, boolean podeGerenciarProdutos) {
        this.podeMovimentar = podeMovimentar;
        this.podeGerenciarProdutos = podeGerenciarProdutos;
    }

    // registrar entrada e saída
    public boolean podeMovimentar() {
        return podeMovimentar;
    }

    // cadastrar, alterar preço e remover
    public boolean podeGerenciarProdutos() {
        return podeGerenciarProdutos;
    }
}
