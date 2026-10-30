package model;

public class Transacao {
    private static int proximoId = 1;
    private int id;
    private double valor;
    private String descricao;
    private Categoria categoria;
    private String data;
    private String tipo; // "entrada" ou "saida"

    public Transacao(double valor, String descricao, Categoria categoria, String data, String tipo) {
        this.id = proximoId++;
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.data = data;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public String getData() {
        return data;
    }  

    public String getTipo() {
        return tipo;
    }

}
