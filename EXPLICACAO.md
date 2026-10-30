# Explicação do desenvolvimento

Este arquivo conta, passo a passo, como o sistema foi feito e por que tomamos cada decisão.

## 1. Escolha do tema

O tema escolhido foi um sistema de controle de estoque. Escolhemos esse tema porque ele tem regras de negócio reais e fáceis de entender:

- o produto tem preço, quantidade e um estoque mínimo;
- não pode sair mais do que tem no estoque;
- quando o estoque fica baixo, alguém precisa ser avisado para comprar mais;
- nem todo funcionário pode fazer tudo (um visitante não pode mudar preço, por exemplo).

Cada uma dessas regras virou um lugar onde um padrão de projeto faz sentido, como mostramos nos passos abaixo.

## 2. Organização das pastas

O enunciado pede separação entre Domínio, Padrões, Serviços e Main. Por isso o código foi dividido assim:

```
src/main/java/br/com/estoque/
├── Main.java          menu do sistema
├── dominio/           Produto, Categoria, Usuario, Perfil e Movimentacao (as "coisas" do negócio)
├── padroes/
│   ├── criacional/    Builder
│   ├── estrutural/    Facade e Proxy
│   └── comportamental/
│       ├── command/   Command e as opções do menu
│       └── observer/  Observer e os canais de notificação
├── repositorio/       onde os produtos (Singleton) e as categorias ficam guardados
├── servicos/          regras do estoque (entrada, saída, preço...) e a sessão
└── util/              leitura do teclado
```

Separar os padrões por categoria deixa fácil para o professor achar cada um. O repositório ficou numa pasta própria porque ele é a camada de dados: ele usa o Singleton, mas a função dele é guardar os produtos.

## 3. Domínio: Produto, Categoria, Usuario, Perfil e Movimentacao

Começamos pelo `Produto`, que é o centro do sistema. Ele guarda código, nome, categoria, preço, quantidade, estoque mínimo e fornecedor. Também tem o método `isEstoqueBaixo()`, que diz se a quantidade chegou no mínimo. Colocamos esse método no próprio produto porque é uma regra dele, e assim os observers só precisam perguntar ao produto.

A `Categoria` (Eletrônico, Alimento...) poderia ser um `enum`, mas um `enum` só tem os valores escritos no código. Como o administrador precisa cadastrar categorias novas pelo menu, ela virou uma classe, e as categorias ficam guardadas no `CategoriaRepositorio`. O sistema começa com 4 categorias. No cadastro do produto, o usuário escolhe a categoria numa lista numerada, e assim não tem como digitar uma categoria que não existe. O serviço também não deixa cadastrar duas categorias com o mesmo nome.

O `Perfil` é um `enum` com os três tipos de usuário: ADMINISTRADOR, OPERADOR e VISITANTE. Usamos `enum` porque são valores fixos, e assim não dá para digitar um perfil que não existe. O próprio `Perfil` diz o que pode fazer, pelos métodos `podeMovimentar()` e `podeGerenciarProdutos()`. Assim as permissões ficam definidas num lugar só, e o Proxy apenas consulta.

O `Usuario` junta o nome e o perfil de quem está usando o sistema. No começo esses dados ficavam soltos na Facade e no Proxy. Criar a entidade deixou isso organizado num objeto só.

A `Movimentacao` representa algo que aconteceu com um produto (cadastro, entrada, saída, alteração de preço ou remoção), com a data e a hora. É ela que o serviço manda para os observers. Antes o histórico guardava só texto. Com a entidade, o histórico guarda os dados de verdade.

## 4. Singleton: EstoqueRepositorio

**Problema:** o cadastro, a listagem, a entrada e a saída precisam usar a mesma lista de produtos. Se cada parte criasse a sua própria lista, um produto cadastrado não apareceria na listagem.

**Solução:** o `EstoqueRepositorio` tem o construtor `private`, então ninguém consegue usar `new` nele. O único jeito de pegar o repositório é pelo `getInstancia()`, que cria o objeto na primeira vez e depois devolve sempre o mesmo.

Decisões tomadas no repositório:

- O repositório só guarda e devolve produtos. Gerar o código do produto (P001, P002...) é regra de negócio, por isso fica no serviço.
- O `listar()` devolve uma cópia da lista que não pode ser alterada. Se devolvesse a lista original, qualquer classe poderia apagar produtos direto, sem passar pelo Proxy e sem avisar os observers.
- Criamos a interface `ProdutoRepositorio`. O serviço recebe essa interface pelo construtor, em vez de chamar `EstoqueRepositorio.getInstancia()` lá dentro. Isso é a inversão de dependência: a regra de negócio depende de uma abstração, e não da classe concreta. Quem entrega o Singleton para o serviço é a Facade, que é quem monta o sistema.

## 5. Builder: ProdutoBuilder

**Problema:** o construtor do `Produto` tem 7 parâmetros. Chamar `new Produto("P001", "Mouse", "Eletrônico", 89.9, 25, 5, "TechParts")` é confuso: é fácil trocar a quantidade pelo estoque mínimo sem perceber. Além disso, alguns campos são opcionais.

**Solução:** o `ProdutoBuilder` monta o produto por partes, com um método para cada campo:

```java
new ProdutoBuilder().nome("Mouse").preco(89.90).quantidade(25).build();
```

Decisões tomadas no Builder:

- Os campos opcionais já começam com valor padrão: estoque mínimo 5 e fornecedor "Não informado". Se o usuário apertar ENTER sem digitar nada, o valor padrão é mantido.
- A validação (nome e categoria obrigatórios, valores não negativos) fica no `build()`. Assim nenhum produto inválido chega a ser criado.
- Cada método devolve `this`, o que permite encadear as chamadas.

## 6. Observer: NotificadorEstoque e os canais

**Problema:** quando o estoque de um produto fica baixo, várias coisas precisam acontecer: mostrar um alerta, avisar o setor de compras e guardar no histórico. Se o serviço de estoque chamasse cada uma diretamente, toda vez que surgisse um aviso novo seria preciso mexer no serviço.

**Solução:** o serviço cria uma `Movimentacao` e só chama `notificador.notificar(movimentacao)`. O `NotificadorEstoque` (Subject) tem uma lista de observers e avisa todos eles. Cada observer decide o que fazer:

- `AlertaConsoleObserver`: mostra um alerta na tela se o estoque estiver baixo ou zerado;
- `EmailObserver`: simula um e-mail para compras pedindo reposição;
- `HistoricoObserver`: guarda todas as movimentações, não só os alertas.

Decisões tomadas no Observer:

- O e-mail é só simulado (aparece na tela), porque enviar e-mail de verdade fugiria do objetivo do trabalho.
- O enunciado pede que o usuário possa escolher o tipo de notificação. Por isso criamos a opção "Ligar/desligar notificações", que inscreve e remove observers com o sistema rodando. O e-mail começa desligado para dar para testar isso.

## 7. Proxy: EstoqueServiceProxy

**Problema:** cada perfil tem permissões diferentes:

- Administrador: pode fazer tudo.
- Operador: pode consultar e registrar entradas e saídas.
- Visitante: só pode consultar.

Se essas verificações ficassem dentro do serviço, ele misturaria regra de estoque com regra de permissão.

**Solução:** criamos a interface `EstoqueService`, implementada por duas classes:

- `EstoqueServiceImpl`: tem as regras de estoque e não sabe nada de permissão;
- `EstoqueServiceProxy`: verifica o perfil e, se estiver liberado, repassa a chamada para o serviço real.

Como os dois implementam a mesma interface, quem usa o serviço não percebe a diferença. Quando o acesso é negado, o proxy lança uma exceção com a mensagem, que é mostrada na tela pelo `MenuInvoker`.

Decisões tomadas no Proxy:

- O proxy descobre quem está logado pela `Sessao`, que guarda o `Usuario` atual. Quando o usuário troca de perfil, a Facade atualiza a sessão e o proxy já passa a usar o novo perfil.
- O proxy não decide sozinho o que cada perfil pode fazer: ele pergunta ao `Perfil` (`podeMovimentar()`, `podeGerenciarProdutos()`).
- A interface `EstoqueService` tem só as operações do estoque. Numa versão anterior existia também um `gerarCodigo()`, que servia só para barrar o visitante mais cedo, e o cadastro acabava conferindo a permissão duas vezes. Tiramos esse método: agora o código é gerado dentro do `cadastrar()` e a permissão é conferida uma vez só. O lado ruim é que o visitante só descobre que não pode cadastrar depois de digitar os dados.

## 8. Facade: EstoqueFacade

**Problema:** para cadastrar um produto é preciso usar o Builder, passar pelo Proxy, chamar o serviço e notificar os observers. Se o menu fizesse tudo isso, ele ficaria dependente de todas essas classes.

**Solução:** a `EstoqueFacade` cria e liga todas as peças no construtor e oferece métodos simples, como `cadastrarProduto`, `registrarSaida` e `alterarPreco`. Os comandos do menu só conhecem a Facade.

Os campos da Facade usam a interface `EstoqueService`, e não as classes concretas. A Facade é o único lugar que dá `new` nas peças do sistema.

A Facade também carrega 4 produtos de exemplo, para o sistema já começar com dados. Esses exemplos são cadastrados direto no serviço real, sem passar pelo Proxy, porque nesse momento ainda não tem ninguém logado.

## 9. Command: as opções do menu

**Problema:** um menu com 11 opções normalmente vira um `switch` enorme no `Main`, com a lógica de todas as opções misturada.

**Solução:** cada opção do menu é uma classe que implementa a interface `Command`, com os métodos `getNome()` e `executar()`. O `MenuInvoker` guarda os comandos num `Map` (número da opção → comando), mostra o menu e executa a opção escolhida.

Decisões tomadas no Command:

- Usamos `LinkedHashMap` para o menu aparecer na mesma ordem em que as opções foram adicionadas.
- O tratamento de erro fica num lugar só, no `MenuInvoker`. Qualquer erro (acesso negado, produto não encontrado, quantidade inválida) mostra a mensagem e volta para o menu, sem fechar o programa.
- Para criar uma opção nova basta criar outra classe de comando e registrar no `Main`.

## 10. Leitura do teclado: Teclado

Criamos a classe `Teclado` para não repetir o código de leitura em todos os comandos. Ela fica pedindo o valor de novo enquanto o usuário digitar algo inválido. O `lerDouble` aceita vírgula ou ponto, porque no Brasil é comum digitar "10,50".

## 11. Mostrando os padrões no terminal

O enunciado pede que fique claro quando cada padrão é usado. Por isso as classes dos padrões imprimem uma linha começando com o nome do padrão, por exemplo:

```
[Command] Executando RegistrarSaidaCommand
[Facade] Registrando saída.
[Proxy] OPERADOR pode registrar saída: acesso liberado.
[Observer] Avisando 3 observer(s): Saída de 3 un.
```

## 12. Documentação

Toda classe que faz parte de um padrão tem no Javadoc a tag `@note Papel no Padrão: ...`, como pede o enunciado. A tag foi registrada no `pom.xml` para o `mvn javadoc:javadoc` funcionar sem erro.

## 13. Fluxo completo de uma operação

Exemplo do que acontece quando um operador registra uma saída:

1. O usuário escolhe a opção 6 no menu.
2. O `MenuInvoker` encontra o `RegistrarSaidaCommand` e chama `executar()`.
3. O comando lê o código e a quantidade e chama `facade.registrarSaida(...)`.
4. A Facade repassa para o `EstoqueServiceProxy`.
5. O Proxy verifica que o perfil é OPERADOR e libera.
6. O `EstoqueServiceImpl` busca o produto no repositório (o `EstoqueRepositorio`, que é o Singleton) e diminui a quantidade.
7. O serviço cria uma `Movimentacao` e chama o `NotificadorEstoque`, que avisa os observers.
8. Se o estoque ficou baixo, aparece o alerta na tela e, se o canal estiver ligado, o e-mail para compras. O histórico registra a saída.
