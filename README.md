# pooa-desafiotecnico
Desafio tecnico para a disciplina de Programação Orientada a Objetos Avançada do curso de Análise e desenvolvimento de Sistemas da Faculdade Princesa do Oeste, Campus Crateús.


DESAFIO TÉCNICO

Disciplina: Programação Orientada a Objetos Avançado
Curso: Análise e Desenvolvimento de Sistemas — 4o Semestre
Sistema de Agenda de Contatos via Console

Tecnologias Java · JDBC · Princípios SOLID
Modalidade Individual
Entrega Repositório Git (GitHub/GitLab)
Pontuação Total 100 pontos

1. Objetivo
Desenvolver um sistema de Agenda de Contatos executado integralmente via console (linha de
comando), utilizando a linguagem Java com acesso a banco de dados relacional por meio de
JDBC, respeitando os cinco princípios SOLID de design de software orientado a objetos.
Este desafio tem como finalidade avaliar a capacidade do aluno de integrar conceitos de
Programação Orientada a Objetos, modelagem de dados, boas práticas de arquitetura e
persistência de dados em um projeto coeso e funcional.
2. Contexto do Problema
Uma pequena empresa precisa de um sistema simples para gerenciar os contatos de seus
clientes. Você foi contratado como desenvolvedor júnior e deverá construir do zero uma
aplicação console em Java capaz de cadastrar, listar, atualizar e remover contatos, com todas
as informações persistidas em banco de dados.
O sistema deve ser robusto, bem organizado e de fácil manutenção, seguindo boas práticas de
engenharia de software.
3. Requisitos Funcionais
3.1 Entidade Contato
Cada contato deve armazenar, no mínimo, os seguintes dados:
• id — identificador único gerado automaticamente pelo banco
• nome — nome completo do contato (obrigatório)
• telefone — número de telefone, podendo ter mais de um por contato

Desafio Técnico — POO Avançado · 4o Semestre ADS Agenda de Contatos
• email — endereço de e-mail (deve ser único no sistema)
• categoria — classificação do contato (ex.: Amigo, Trabalho, Família, Outro)
• dataCadastro — data e hora em que o contato foi criado (gerada automaticamente)

3.2 Operações CRUD
O sistema deve oferecer as seguintes operações acessíveis por menu numérico:
1. Cadastrar Contato — inserir um novo contato com validação dos campos obrigatórios
2. Listar Todos os Contatos — exibir todos os contatos em formato tabular organizado
3. Buscar Contato — localizar contato por nome (busca parcial) ou por e-mail exato
4. Atualizar Contato — editar os dados de um contato existente (identificado pelo id)
5. Remover Contato — excluir permanentemente um contato (com confirmação do
usuário)
6. Listar por Categoria — filtrar e exibir contatos de uma categoria específica
7. Sair — encerrar o sistema

4. Requisitos Técnicos
4.1 Java e JDBC
• A aplicação deve ser desenvolvida em Java 11 ou superior
• Toda a comunicação com o banco de dados deve ser feita exclusivamente via JDBC
puro (sem frameworks ORM como Hibernate ou JPA)
• Utilizar banco de dados relacional — recomendado SQLite (arquivo local) ou
MySQL/PostgreSQL
• O script SQL de criação das tabelas deve ser incluído no repositório (arquivo
schema.sql)
• Utilizar PreparedStatement em todas as queries para evitar SQL Injection

• Gerenciar corretamente a abertura e fechamento de conexões (uso de try-with-
resources é recomendado)

• Tratar todas as SQLExceptions de forma adequada

4.2 Aplicação dos Princípios SOLID
A arquitetura do projeto deve demonstrar claramente a aplicação dos cinco princípios:

💡 S — Single Responsibility Principle (Responsabilidade Única)

Desafio Técnico — POO Avançado · 4o Semestre ADS Agenda de Contatos

Cada classe deve ter uma única razão para mudar.
Exemplo esperado: separar a classe de conexão ao banco (DatabaseConnection), a classe de
acesso a dados (ContatoDAO), a classe de negócio/validação (ContatoService) e a interface
com o usuário (ContatoMenu).

💡 O — Open/Closed Principle (Aberto/Fechado)
Classes abertas para extensão, fechadas para modificação.
Exemplo esperado: utilizar interfaces para os DAOs, permitindo que uma nova implementação
(ex.: para outro banco de dados) seja criada sem alterar o código existente.

💡 L — Liskov Substitution Principle (Substituição de Liskov)
Subclasses devem poder substituir suas classes-base sem quebrar o comportamento
esperado.
Exemplo esperado: se houver herança entre tipos de contatos (ex.: ContatoPessoal e
ContatoProfissional), o sistema deve funcionar corretamente utilizando a referência do tipo pai.

💡 I — Interface Segregation Principle (Segregação de Interfaces)
Nenhuma classe deve ser forçada a implementar métodos que não utiliza.
Exemplo esperado: criar interfaces específicas como ContatoRepository (CRUD básico) e
ContatoSearchRepository (métodos de busca), em vez de uma única interface gigante.

💡 D — Dependency Inversion Principle (Inversão de Dependências)
Módulos de alto nível não devem depender de módulos de baixo nível; ambos devem depender
de abstrações.
Exemplo esperado: ContatoService recebe a interface ContatoRepository por injeção de
dependência
(via construtor), e não instancia diretamente a implementação ContatoJdbcRepository.

5. Estrutura de Pacotes Sugerida
A organização abaixo é uma sugestão. O aluno pode adaptar conforme julgar necessário,
desde que a separação de responsabilidades seja mantida e justificada:

Pacote Responsabilidade
model Classes de domínio: Contato, Categoria

Desafio Técnico — POO Avançado · 4o Semestre ADS Agenda de Contatos

Pacote Responsabilidade
repository Interfaces dos DAOs (ContatoRepository, etc.)
repository.impl Implementações JDBC das interfaces
service Regras de negócio e validações
ui (ou console) Menus, leitura de entradas e saídas formatadas
database Classe de conexão e gerenciamento do banco
exception Exceções personalizadas da aplicação
Main.java Ponto de entrada — instancia e injeta dependências

6. Critérios de Avaliação
Critério Pontuação Descrição
Funcionamento do CRUD completo 25 pts Todas as 7 opções do menu
devem funcionar corretamente
Aplicação dos princípios SOLID 30 pts Cada princípio (S, O, L, I, D)
vale 6 pontos — avaliado na
estrutura e justificativa
Uso correto do JDBC 20 pts PreparedStatement, tratamento
de exceções, gerenciamento de
conexão

Qualidade do código 10 pts Nomenclatura, organização,
ausência de código duplicado
Script SQL e documentação 10 pts schema.sql funcional +
README com instruções de
execução

Apresentação e defesa oral 5 pts Capacidade de explicar as
decisões de design tomadas

7. Requisitos de Entrega
• Repositório público no GitHub
• Arquivo README.md com: descrição do projeto, instruções de configuração do banco,
como executar a aplicação e diagrama ou descrição da arquitetura
• Arquivo schema.sql na raiz do repositório com o script de criação das tabelas
• O projeto deve compilar e executar sem erros a partir das instruções do README
• Não é permitido o uso de frameworks ORM (Hibernate, JPA, MyBatis, etc.)

Desafio Técnico — POO Avançado · 4o Semestre ADS Agenda de Contatos
• Não é permitido o uso de frameworks de injeção de dependências (Spring, Guice, etc.)
— a injeção deve ser manual

8. Dicas e Orientações
8.1 Por onde começar
8. Crie o banco de dados e o schema.sql antes de escrever qualquer código Java
9. Implemente e teste a classe de conexão (DatabaseConnection) isoladamente
10. Defina as interfaces (repositórios) antes de implementá-las — pense no contrato
primeiro
11. Implemente o DAO com os métodos de CRUD e teste cada um antes de avançar
12. Crie a camada de serviço com as validações e só então construa os menus

8.2 Boas práticas recomendadas
• Use try-with-resources para garantir o fechamento de Connection, Statement e
ResultSet
• Crie uma classe de exceção customizada (ex.: ContatoNaoEncontradoException) para
tratar erros de negócio
• Valide entradas do usuário (campos obrigatórios, formato de e-mail, telefone não nulo)
• Evite lógica de negócio nas classes de UI — delegue para a camada de serviço
• Faça commits frequentes com mensagens descritivas (feat:, fix:, refactor:, etc.)

9. Funcionalidades Extras (Bônus)
As funcionalidades abaixo não são obrigatórias, mas podem somar pontos extras na avaliação:
• Exportar lista de contatos para arquivo .CSV
• Implementar paginação na listagem (exibir N contatos por vez)
• Suporte a múltiplos telefones por contato com tabela separada no banco
• Histórico de alterações: registrar em tabela de log cada modificação realizada
• Testes unitários com JUnit 5 para as classes de serviço

10. Conceito Final

Desafio Técnico — POO Avançado · 4o Semestre ADS Agenda de Contatos

Faixa de Pontos Conceito Descrição
90 – 100 pontos A — Excelente Domínio pleno de todos os requisitos e

princípios

75 – 89 pontos B — Bom Bom entendimento, pequenas lacunas na

aplicação do SOLID

60 – 74 pontos C — Regular CRUD funcional mas SOLID aplicado

superficialmente

Abaixo de 60 D — Insuficiente Requisitos básicos não atendidos ou projeto

não executa

Bom desafio! Demonstre o que aprendeu.
