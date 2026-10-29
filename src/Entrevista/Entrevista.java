package Entrevista;

import java.util.List;

public class Entrevista {
    private int identificacao;
    private String status;

    private Entrevistador entrevistador;
    private Entrevistado entrevistado;
    private List<Resposta> respostas;

    public Entrevista(int identificacao, String status){
        this.identificacao = identificacao;
        this.status = status;
    }

    //GET e SET

    public int getIdentificacao() {
        return identificacao;
    }

    public String getStatus() {
        return status;
    }

    public void setIdentificacao(int identificacao) {
        this.identificacao = identificacao;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

