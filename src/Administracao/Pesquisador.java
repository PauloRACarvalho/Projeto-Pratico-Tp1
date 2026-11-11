package Administracao;

import Tratamento_de_Excessoes.ValidarEmailException;
import Tratamento_de_Excessoes.ValidarSenhaTipo1Exception;
import Tratamento_de_Excessoes.ValidarSenhaTipo2Exception;

import Pesquisas.Pesquisa;
import Pesquisas.SituacaoPesquisa;
import Pesquisas.TipoPesquisa;

import java.util.ArrayList;
import java.util.List;

public class Pesquisador extends Usuario {
    private String abrangencia;
    private SituacaoPesquisa situacao;
    private TipoPesquisa tipo;

    // Lista de pesquisa
    private List<Pesquisa> minhasPesquisas = new ArrayList<>();

    public Pesquisador(String nome, String email, String senha,
                       String perfil) throws ValidarEmailException, ValidarSenhaTipo1Exception, ValidarSenhaTipo2Exception{
        super(nome, email, senha, perfil);

    }

    @Override
    public String toString() {
        return "nome: " + nome + " email: " + email + " perfil: " + perfil;
    }

    public void cadastrarPesquisa(Pesquisa pesquisa) {
        if (pesquisa.getCodigo() == null || pesquisa.getCodigo().isEmpty()) {
            System.out.println("ERRO: Pesquisa sem código!");
            return;
        }

        // Adiciona na lista
        this.minhasPesquisas.add(pesquisa);
        System.out.println("SUCESSO: Pesquisa " + pesquisa.getCodigo() +
                " cadastrada pelo pesquisador " + getNome());
    }

    public void editarPesquisa(Pesquisa pesquisaNova, Pesquisa pesquisaAntiga) {
        int indice = this.minhasPesquisas.indexOf(pesquisaAntiga);

        if (indice != -1) {
            this.minhasPesquisas.set(indice, pesquisaNova);
            System.out.println("SUCESSO: Pesquisa " + pesquisaAntiga.getCodigo() +
                    " substituída por " + pesquisaNova.getCodigo());
        } else {
            System.out.println("ERRO: Pesquisa antiga não encontrada!");
        }
    }

    public void ExcluirPesquisa(Pesquisa pesquisa) {
        // Remove da lista
        boolean removido = this.minhasPesquisas.remove(pesquisa);

        if (removido) {
            System.out.println("SUCESSO: Pesquisa " + pesquisa.getCodigo() +
                    " excluída pelo pesquisador " + getNome());
        } else {
            System.out.println("ERRO: Pesquisa não encontrada na lista!");
        }
    }

    public List<Pesquisa> listarPesquisa() {
        System.out.println("=== Pesquisas do Pesquisador " + getNome() + " ===");
        for (Pesquisa p : minhasPesquisas) {
            System.out.println("- " + p.getCodigo() + ": " + p.getCargo());
        }
        return this.minhasPesquisas;
    }

    public List<Pesquisa> consultarPesquisas() {
        return this.minhasPesquisas;
    }

    public double calcularPercentualConclusao() {
        if (minhasPesquisas.isEmpty()) return 0.0;

        int concluidas = 0;
        for (Pesquisa p : minhasPesquisas) {
            if (p.getSituacao() != null && p.getSituacao().getStatus().equals("Concluída")) {
                concluidas++;
            }
        }

        return (concluidas * 100.0) / minhasPesquisas.size();
    }

    public void encerrarColeta() {
        System.out.println("Encerrando coleta de todas as pesquisas...");
        for (Pesquisa p : minhasPesquisas) {
            if (p.getSituacao() != null) {
                p.getSituacao().alterarStatus("Concluída");
            }
        }
    }

    public void processarPesquisa() {
        System.out.println("Processando pesquisas...");

    }

    // Get e Set
    public String getAbrangencia() { return abrangencia; }
    public void setAbrangencia(String abrangencia) {
        this.abrangencia = abrangencia;
    }

    public SituacaoPesquisa getSituacao() { return situacao; }
    public void setSituacao(SituacaoPesquisa situacao) {
        this.situacao = situacao;
    }

    public TipoPesquisa getTipo() { return tipo; }
    public void setTipo(TipoPesquisa tipo) { this.tipo = tipo; }

    public List<Pesquisa> getMinhasPesquisas() { return minhasPesquisas; }
}