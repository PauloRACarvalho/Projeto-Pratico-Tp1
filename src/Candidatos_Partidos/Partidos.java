package Candidatos_Partidos;

public class Partidos {
    private String nome;
    private String sigla;
    private int numero;
    private String situacao;

    public Partidos(String nome, String sigla, int numero, String situacao){
        this.nome = nome;
        this.sigla = sigla;
        this.numero = numero;
        this.situacao = situacao;
    }
    //GET e SET

    public String getNome() {
        return nome;
    }

    public int getNumero() {
        return numero;
    }

    public String getSigla() {
        return sigla;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}

