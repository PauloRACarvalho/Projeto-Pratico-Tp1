package Pesquisas;

public class Estatistica {
    private Pesquisa pesquisaReferencia;
    private int totalRespostas;

    public Estatistica(Pesquisa pesquisa) {
        this.pesquisaReferencia = pesquisa;
        this.totalRespostas = 0;
    }


    public int calcularRespostas() {
        if (pesquisaReferencia != null && pesquisaReferencia.getPerguntas() != null) {
            int total = 0;
            for (Pergunta p : pesquisaReferencia.getPerguntas()) {
                if (p.getOpcoes() != null) {
                    total += p.getOpcoes().size();
                }
            }
            this.totalRespostas = total;
            System.out.println("Total de respostas calculadas: " + total);
            return total;
        }
        return 0;
    }

    public void distribuiRspostas() {
        System.out.println("=== Distribuição de Respostas ===");
        if (pesquisaReferencia != null) {
            System.out.println("Pesquisa: " + pesquisaReferencia.getCodigo());
            System.out.println("Cargo: " + pesquisaReferencia.getCargo());
            System.out.println("Total de entrevistados previstos: " + pesquisaReferencia.getQtdeEntrevistados());

            if (pesquisaReferencia.getPerguntas() != null) {
                System.out.println("Número de perguntas: " + pesquisaReferencia.getPerguntas().size());
            }
        }
        System.out.println("Respostas distribuídas com sucesso!");
    }

    public Pesquisa comparaPesquisas() {
        System.out.println("Comparando pesquisas...");

        if (pesquisaReferencia != null) {
            System.out.println("Pesquisa base: " + pesquisaReferencia.getCodigo());
            System.out.println("Metodologia: " + pesquisaReferencia.getMetodologia());
            System.out.println("Período de coleta: " + pesquisaReferencia.getPeriodoColeta());
        }

        return pesquisaReferencia;
    }

    public Estatistica getRelatorio() {
        System.out.println("=== RELATÓRIO ESTATÍSTICO ===");

        if (pesquisaReferencia != null) {
            System.out.println("Código da Pesquisa: " + pesquisaReferencia.getCodigo());
            System.out.println("Cargo Pesquisado: " + pesquisaReferencia.getCargo());
            System.out.println("Metodologia: " + pesquisaReferencia.getMetodologia());
            System.out.println("Período de Coleta: " + pesquisaReferencia.getPeriodoColeta());
            System.out.println("Qtde. Entrevistados: " + pesquisaReferencia.getQtdeEntrevistados());

            int totalRespostas = calcularRespostas();
            System.out.println("Total de Opções de Resposta: " + totalRespostas);

            if (pesquisaReferencia.getSituacao() != null) {
                System.out.println("Situação: " + pesquisaReferencia.getSituacao().getStatus());
            }
        } else {
            System.out.println("Nenhuma pesquisa de referência disponível.");
        }

        System.out.println("=============================");

        return this;
    }

    //  Gets e Sets

    public Pesquisa getPesquisaReferencia() {
        return pesquisaReferencia;
    }

    public void setPesquisaReferencia(Pesquisa pesquisaReferencia) {
        this.pesquisaReferencia = pesquisaReferencia;
    }

    public int getTotalRespostas() {
        return totalRespostas;
    }

    public void setTotalRespostas(int totalRespostas) {
        this.totalRespostas = totalRespostas;
    }
}