package controller;

import java.time.LocalDate;
import java.util.Scanner;

import model.Carteira;
import model.Categoria;
import view.CarteiraView;

public class CarteiraController {
    private final CarteiraView view;
    private final Carteira carteira;

    public CarteiraController(CarteiraView view, Carteira carteira) {
        this.view = view;
        this.carteira = carteira;
    }

    public void iniciar() {
        configurarCarteira(true);
        boolean executando = true;

        while (executando) {
            view.exibirMenu();
            String opcao = view.lerOpcao();

            switch (opcao) {
                case "1":
                    registrarGasto();
                    break;
                case "2":
                    registrarGanho();
                    break;
                case "3":
                    view.exibirTransacoes(carteira);
                    break;
                case "4":
                    view.exibirSaldo(carteira);
                    break;
                case "5":
                    configurarCarteira(false);
                    break;
                case "0":
                    executando = false;
                    view.exibirMensagem("Até mais!");
                    break;
                default:
                    view.exibirMensagem("Opção inválida.");
            }

        }
    }

    private void configurarCarteira(boolean registrarSalario) {
        double salario = view.lerSalarioMensal();
        double percentual = view.lerPercentualAlerta(salario);
        carteira.configurarSalario(salario, percentual);

        if (registrarSalario) {
            carteira.registrarGanho(
                    salario,
                    "Salário mensal",
                    Categoria.SALARIO,
                    LocalDate.now().toString());
        }

        view.exibirMensagem("Carteira configurada com sucesso.");
    }

    private void registrarGasto() {
        registrar(false);
    }

    private void registrarGanho() {
        registrar(true);
    }

    private void registrar(boolean ganho) {
        double valor = view.lerValor();
        String descricao = view.lerTexto("Descrição: ");
        Categoria categoria = view.lerCategoria();
        String data = view.lerTexto("Data (AAAA-MM-DD) ou Enter para hoje: ");
        if (data.isBlank()) {
            data = LocalDate.now().toString();
        }

        try {
            if (ganho) {
                carteira.registrarGanho(valor, descricao, categoria, data);
            } else {
                carteira.registrarGasto(valor, descricao, categoria, data);
            }
            view.exibirMensagem("Transação registrada com sucesso.");
            if (carteira.saldoAbaixoDaReserva()) {
                view.exibirAlertaSaldo(carteira);
            }
        } catch (IllegalArgumentException exception) {
            view.exibirMensagem("Não foi possível registrar: " + exception.getMessage());
        }
    }

    public static CarteiraController criarPadrao() {
        Scanner scanner = new Scanner(System.in);
        CarteiraView view = new CarteiraView(scanner);
        return new CarteiraController(view, new Carteira());
    }
}
