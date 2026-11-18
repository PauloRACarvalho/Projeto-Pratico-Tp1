
package Candidatos_Partidos;

public class Candidato {
    private String nome;
    private int numero;
    private String cargo;
    private String situacao;
    private Partidos partido;

    // Construtor
    public Candidato(String nome, int numero, String cargo, String situacao) {
        this.nome = nome;
        this.numero = numero;
        this.cargo = cargo;
        this.situacao = situacao;
    }

    // Alterar situação candidato
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

    public int getNumero() {
        return numero;
    }

    public String getCargo() {
        return cargo;
    }

    public String getSituacao() {
        return situacao;
    }

    public Partidos getPartido() {
        return partido;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public void setPartido(Partidos partido) {
        this.partido = partido;
    }

    // Troca do print
    @Override
    public String toString() {
        return "Candidato: " + nome +
                "\nNúmero: " + numero +
                "\nCargo: " + cargo +
                "\nSituação: " + situacao +
                "\nPartido: " + (partido != null ? partido.getSigla() : "Não informado");
    }
}
