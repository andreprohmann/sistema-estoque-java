# Sistema de Estoque

Trabalho final da disciplina de Padrões de Projeto. É um sistema de controle de estoque em Java puro, usado pelo terminal através de um menu.

O sistema tem três perfis de usuário: administrador, operador e visitante. Ele controla o cadastro de produtos, as entradas e saídas e os alertas de estoque baixo.

## Padrões utilizados

| Padrão | Categoria | Problema resolvido no projeto |
|---|---|---|
| Builder | Criacional | O produto tem muitos campos, e alguns são opcionais (categoria, estoque mínimo e fornecedor). O `ProdutoBuilder` monta o produto por partes, coloca valores padrão nos campos não informados e valida os dados antes de criar o objeto. |
| Singleton | Criacional | O cadastro, as movimentações e as consultas precisam usar a mesma lista de produtos. O `EstoqueRepositorio` garante que exista só uma instância dessa lista. |
| Facade | Estrutural | Para cadastrar um produto é preciso usar o Builder, o Proxy, o serviço e os observers. A `EstoqueFacade` junta tudo isso em métodos simples, e o menu só conversa com ela. |
| Proxy | Estrutural | Cada perfil tem permissões diferentes: o visitante só consulta, o operador registra entradas e saídas e o administrador pode tudo. O `EstoqueServiceProxy` verifica o perfil antes de chamar o serviço real, e assim as regras de estoque não ficam misturadas com as de permissão. |
| Command | Comportamental | Cada opção do menu é uma classe que implementa `Command`. O `MenuInvoker` executa a opção escolhida, sem precisar de um `switch` grande no `Main`. |
| Observer | Comportamental | Quando o estoque muda, o `NotificadorEstoque` avisa os observers inscritos: o alerta na tela, o e-mail para compras e o histórico. O usuário pode ligar e desligar cada canal pelo menu. |

## Estrutura

```
src/main/java/br/com/estoque/
├── Main.java
├── dominio/      Produto, Perfil
├── padroes/
│   ├── criacional/       ProdutoBuilder, EstoqueRepositorio
│   ├── estrutural/       EstoqueFacade, EstoqueServiceProxy
│   └── comportamental/   command/ e observer/
├── servicos/     EstoqueService, EstoqueServiceImpl
└── util/         Teclado
```

## Como rodar

É preciso ter o JDK 17 ou superior.

```
mvn compile exec:java
```

Também dá para rodar sem o Maven:

```
javac -d out $(find src -name "*.java")
java -cp out br.com.estoque.Main
```

O sistema já começa com 4 produtos cadastrados. As mensagens que começam com `[Builder]`, `[Singleton]`, `[Facade]`, `[Proxy]`, `[Command]` e `[Observer]` mostram o momento em que cada padrão é usado.

## Autores

- Nome do aluno - RGM
