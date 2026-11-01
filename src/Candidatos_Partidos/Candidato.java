package Candidatos_Partidos;

public class Candidato {
    private String nome;
    private int numero;
    private String cargo;
    private String situacao;

    public Candidato(String nome, int numero, String cargo, String situacao){
        this.nome = nome;
        this.numero = numero;
        this.cargo = cargo;
        this.situacao = situacao;

    }

    //GET e SET
    public String getSituacao() {
        return situacao;
    }

    public int getNumero() {
        return numero;
    }

    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
