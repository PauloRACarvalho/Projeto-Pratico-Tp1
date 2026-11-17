
package Candidatos_Partidos;

public class Partidos {
    private String nome;
    private String sigla;
    private int numero;
    private String situacao;

    // Construtor
    public Partidos(String nome, String sigla, int numero, String situacao) {
        this.nome = nome;
        this.sigla = sigla;
        this.numero = numero;
        this.situacao = situacao;
    }

    // Alterar situação do partido
    public void ativar() {
        this.situacao = "Ativo";
    }

    public void desativar() {
        this.situacao = "Inativo";
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }

    public int getNumero() {
        return numero;
    }

    public String getSituacao() {
        return situacao;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    // Troca do print
    @Override
    public String toString() {
        return "Partido: " + nome +
                "\nSigla: " + sigla +
                "\nNúmero: " + numero +
                "\nSituação: " + situacao;
    }
}
