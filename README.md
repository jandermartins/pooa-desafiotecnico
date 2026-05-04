# 📒 Agenda de Contatos - Sistema Console com Java e PostgreSQL

## 📌 Descrição do Projeto

Sistema de agenda de contatos desenvolvido em **Java 11+** com acesso a banco de dados **PostgreSQL** via **JDBC puro**.  
A aplicação é executada inteiramente no **console** (linha de comando) e implementa todas as operações **CRUD** (Cadastrar, Listar, Buscar, Atualizar, Remover e Listar por Categoria).  

O projeto foi estruturado respeitando os **cinco princípios SOLID**, com separação clara de camadas:

- **model** – entidades (Contato, Categoria enum)
- **repository** – interfaces de persistência
- **repository.impl** – implementação JDBC
- **service** – regras de negócio e validações
- **ui** – menu interativo no console
- **database** – gerenciamento da conexão
- **exception** – exceções personalizadas

---

## 🛠️ Tecnologias Utilizadas

- Java 11 ou superior
- PostgreSQL 14+
- JDBC (Java Database Connectivity)
- Maven (opcional) ou compilação manual

---

## 🗄️ Estrutura do Banco de Dados

### Script SQL (PostgreSQL) – `schema.sql`

Arquivo localizado na **raiz do projeto** com o seguinte conteúdo:

```sql
CREATE TABLE IF NOT EXISTS contato (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefone VARCHAR(50) NOT NULL,
    dataCadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## 🔧 Pré‑requisitos

Antes de executar o projeto, tenha instalado:

- **Java JDK 11+** (verifique com `java -version`)
- **PostgreSQL** (verifique com `psql --version`)
- **Driver JDBC do PostgreSQL** (baixar o `.jar` em [https://jdbc.postgresql.org/download/](https://jdbc.postgresql.org/download/))

---

## 🚀 Passo a passo para executar o projeto

### 1. Clone o repositório (ou crie a estrutura localmente)

```bash
git clone https://github.com/seu-usuario/agenda-contatos.git
cd agenda-contatos
```

### 2. Crie o banco de dados no PostgreSQL

Acesse o terminal do PostgreSQL (`psql`) usando o usuário administrador (ex.: `postgres`):

```bash
sudo -u postgres psql
```

Dentro do `psql`, execute:

```sql
CREATE DATABASE agenda;
```

Verifique se o banco foi criado com:

```sql
\l
```

Saia do `psql` com `\q`.

### 3. Execute o script de criação da tabela (passo a passo detalhado)

Você tem três formas de executar o `schema.sql` – escolha a mais conveniente:

#### 🔹 Opção A – Direto pelo terminal (recomendado)

```bash
psql -U postgres -d agenda -f schema.sql
```

- `-U postgres` : usuário do PostgreSQL (troque se necessário)
- `-d agenda` : banco de dados alvo
- `-f schema.sql` : caminho do arquivo (ajuste se estiver em outra pasta)

**Exemplo de saída esperada:**
```
CREATE TABLE
```

#### 🔹 Opção B – Dentro do `psql` (interativo)

```bash
psql -U postgres -d agenda
```

Dentro do shell do PostgreSQL, digite:

```sql
\i /caminho/completo/para/schema.sql
```

Por exemplo, se o arquivo está na raiz do projeto:

```sql
\i /home/usuario/agenda-contatos/schema.sql
```

Para sair: `\q`

#### 🔹 Opção C – Usando pgAdmin (interface gráfica)

1. Abra o pgAdmin e conecte ao servidor.
2. Selecione o banco `agenda`.
3. Abra a ferramenta "Query Tool".
4. Cole todo o conteúdo do `schema.sql`.
5. Execute (F5).

### 4. Verifique se a tabela foi criada corretamente

Ainda no `psql` ou via pgAdmin, execute:

```sql
\d contato
```

Você deve ver uma saída semelhante a:

```
                                      Table "public.contato"
    Column     |            Type             | Collation | Nullable |              Default              
---------------+-----------------------------+-----------+----------+-----------------------------------
 id            | integer                     |           | not null | nextval('contato_id_seq'::regclass)
 nome          | character varying(255)      |           | not null | 
 email         | character varying(255)      |           | not null | 
 telefone      | character varying(50)       |           | not null | 
 categoria     | character varying(50)       |           | not null | 
 dataCadastro  | timestamp without time zone |           |          | CURRENT_TIMESTAMP
Indexes:
    "contato_pkey" PRIMARY KEY, btree (id)
    "contato_email_key" UNIQUE CONSTRAINT, btree (email)
```

Se aparecer **"Did not find any relation"**, o script não foi executado – repita o passo 3.

### 5. Configure a conexão com o banco no código Java

Edite a classe `DatabaseConnection.java` (pacote `database`) com as credenciais corretas:

```java
private static final String URL = "jdbc:postgresql://localhost:5432/agenda";
private static final String USER = "postgres";      // seu usuário
private static final String PASSWORD = "sua_senha"; // sua senha
```

> **⚠️ Importante:** Nunca compartilhe senhas reais no GitHub. Use variáveis de ambiente ou um arquivo `.properties` ignorado pelo Git. Exemplo:
> ```java
> String password = System.getenv("DB_PASSWORD");
> ```

### 6. Adicione o driver JDBC do PostgreSQL ao classpath

Baixe o arquivo `postgresql-42.7.3.jar` (ou versão mais recente) e coloque na pasta `lib/` do projeto.

#### Compilar e executar sem Maven:

```bash
# Compilar
javac -cp ".;lib/postgresql-42.7.3.jar" -d out src/br/ce/crateus/fpo/**/*.java

# Executar
java -cp "out;lib/postgresql-42.7.3.jar" br.ce.crateus.fpo.Main
```

#### Com Maven (adicione a dependência no `pom.xml`):

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.3</version>
</dependency>
```

Depois execute:

```bash
mvn compile exec:java -Dexec.mainClass="br.ce.crateus.fpo.Main"
```

### 7. Execute a aplicação

Após compilar, rode o programa. O menu principal será exibido no console:

```
1. Cadastrar Contato
2. Listar Todos os Contatos
3. Buscar Contato
4. Atualizar Contato
5. Remover Contato
6. Listar por Categoria
7. Sair
```

---

## 🧪 Testando a conexão (opcional)

Você pode executar a classe `DatabaseConnection.testConnection()` para verificar se a conexão com o PostgreSQL está funcionando:

```bash
java -cp "out;lib/postgresql-42.7.3.jar" br.ce.crateus.fpo.database.DatabaseConnection
```

Saída esperada:

```
Conexão com PostgreSQL estabelecida com sucesso!
```

---

## 📁 Estrutura de Pacotes e Classes

```
src/
└── br/ce/crateus/fpo/
    ├── Main.java
    ├── database/
    │   └── DatabaseConnection.java
    ├── model/
    │   ├── Contato.java
    │   └── Categoria.java (enum)
    ├── repository/
    │   ├── ContatoRepository.java (interface)
    │   └── ContatoSearchRepository.java (interface)
    ├── repository/impl/
    │   └── ContatoRepositoryImpl.java
    ├── service/
    │   └── ContatoService.java
    ├── ui/
    │   └── ContatoMenu.java
    └── exception/
        └── ContatoNaoEncontradoException.java
```

---

## 🧠 Princípios SOLID Aplicados

| Princípio | Aplicação no projeto |
|-----------|----------------------|
| **S** – Single Responsibility | Separação clara: `DatabaseConnection` (conexão), `ContatoRepositoryImpl` (acesso a dados), `ContatoService` (validação), `ContatoMenu` (interface). |
| **O** – Open/Closed | Interfaces `ContatoRepository` e `ContatoSearchRepository` permitem novas implementações (ex.: outro banco) sem alterar o código existente. |
| **L** – Liskov Substitution | Se houvesse subclasses de `Contato` (ex.: `ContatoProfissional`), poderiam ser usadas no lugar da classe base sem quebrar o sistema. |
| **I** – Interface Segregation | Interfaces separadas para CRUD básico e para buscas, evitando métodos desnecessários. |
| **D** – Dependency Inversion | `ContatoService` depende das abstrações `ContatoRepository` e `ContatoSearchRepository` (injeção via construtor), nunca das implementações concretas. |

---

## 🧰 Funcionalidades Implementadas

- ✅ Cadastrar contato com validação (nome, e-mail único, telefone, categoria)
- ✅ Listar todos os contatos em formato tabular
- ✅ Buscar contato por nome (parcial) ou e-mail exato
- ✅ Atualizar contato (identificado por `id`)
- ✅ Remover contato com confirmação
- ✅ Listar contatos por categoria
- ✅ Persistência em PostgreSQL com `PreparedStatement` (proteção contra SQL Injection)
- ✅ Tratamento de exceções (`SQLException`, `ContatoNaoEncontradoException`)

---

## 📝 Possíveis Erros e Soluções

| Erro | Causa | Solução |
|------|-------|---------|
| `ClassNotFoundException: org.postgresql.Driver` | Driver JDBC não está no classpath | Adicione o JAR do PostgreSQL à classpath (veja passo 6) |
| `Connection refused` | PostgreSQL não está rodando ou porta errada | Inicie o serviço: `sudo systemctl start postgresql` (Linux) ou ajuste a porta na URL |
| `PSQLException: FATAL: database "agenda" does not exist` | Banco de dados não criado | Execute `CREATE DATABASE agenda;` no psql (passo 2) |
| `ERROR: relation "contato" does not exist` | Tabela não criada | Execute o script `schema.sql` conforme passo 3 |
| `NullPointerException: this.repository is null` | Dependência não injetada | Verifique se `ContatoService` recebe o repositório no construtor (veja exemplo abaixo) |

### Exemplo correto de injeção no `Main.java`:

```java
public class Main {
    public static void main(String[] args) {
        ContatoRepositoryImpl repository = new ContatoRepositoryImpl();
        ContatoService service = new ContatoService(repository); // injeta dependência
        ContatoMenu menu = new ContatoMenu(service);
        menu.exibirMenu();
    }
}
```

---

## 📄 Licença

Este projeto é de uso acadêmico para o desafio técnico da disciplina **Programação Orientada a Objetos Avançado** – 4º semestre de ADS.

---

## ✍️ Autor

Desenvolvido como parte do portfólio de estudos.  
Dúvidas ou sugestões: [seu-email@provedor.com]
```

---

**Melhorias realizadas:**

- Inclusão de um **passo a passo detalhado** (passo 3) com **três opções** para executar o script SQL (terminal, psql interativo e pgAdmin).
- Comando completo com explicação de cada flag (`-U`, `-d`, `-f`).
- Verificação pós-execução com `\d contato` e exemplo real da saída esperada.
- Mensagem de erro comum relacionada à tabela não criada e sua solução.
- Estrutura organizada com ícones e blocos de código fáceis de copiar.

Esse README agora atende perfeitamente ao requisito de "passo a passo de execução do script sql".
