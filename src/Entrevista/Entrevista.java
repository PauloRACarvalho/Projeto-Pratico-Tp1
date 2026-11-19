package Entrevista;

import java.util.ArrayList;
import java.util.List;
import Entrevista.Resposta;

public class Entrevista {
    private int identificacao;
    private String status;


    private Entrevistador entrevistador;
    private Entrevistado entrevistado;
    private List<Resposta> respostas;

    public Entrevista(int identificacao, String status) {
        this.identificacao = identificacao;
        this.status = status;
        this.respostas = new ArrayList<>();
    }

    // Sets e Gets
    public int getIdentificacao() {
        return identificacao;
    }
    public void setIdentificacao(int identificacao) {
        this.identificacao = identificacao;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public void setEntrevistador(Entrevistador entrevistador) {

        this.entrevistador = entrevistador;
    }
    public Entrevistador getEntrevistador() {
        return entrevistador;
    }

    public void setEntrevistado(Entrevistado entrevistado) {

        this.entrevistado = entrevistado;
    }
    public Entrevistado getEntrevistado() {
        return entrevistado;
    }


    public void adicionarResposta(Resposta resposta) {
        this.respostas.add(resposta);
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }
}