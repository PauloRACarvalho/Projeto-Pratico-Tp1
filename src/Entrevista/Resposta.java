package Entrevista;

public class Resposta {
    private int identificacao;
    private String conteudo;

    public Resposta(int identificacao, String conteudo){
        this.conteudo = conteudo;
        this.identificacao = identificacao;
    }

    //GET e SET

    public int getIdentificacao() {
        return identificacao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setIdentificacao(int identificacao) {
        this.identificacao = identificacao;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
