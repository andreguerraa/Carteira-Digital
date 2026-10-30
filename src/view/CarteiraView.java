package view;

import java.util.Scanner;

import model.Carteira;
import model.Categoria;
import model.Transacao;

public class CarteiraView {
    private final Scanner scanner;

    public CarteiraView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void exibirMenu() {
        System.out.println("\n=== Controle de gastos ===");
        System.out.println("1. Registrar gasto");
        System.out.println("2. Registrar ganho");
        System.out.println("3. Listar transações");
        System.out.println("4. Ver saldo");
        System.out.println("5. Alterar salário e alerta");
        System.out.println("0. Sair");
    }

    public double lerSalarioMensal() {
        System.out.println("Vamos configurar sua carteira.");
        while (true) {
            System.out.print("Salário mensal: R$ ");
            try {
                double salario = Double.parseDouble(scanner.nextLine().replace(',', '.'));
                if (salario > 0) {
                    return salario;
                }
            } catch (NumberFormatException exception) {
                // A mensagem abaixo orienta o usuário a tentar novamente.
            }
            System.out.println("Digite um salário positivo.");
        }
    }

    public double lerPercentualAlerta(double salario) {
        double percentualPadrao = Carteira.getPercentualAlertaPadrao();
        double limitePadrao = salario * percentualPadrao / 100;
        System.out.printf("Por padrão, o alerta será de %.0f%% (R$ %.2f).%n",
                percentualPadrao, limitePadrao);
        System.out.print("Percentual personalizado ou Enter para usar o padrão: ");
        String entrada = scanner.nextLine().trim();

        if (entrada.isBlank()) {
            return percentualPadrao;
        }

        while (true) {
            try {
                double percentual = Double.parseDouble(entrada.replace(',', '.'));
                if (percentual > 0 && percentual <= 100) {
                    System.out.printf("O alerta será ativado em R$ %.2f.%n",
                            salario * percentual / 100);
                    return percentual;
                }
            } catch (NumberFormatException exception) {
                // A mensagem abaixo orienta o usuário a tentar novamente.
            }
            System.out.print("Digite um percentual entre 0 e 100: ");
            entrada = scanner.nextLine().trim().replace(',', '.');
        }
    }

    public String lerOpcao() {
        System.out.print("Escolha uma opção: ");
        return scanner.nextLine().trim();
    }

    public double lerValor() {
        while (true) {
            System.out.print("Valor: ");
            try {
                return Double.parseDouble(scanner.nextLine().replace(',', '.'));
            } catch (NumberFormatException exception) {
                System.out.println("Digite um valor numérico válido.");
            }
        }
    }

    public String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    public Categoria lerCategoria() {
        Categoria[] categorias = Categoria.values();

        System.out.println("Categorias:");
        for (int i = 0; i < categorias.length; i++) {
            System.out.println((i + 1) + ". " + categorias[i]);
        }

        while (true) {
            System.out.print("Escolha uma categoria: ");
            try {
                int opcao = Integer.parseInt(scanner.nextLine());
                if (opcao >= 1 && opcao <= categorias.length) {
                    return categorias[opcao - 1];
                }
            } catch (NumberFormatException exception) {
                // A mensagem abaixo orienta o usuário a tentar novamente.
            }
            System.out.println("Escolha uma categoria válida.");
        }
    }

    public void exibirTransacoes(Carteira carteira) {
        if (carteira.getTransacoes().isEmpty()) {
            System.out.println("Nenhuma transação registrada.");
            return;
        }

        System.out.println("\n=== Transações ===");
        for (Transacao transacao : carteira.getTransacoes()) {
            System.out.printf("#%d | %s | R$ %.2f | %s | %s | %s%n",
                    transacao.getId(),
                    transacao.getTipo(),
                    transacao.getValor(),
                    transacao.getDescricao(),
                    transacao.getCategoria(),
                    transacao.getData());
        }
    }

    public void exibirSaldo(Carteira carteira) {
        System.out.printf("%nSaldo atual: R$ %.2f%n", carteira.calcularSaldo());
        if (carteira.saldoAbaixoDaReserva()) {
            exibirAlertaSaldo(carteira);
        }
    }

    public void exibirAlertaSaldo(Carteira carteira) {
        System.out.printf("Aviso: seu saldo está em até %.1f%% do salário mensal " +
                "(limite de R$ %.2f).%n",
                carteira.getPercentualAlerta(),
                carteira.getLimiteAlerta());
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }
}
