# Versão 2.0 - Apontamentos Online

---

## 1. Descrição 

---

Este projeto é um formulário que serve para a utilização de apontamentos de hora 
para oficinas. O intuito dele é otimizar um processo reduzindo a utilização de folhas de papel e facilitar 
o acesso a essas informações, além de evitar perda de dados importantes.

## 2. Por quê usar?

---

* Permite otimizar seu tempo para tarefas mais importantes dentro de uma oficina.
* Reduz a quantidade de papel consumida, o que é benéfico para seu bolso e para o meio ambiente.
* Maior facilidade de gerenciar dados, não será mais necessário cuidar de caixas e caixas de papel. Além de reduzir 
grandemente a perca de dados.


## 3. Funcionalidades

---

* Sistema de login
* Possibilidade de consultar os apontamentos feitos
* Formulário para criação de apontamento



## 4. Tecnologias

---

Esse projeto foi construído utilizando as seguintes tecnologias:
* linguagens: 
  * Java
  * Java Script
  * HTML
  * CSS
  <br></br>
* Banco de dados:
  * MySQL
  <br></br>
* Frameworks:
  * Spring Boot
  <br></br>
* Bibliotecas:
  * Spring Security
  * Spring Web
  * Spring Data JPA
  * Thymeleaf
  <br></br>

## 5. Pré-requisitos

---

Pré requisitos de versão das tecnologias usadas.

```
    IntelliJ IDEA Community Edition : 2025.2.5 ou superior
    Java :                 21.0.9
    MySQL-connector :      9.5.0
    MySQL :                9.4
    Spring-boot :          4.0.1
    Spring-boot-jpa:       4.0.1
    Spring-boot-security:  4.0.1
    Spring-boot-thymeleaf: 4.0.1
    Spring-boot-webmvc:    4.0.1
    Spring-web:            7.0.2
```

## 7. Análise de Dados e Relatórios

Para viabilizar a análise estratégica dos dados, a aplicação está conectada ao Railway, um provedor de nuvem que oferece
excelente custo-benefício e estabilidade para operações deste porte. Embora o sistema utilize o Railway, a estrutura foi
desenhada para ser compatível com qualquer outro grande provedor de nuvem.

### Interface de Consulta no Excel
A extração e filtragem das informações são feitas diretamente pelo Excel, utilizando uma tabela dinâmica chamada 
"Filtros". Esta tabela funciona como o painel de controle do usuário, onde são inseridos três parâmetros essenciais:

* Unidade: Define a sede ou localidade específica da empresa.

* Data Início e Data Final: Delimitam o período exato para a análise dos apontamentos.

O sistema é flexível, permitindo a inclusão futura de novos filtros, como número de matrícula ou categorias específicas,
conforme a necessidade da operação.

### Segurança e Acesso
A conexão é estabelecida através da função "Obter Dados" do MySQL, utilizando o endereço do servidor e o banco de dados 
hospedado. Por questões de segurança, o acesso é feito obrigatoriamente pelo usuário leitor_excel. Este usuário possui 
permissões restritas apenas para leitura (consultas SQL), o que impede qualquer alteração ou exclusão acidental dos
dados originais por parte do usuário final.

### Automação do Filtro (Power Query)
Para que o Excel "converse" com o banco de dados e entenda os filtros aplicados, utilizamos o código abaixo no Editor 
Avançado. Ele é responsável por capturar o que foi digitado na tabela "Filtros", tratar as informações 
(como converter textos para minúsculo para evitar erros de digitação) e buscar no servidor apenas os registros que 
batem com os critérios:


```
    let

    FiltroTabela = Excel.CurrentWorkbook(){[Name="Filtros"]}[Content],
    LocalidadeAlvo = Text.Lower(Text.From(FiltroTabela{0}[localidade])),
    DataInicio = Date.From(FiltroTabela{0}[data inicio]),
    DataFim = Date.From(FiltroTabela{0}[data fim]),

    Fonte = MySQL.Database("gondola.proxy.rlwy.net:39699", "railway", [ReturnSingleDatabase=true]),
    TabelaBanco = Fonte{[Schema="railway", Item="apontamentos"]}[Data],

    DadosFiltrados = Table.SelectRows(TabelaBanco, each 
        (Text.Lower([unidade]) = LocalidadeAlvo) and 
        (Date.From([data]) >= DataInicio) and 
        (Date.From([data]) <= DataFim)
    )
    in
        DadosFiltrados

```



### Resultado Final
Após a configuração inicial, o processo de uso é imediato: o usuário altera os valores de unidade ou data na planilha e 
clica em Atualizar. O sistema processa a requisição na nuvem e retorna o relatório filtrado instantaneamente, garantindo
uma análise rápida, segura e sem burocracia técnica.

