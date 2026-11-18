package Pesquisas;

import java.util.ArrayList;
import java.util.List;
import Candidatos_Partidos.Candidato;
import Candidatos_Partidos.Partidos;
public class Pesquisa {
    private String codigo;
    private String cargo;
    private String periodoColeta;
    private int qtdeEntrevistados;
    private String metodologia;
    private SituacaoPesquisa situacao;
    private List<Pergunta> perguntas = new ArrayList<>();
    private List<Partidos> partidos = new ArrayList<>();
    private List<Candidato> candidatos = new ArrayList<>();

    // Construtor
    public Pesquisa(String codigo, String cargo, String periodoColeta,
                    int qtdeEntrevistados, String metodologia) {
        this.codigo = codigo;
        this.cargo = cargo;
        this.periodoColeta = periodoColeta;
        this.qtdeEntrevistados = qtdeEntrevistados;
        this.metodologia = metodologia;
        this.perguntas = new ArrayList<>();
        this.partidos = new ArrayList<>();
        this.candidatos = new ArrayList<>();
    }

    // 🔑 MÉTODOS SIMPLES (apenas validações internas e prints)

    public void cadastrarPesquisa(Pesquisa pesquisa) {
        // Validação simples
        if (pesquisa.codigo != null && !pesquisa.codigo.isEmpty()) {
            System.out.println("Pesquisa " + pesquisa.codigo + " pronta para cadastro.");
        } else {
            System.out.println("ERRO: Código inválido!");
        }
    }

    public void editarPesquisa(Pesquisa pesquisaNova, Pesquisa pesquisaAntiga) {
        System.out.println("Preparando edição da pesquisa " + pesquisaAntiga.codigo);
        // A edição REAL será feita pelo Pesquisador
    }

    public void excluirPesquisa(Pesquisa pesquisa) {
        System.out.println("Pesquisa " + pesquisa.codigo + " marcada para exclusão.");
        // A exclusão REAL será feita pelo Pesquisador
    }

    //cadastrar perguntas
    public void adicionarPergunta(Pergunta pergunta) {
        this.perguntas.add(pergunta);
    }

    // Cadastro de partidos e candidatos pertencentes a esta pesquisa
    public void adicionarPartido(Partidos partido) {
        if (partido != null) {
            this.partidos.add(partido);
        }
    }

    public void adicionarCandidato(Candidato candidato) {
        if (candidato != null) {
            this.candidatos.add(candidato);
        }
    }

    @Override
    public String toString() { return codigo + " - " + cargo; }

    // Getters e Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getPeriodoColeta() { return periodoColeta; }
    public void setPeriodoColeta(String periodoColeta) {
        this.periodoColeta = periodoColeta;
    }

    public int getQtdeEntrevistados() { return qtdeEntrevistados; }
    public void setQtdeEntrevistados(int qtdeEntrevistados) {
        this.qtdeEntrevistados = qtdeEntrevistados;
    }

    public String getMetodologia() { return metodologia; }
    public void setMetodologia(String metodologia) {
        this.metodologia = metodologia;
    }

    public SituacaoPesquisa getSituacao() { return situacao; }
    public void setSituacao(SituacaoPesquisa situacao) {
        this.situacao = situacao;
    }

    public List<Pergunta> getPerguntas() { return perguntas; }
    public List<Partidos> getPartidos() { return partidos; }
    public List<Candidato> getCandidatos() { return candidatos; }
    public void setPerguntas(List<Pergunta> perguntas) {
        this.perguntas = perguntas;
    }
}
