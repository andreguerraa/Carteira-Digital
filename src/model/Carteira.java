package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Carteira {
    private static final double PERCENTUAL_ALERTA_PADRAO = 20.0;
    private List<Transacao> transacoes = new ArrayList<>();
    private double salarioMensal;
    private double percentualAlerta;

    public void configurarSalario(double salarioMensal, double percentualAlerta) {
        if (salarioMensal <= 0) {
            throw new IllegalArgumentException("O salário mensal deve ser positivo.");
        }
        if (percentualAlerta <= 0 || percentualAlerta > 100) {
            throw new IllegalArgumentException(
                    "O percentual de alerta deve estar entre 0 e 100.");
        }

        this.salarioMensal = salarioMensal;
        this.percentualAlerta = percentualAlerta;
    }

    public void registrarGasto(double valor, String descricao, Categoria categoria,
            String data) {
        registrarTransacao(valor, descricao, categoria, data, "saida");
    }

    public void registrarGanho(double valor, String descricao, Categoria categoria,
            String data) {
        registrarTransacao(valor, descricao, categoria, data, "entrada");
    }

    public void registrarTransacao(double valor, String descricao, Categoria categoria,
            String data, String tipo) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor da transação deve ser positivo.");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição da transação é obrigatória.");
        }
        if (categoria == null) {
            throw new IllegalArgumentException("A categoria da transação é obrigatória.");
        }
        if (data == null || data.isBlank()) {
            throw new IllegalArgumentException("A data da transação é obrigatória.");
        }
        if (!"entrada".equals(tipo) && !"saida".equals(tipo)) {
            throw new IllegalArgumentException("O tipo deve ser 'entrada' ou 'saida'.");
        }
        transacoes.add(new Transacao(valor, descricao, categoria, data, tipo));
    }

    public List<Transacao> getTransacoes() {
        return Collections.unmodifiableList(transacoes);
    }

    public double calcularSaldo() {
        double saldo = 0;

        for (Transacao transacao : transacoes) {
            if ("entrada".equals(transacao.getTipo())) {
                saldo += transacao.getValor();
            } else {
                saldo -= transacao.getValor();
            }
        }

        return saldo;
    }

    public double getSalarioMensal() {
        return salarioMensal;
    }

    public boolean saldoAbaixoDaReserva() {
        return salarioMensal > 0 && calcularSaldo() <= getLimiteAlerta();
    }

    public double getPercentualAlerta() {
        return percentualAlerta;
    }

    public double getLimiteAlerta() {
        return salarioMensal * percentualAlerta / 100;
    }

    public static double getPercentualAlertaPadrao() {
        return PERCENTUAL_ALERTA_PADRAO;
    }
}
