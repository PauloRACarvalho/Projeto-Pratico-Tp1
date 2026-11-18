package Pesquisas;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/** Persistência simples das pesquisas cadastradas. */
public final class ArquivoPesquisas {
    private static final String ARQUIVO = "pesquisas.txt";

    private ArquivoPesquisas() {}

    // Base64 evita problemas caso algum campo contenha ponto e vírgula ou acentos.
    private static String codificar(String texto) {
        return Base64.getEncoder().encodeToString(texto.getBytes(StandardCharsets.UTF_8));
    }

    private static String decodificar(String texto) {
        return new String(Base64.getDecoder().decode(texto), StandardCharsets.UTF_8);
    }

    public static void salvar(List<Pesquisa> pesquisas) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(ARQUIVO), StandardCharsets.UTF_8))) {
            for (Pesquisa p : pesquisas) {
                String status = p.getSituacao() == null
                        ? "Em andamento" : p.getSituacao().getStatus();

                bw.write(codificar(p.getCodigo()) + ";" +
                        codificar(p.getCargo()) + ";" +
                        codificar(p.getPeriodoColeta()) + ";" +
                        p.getQtdeEntrevistados() + ";" +
                        codificar(p.getMetodologia()) + ";" +
                        codificar(status));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível salvar as pesquisas: " + e.getMessage(), e);
        }
    }

    public static List<Pesquisa> carregar() {
        List<Pesquisa> pesquisas = new ArrayList<>();
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return pesquisas;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(arquivo), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                try {
                    String[] campos = linha.split(";", -1);
                    if (campos.length != 6) continue;

                    Pesquisa p = new Pesquisa(
                            decodificar(campos[0]),
                            decodificar(campos[1]),
                            decodificar(campos[2]),
                            Integer.parseInt(campos[3]),
                            decodificar(campos[4]));
                    p.setSituacao(new SituacaoPesquisa(pesquisas.size() + 1, decodificar(campos[5])));
                    pesquisas.add(p);
                } catch (IllegalArgumentException e) {
                    System.err.println("Linha inválida em pesquisas.txt: " + linha);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível carregar pesquisas.txt: " + e.getMessage(), e);
        }
        return pesquisas;
    }
}
