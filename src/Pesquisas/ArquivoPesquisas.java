package Pesquisas;

import Candidatos_Partidos.Candidato;
import Candidatos_Partidos.Partidos;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class ArquivoPesquisas {
    private static final String ARQUIVO = "pesquisas.txt";
    private ArquivoPesquisas() {}

    private static String codificar(String texto) {
        return Base64.getEncoder().encodeToString((texto == null ? "" : texto).getBytes(StandardCharsets.UTF_8));
    }
    private static String decodificar(String texto) {
        return new String(Base64.getDecoder().decode(texto), StandardCharsets.UTF_8);
    }

    public static void salvar(List<Pesquisa> pesquisas) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(ARQUIVO), StandardCharsets.UTF_8))) {
            for (Pesquisa p : pesquisas) {
                String status = p.getSituacao() == null ? "Em andamento" : p.getSituacao().getStatus();
                bw.write(codificar(p.getCodigo()) + ";" + codificar(p.getCargo()) + ";"
                        + codificar(p.getPeriodoColeta()) + ";" + p.getQtdeEntrevistados() + ";"
                        + codificar(p.getMetodologia()) + ";" + codificar(status) + ";"
                        + codificar(serializarPerguntas(p)) + ";"
                        + codificar(serializarPartidos(p)) + ";"
                        + codificar(serializarCandidatos(p)));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível salvar as pesquisas: " + e.getMessage(), e);
        }
    }

    private static String serializarPerguntas(Pesquisa p) {
        List<String> registros = new ArrayList<>();
        for (Pergunta pergunta : p.getPerguntas()) {
            StringBuilder r = new StringBuilder().append(pergunta.getIdentificacao())
                    .append(",").append(codificar(pergunta.getEnunciado()));
            for (OpcaoResposta op : pergunta.getOpcoes())
                r.append(",").append(op.getIdentficacao()).append(":").append(codificar(op.getDescricao()));
            registros.add(r.toString());
        }
        return String.join("|", registros);
    }

    private static String serializarPartidos(Pesquisa p) {
        List<String> registros = new ArrayList<>();
        for (Partidos partido : p.getPartidos())
            registros.add(codificar(partido.getNome()) + "," + codificar(partido.getSigla()) + ","
                    + partido.getNumero() + "," + codificar(partido.getSituacao()));
        return String.join("|", registros);
    }

    private static String serializarCandidatos(Pesquisa p) {
        List<String> registros = new ArrayList<>();
        for (Candidato c : p.getCandidatos()) {
            int indicePartido = p.getPartidos().indexOf(c.getPartido());
            registros.add(codificar(c.getNome()) + "," + c.getNumero() + ","
                    + codificar(c.getCargo()) + "," + codificar(c.getSituacao()) + "," + indicePartido);
        }
        return String.join("|", registros);
    }

    public static List<Pesquisa> carregar() {
        List<Pesquisa> pesquisas = new ArrayList<>();
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return pesquisas;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(arquivo), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                try {
                    String[] c = linha.split(";", -1);
                    if (c.length < 6) continue;
                    Pesquisa p = new Pesquisa(decodificar(c[0]), decodificar(c[1]),
                            decodificar(c[2]), Integer.parseInt(c[3]), decodificar(c[4]));
                    p.setSituacao(new SituacaoPesquisa(pesquisas.size() + 1, decodificar(c[5])));
                    if (c.length >= 9) {
                        carregarPerguntas(p, decodificar(c[6]));
                        carregarPartidos(p, decodificar(c[7]));
                        carregarCandidatos(p, decodificar(c[8]));
                    }
                    pesquisas.add(p);
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Linha inválida em pesquisas.txt: " + linha);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível carregar pesquisas.txt: " + e.getMessage(), e);
        }
        return pesquisas;
    }

    private static void carregarPerguntas(Pesquisa p, String dados) {
        if (dados.isEmpty()) return;
        for (String registro : dados.split("\\|")) {
            String[] campos = registro.split(",", -1);
            if (campos.length < 2) continue;
            Pergunta pergunta = new Pergunta(Integer.parseInt(campos[0]), decodificar(campos[1]));
            for (int i = 2; i < campos.length; i++) {
                String[] op = campos[i].split(":", 2);
                pergunta.adicionarOpcao(new OpcaoResposta(Integer.parseInt(op[0]), decodificar(op[1])));
            }
            p.adicionarPergunta(pergunta);
        }
    }

    private static void carregarPartidos(Pesquisa p, String dados) {
        if (dados.isEmpty()) return;
        for (String registro : dados.split("\\|")) {
            String[] c = registro.split(",", -1);
            p.adicionarPartido(new Partidos(decodificar(c[0]), decodificar(c[1]),
                    Integer.parseInt(c[2]), decodificar(c[3])));
        }
    }

    private static void carregarCandidatos(Pesquisa p, String dados) {
        if (dados.isEmpty()) return;
        for (String registro : dados.split("\\|")) {
            String[] c = registro.split(",", -1);
            Candidato candidato = new Candidato(decodificar(c[0]), Integer.parseInt(c[1]),
                    decodificar(c[2]), decodificar(c[3]));
            int indice = Integer.parseInt(c[4]);
            if (indice >= 0 && indice < p.getPartidos().size()) candidato.setPartido(p.getPartidos().get(indice));
            p.adicionarCandidato(candidato);
        }
    }
}
