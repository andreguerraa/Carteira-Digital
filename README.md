# Carteira Digital

Aplicação de console em Java para controle de ganhos, gastos e saldo pessoal.

## Funcionalidades

- Configuração do salário mensal;
- Percentual personalizado para alerta de saldo baixo, com padrão de 20%;
- Registro de gastos e ganhos;
- Cálculo do saldo com base nas transações;
- Listagem das transações;
- Alertas quando o saldo atinge o limite definido.

O projeto não bloqueia gastos que ultrapassem o saldo, pois funciona como um
relatório financeiro: as transações podem ser registradas mesmo depois de
realizadas.

## Tecnologias

- Java;
- MVC simples para aplicação de console;
- VS Code.

## Estrutura

```text
src/
├── App.java
├── controller/
│   └── CarteiraController.java
├── model/
│   ├── Carteira.java
│   ├── Categoria.java
│   └── Transacao.java
└── view/
    └── CarteiraView.java
```

## Como executar

É necessário ter o JDK instalado. Na raiz do projeto, execute:

```bash
mkdir -p bin
javac -d bin src/model/*.java src/view/*.java src/controller/*.java src/App.java
java -cp bin App
```

No Windows, o comando `mkdir -p bin` pode ser substituído por:

```cmd
mkdir bin
```

## Próximos passos

- Persistir as transações em arquivo ou banco de dados;
- Usar `BigDecimal` para valores financeiros;
- Substituir strings de tipo por um enum;
- Adicionar testes automatizados;
- Criar relatórios por categoria e período.
