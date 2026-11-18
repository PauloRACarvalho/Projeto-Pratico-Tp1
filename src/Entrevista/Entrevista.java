package Entrevista;

import java.util.ArrayList;
import java.util.List;
import Entrevista.Resposta; // Ajuste o pacote se a classe Resposta estiver em outro lugar

public class Entrevista {
    private int identificacao;
    private String status;

    // 🔑 ATRIBUTOS DE RELACIONAMENTO
    private Entrevistador entrevistador;
    private Entrevistado entrevistado;
    private List<Resposta> respostas; // Uma entrevista tem VÁRIAS respostas

    public Entrevista(int identificacao, String status) {
        this.identificacao = identificacao;
        this.status = status;
        this.respostas = new ArrayList<>(); // Inicializa a lista vazia para não dar erro
    }

    // --- Getters e Setters básicos ---
    public int getIdentificacao() { return identificacao; }
    public void setIdentificacao(int identificacao) { this.identificacao = identificacao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // --- Getters e Setters dos Relacionamentos ---
    public void setEntrevistador(Entrevistador entrevistador) {
        this.entrevistador = entrevistador;
    }
    public Entrevistador getEntrevistador() { return entrevistador; }

    public void setEntrevistado(Entrevistado entrevistado) {
        this.entrevistado = entrevistado;
    }
    public Entrevistado getEntrevistado() { return entrevistado; }

    // 🔑 MÉTODO PARA ADICIONAR RESPOSTA (O que você perguntou!)
    public void adicionarResposta(Resposta resposta) {
        this.respostas.add(resposta);
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }
}