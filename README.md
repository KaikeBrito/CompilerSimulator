# Compiler Simulator (Scanner + Parser com JFlex/CUP)

Este projeto implementa um compilador básico que realiza análise léxica e sintática de um código‐fonte em C‐like, utilizando **JFlex** para gerar o *scanner* (analisador léxico) e **JavaCUP** para gerar o *parser* (analisador sintático). Ele está organizado de forma a possibilitar colaboradores compreenderem rapidamente como gerar os artefatos (scanner/parser), compilar o código‐fonte Java e executar testes para verificar o funcionamento correto.

---

## Sumário

1. [Visão Geral do Projeto](#visão-geral-do-projeto)
2. [Pré-requisitos](#pré-requisitos)
3. [Estrutura de Diretórios](#estrutura-de-diretórios)
4. [Instalação e Geração de Artefatos](#instalação-e-geração-de-artefatos)

   * 4.1. Configurar variáveis de ambiente (opcional)
   * 4.2. Gerar `Scanner.java` (JFlex)
   * 4.3. Gerar `parser.java` e `sym.java` (JavaCUP)
5. [Compilando o Projeto](#compilando-o-projeto)
6. [Executando e Testando](#executando-e-testando)

   * 6.1. Teste rápido com `input.txt`
   * 6.2. Executando em Windows (PowerShell/CMD)
   * 6.3. Executando em Linux/macOS (bash)
7. [Fluxo de Trabalho para Colaboradores](#fluxo-de-trabalho-para-colaboradores)

   * 7.1. Clonando o repositório
   * 7.2. Gerando e versionando artefatos
   * 7.3. Contribuindo / Pull Requests
8. [Dicas de Depuração](#dicas-de-depuração)
9. [Contato / Suporte](#contato--suporte)

---

## Visão Geral do Projeto

Este compilador mínimo possui as seguintes etapas:

1. **Análise Léxica (Scanner):**

   * Definida em `scanner.flex`. Gera `Scanner.java` com JFlex.
   * Reconhece tokens: palavras-chave (`if`, `else`, `int`, `float`, etc.), operadores aritméticos/lógicos, literais numéricos e identificadores.

2. **Análise Sintática (Parser):**

   * Definida em `parser.cup`. Gera `parser.java` e `sym.java` com JavaCUP.
   * Implementa uma gramática para estruturas de controle (if, for, while, return), declarações de variáveis/funções, expressões aritméticas/lógicas, arrays, listas de parâmetros, blocos, etc.

3. **Classe Principal (`Main.java`):**

   * Realiza a análise léxica (lista de tokens) e, em seguida, a análise sintática (mínima construção de árvore).
   * Exibe no console informações sobre tokens encontrados e passos de parsing (inclusive mensagens de sucesso/falha).

4. **Testes de Entrada:**

   * Um arquivo de exemplo `input.txt` (na pasta `compiler/`) para validar o funcionamento básico.

---

## Pré-requisitos

Para compilar e executar este projeto localmente, você precisará ter instalado:

1. **Java Development Kit (JDK) 8 ou superior**

   * Deve incluir `javac` e `java` no PATH.
   * Versão recomendada: OpenJDK 11, Oracle JDK 8+.

2. **JFlex (versão ≥ 1.9.1)**

   * [Site oficial JFlex](https://jflex.de/)
   * Opcionalmente, use o `.jar` fornecido: `lib/jflex-full-1.9.1.jar`.

3. **JavaCUP (versão 11b)**

   * [Site oficial JavaCUP](http://www2.cs.tum.edu/projects/cup/)
   * Utilize os jars fornecidos em `lib/java-cup-11b.jar` e `lib/java-cup-11b-runtime.jar`.

4. **Git (opcional, mas recomendado)**

   * Para clonar o repositório e colaborar.

> **Observação:** O repositório já inclui em `lib/` todos os `.jar` necessários (JFlex e JavaCUP). Basta apontar as variáveis de classpath corretamente.

---

## Estrutura de Diretórios

```
.
├── compiler/                   ← Pasta principal com todo o código‐fonte
│   ├── Scanner.flex            ← Definição do scanner (JFlex)
│   ├── parser.cup              ← Definição da gramática (JavaCUP)
│   ├── Main.java               ← Classe principal (analisa léxico + sintático)
│   ├── input.txt               ← Exemplo de arquivo de entrada para testes
│   ├── Scanner.java            ← (Gerado) Scanner JFlex  
│   ├── parser.java             ← (Gerado) Parser JCUP  
│   ├── sym.java                ← (Gerado) Contém constantes dos símbolos  
│   ├── *.class                 ← (Gerados) Arquivos compilados  
│   └── …                       ← Outros *.class de teste, se houver
│
├── lib/                        ← Dependências externas
│   ├── java-cup-11b.jar
│   ├── java-cup-11b-runtime.jar
│   ├── jflex-full-1.9.1.jar
│
├── README.md                   ← Este arquivo
└── .gitignore                  ← Arquivos/pastas ignorados pelo Git
```

* **`compiler/Scanner.flex`**
  Descreve as regras léxicas para gerar `Scanner.java` (usando JFlex).
* **`compiler/parser.cup`**
  Descreve a gramática e ações semânticas para gerar `parser.java` e `sym.java` (usando JavaCUP).
* **`compiler/Main.java`**
  Classe Java que:

  1. Invoca o scanner para listar tokens.
  2. Invoca o parser para validar sintaxe.
  3. Exibe mensagens no console.
* **`lib/`**
  Contém os arquivos `.jar` de JFlex e JavaCUP. São necessários para geração e execução do scanner/parser.
* **`input.txt`**
  Exemplo de programa a ser analisado. Pode ser alterado ou substituído por qualquer outro arquivo‐fonte.

---

## Instalação e Geração de Artefatos

Para gerar / atualizar **Scanner.java**, **parser.java** e **sym.java**, siga estes passos:

### 4.1. Configurar Variáveis de Ambiente (opcional)

* Se você tiver **JFlex** e **JavaCUP** instalados globalmente, pode configurar variáveis de ambiente apontando para os `.jar`.
* Caso contrário, usaremos diretamente os `.jar` dentro de `lib/`.

### 4.2. Gerar `Scanner.java` (JFlex)

No diretório raiz do projeto:

```bash
cd compiler
java -jar ../lib/jflex-full-1.9.1.jar Scanner.flex
```

* Isso criará (ou sobrescreverá) o arquivo `compiler/Scanner.java`.
* Caso a execução retorne erros de sintaxe no `.flex`, revise as definições de expressões regulares e ações (ex.: tokens duplicados, regex inválidas etc.).

### 4.3. Gerar `parser.java` e `sym.java` (JavaCUP)

Ainda no diretório `compiler/`:

```bash
java -jar ../lib/java-cup-11b.jar -parser parser -destdir . parser.cup
```

* O parâmetro `-parser parser` indica que o arquivo gerado para o parser se chamará `parser.java`.
* `-destdir .` define que os arquivos gerados (`parser.java` e `sym.java`) ficarão na mesma pasta `compiler`.
* Caso haja mensagens de **shift/reduce** ou **reduce/reduce conflicts**, revise as regras de precedência associadas no `.cup`.

---

## Compilando o Projeto

Depois de gerar os arquivos `Scanner.java`, `parser.java` e `sym.java`, basta compilar todos os arquivos Java dentro de `compiler/`, incluindo as classes geradas. Há dois cenários:

### 5.1. Compilação em Linux/macOS (bash)

```bash
# A partir da raiz do projeto:
javac -cp ".:lib/java-cup-11b-runtime.jar" compiler/*.java
```

### 5.2. Compilação em Windows (PowerShell ou CMD)

```powershell
# A partir da raiz do projeto
javac -cp ".;lib\java-cup-11b-runtime.jar" compiler\*.java
```

* O `-cp` inclui o JAR de runtime do JavaCUP, que é necessário no momento de compilação (pois a classe `parser.java` faz referência a pacotes do JavaCUP).
* Caso ocorra erro “cannot find symbol: sym” ou “cannot find symbol: parser”, verifique se você executou corretamente os passos de geração (`Scanner.java`, `parser.java`, `sym.java`) antes da compilação.

---

## Executando e Testando

Após compilar, você terá os arquivos `.class` dentro de `compiler/`. Agora, para executar a análise léxica e sintática em um arquivo‐fonte (por exemplo, `compiler/input.txt`), use:

### 6.1. Teste rápido com `input.txt`

O repositório já inclui um `input.txt` de exemplo. Para testar:

#### Linux/macOS (bash)

```bash
java -cp ".:lib/java-cup-11b-runtime.jar" compiler.Main compiler/input.txt
```

#### Windows (PowerShell/CMD)

```powershell
java -cp ".;lib\java-cup-11b-runtime.jar" compiler.Main compiler\input.txt
```

* A saída esperada deve listar os tokens lidos e mostrar as mensagens de parsing, encerrando com “✅ COMPILAÇÃO CONCLUÍDA COM SUCESSO!”.
* Se houver erros léxicos (token não reconhecido) ou sintáticos (gramática violada), serão exibidas mensagens de exceção no console.

### 6.2. Executando em Windows (PowerShell / CMD)

1. Abra o **PowerShell** (ou CMD) e navegue até a raiz do projeto.
2. Compile (caso ainda não tenha compilado):

   ```powershell
   javac -cp ".;lib\java-cup-11b-runtime.jar" compiler\*.java
   ```
3. Execute com um arquivo‐fonte qualquer (substitua “meu\_codigo.c” pelo seu arquivo):

   ```powershell
   java -cp ".;lib\java-cup-11b-runtime.jar" compiler.Main compiler\input.txt
   ```

### 6.3. Executando em Linux/macOS (bash)

1. No terminal, navegue até a raiz do projeto.
2. Compile:

   ```bash
   javac -cp ".:lib/java-cup-11b-runtime.jar" compiler/*.java
   ```
3. Execute:

   ```bash
   java -cp ".:lib/java-cup-11b-runtime.jar" compiler.Main compiler/input.txt
   ```

---

## Fluxo de Trabalho para Colaboradores

Esta seção descreve um guia robusto para que colaboradores possam clonar o repositório, gerar artefatos e testar localmente.

### 7.1. Clonando o repositório

```bash
git clone https://github.com/SEU_USUARIO/SEU_REPOSITORIO.git
cd SEU_REPOSITORIO
```

* Substitua `SEU_USUARIO/SEU_REPOSITORIO` pela URL real do projeto no GitHub.
* Após clonar, você terá a mesma estrutura de diretórios listada anteriormente.

### 7.2. Gerando e Versionando Artefatos

1. **Gerar Scanner.java**

   ```bash
   cd compiler
   java -jar ../lib/jflex-full-1.9.1.jar Scanner.flex
   ```

   * Adicione o `Scanner.java` ao controle de versão:

     ```bash
     git add compiler/Scanner.java
     git commit -m "Gerar Scanner.java via JFlex"
     ```

2. **Gerar parser.java e sym.java**

   ```bash
   java -jar ../lib/java-cup-11b.jar -parser parser -destdir . parser.cup
   ```

   * Versão do CUP gera:

     * `compiler/parser.java`
     * `compiler/sym.java`
   * Adicione os dois arquivos:

     ```bash
     git add compiler/parser.java compiler/sym.java
     git commit -m "Gerar parser.java e sym.java via JavaCUP"
     ```

3. **Compilar todos os `.java`** (para verificar se não há erros)

   ```bash
   javac -cp ".:../lib/java-cup-11b-runtime.jar" *.java
   ```

   * Se ocorrerem erros, revise o código gerado pelo JFlex/CUP antes de prosseguir.

4. **Testar manualmente**

   * Execute:

     ```bash
     java -cp ".:../lib/java-cup-11b-runtime.jar" compiler.Main input.txt
     ```
   * Verifique se a saída condiz com o esperado (tokens listados, parsing sem erros).

### 7.3. Contribuindo / Pull Requests

1. Crie uma nova *branch* para sua funcionalidade ou correção:

   ```bash
   git checkout -b feature/nova-gramatica
   ```
2. Faça alterações no `.cup` ou no `.flex`, gere novamente os artefatos, compile e teste.
3. Adicione e comite apenas os arquivos relevantes:

   ```bash
   git add compiler/parser.cup compiler/Scanner.flex
   git commit -m "Adiciona suporte a expressão X no parser"
   ```
4. Gere e versiona novamente:

   ```bash
   cd compiler
   java -jar ../lib/jflex-full-1.9.1.jar Scanner.flex
   java -jar ../lib/java-cup-11b.jar -parser parser -destdir . parser.cup
   git add Scanner.java parser.java sym.java
   git commit -m "Atualiza artefatos JFlex/CUP"
   ```
5. Push para seu fork/branch e abra um *Pull Request* no repositório principal.
6. Descreva no PR:

   * Qual foi a mudança feita (ex.: nova regra de gramática, refinamento de token, etc.).
   * Instruções de como testar (usar um `input.txt` específico, exemplo de código‐fonte, etc.).

---

## Dicas de Depuração

* **Erro de “cannot find symbol: sym.EOF”**

  * Verifique se você gerou o `sym.java` (por meio do JavaCUP) **antes** de compilar o `Main.java`.
  * Assegure-se de que o pacote `compiler` está correto em todos os arquivos (cabeçalhos `package compiler;`).

* **Conflito de nomes (“sym” na classe vs. variável local)**

  * Se você declarou `Symbol sym = lexer.next_token()`, é melhor renomear a variável local (ex.: `Symbol tok = lexer.next_token()`) para não conflitar com a classe estática `sym` gerada pelo JavaCUP.

* **Mensagens de SHIFT/REDUCE conflicts no JavaCUP**

  * Confira as diretivas de precedência no início de `parser.cup` (por exemplo, `precedence left PLUS, MINUS;`).
  * Ajuste ou adicione regras de associatividade / precedência para operações ambíguas.

* **Token não reconhecido (JFlex)**

  * Se o scanner encontrar um caractere que não bate com nenhum padrão, ele lança:

    ```
    throw new Error("Caractere ilegal: " + yytext());
    ```
  * Ajuste a expressão regular correspondente ou trate casos adicionais (ex.: strings, comentários, etc.).

* **Teste passo a passo**

  1. Comente temporariamente as ações semânticas em `parser.cup` para ver apenas a aceitação da gramática (sem imprimir mensagens).
  2. Insira `System.out.println` em pontos-chave de reduções para entender em qual regra o código está falhando.

---

## Contato / Suporte

* **Autor(es) / Mantenedor(es):**

  * Kaike Brito ([kaike.brito@example.com](mailto:kaike.brito@example.com))
  * Outros contribuidores ver “contributors” no GitHub.

* **Relatar Bugs / Feature Requests:**

  * Abra uma *Issue* no repositório:

    ```
    https://github.com/SEU_USUARIO/SEU_REPOSITORIO/issues
    ```
  * Descreva detalhadamente: versão do JDK, sistema operacional, conteúdo de `input.txt` de teste e mensagens de erro trafegadas.

* **Discussões e Duvidas Gerais:**

  * Utilize a seção “Discussions” (ou canal de comunicação interna do time).

---

> **Observação Final:**
> Este README foi elaborado para ser robusto e autoexplicativo. Caso encontre qualquer dificuldade, revise cada passo atentamente (especialmente a geração do scanner e do parser via JFlex/CUP) e verifique se as versões das ferramentas são compatíveis. Bom desenvolvimento!
