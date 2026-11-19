package Entrevista;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public final class ArquivoEntrevistados {
    private static final String ARQUIVO = "entrevistados.txt";

    private ArquivoEntrevistados() {}

    // evitar problemas com acentos na palavra
    private static String codificar(String texto) {
        return Base64.getEncoder().encodeToString(texto.getBytes(StandardCharsets.UTF_8));
    }

    private static String decodificar(String texto) {
        return new String(Base64.getDecoder().decode(texto), StandardCharsets.UTF_8);
    }

    public static void salvar(List<Entrevistado> entrevistados) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(ARQUIVO), StandardCharsets.UTF_8))) {
            for (Entrevistado e : entrevistados) {
                bw.write(e.getCodigo() + ";" +
                        e.getIdade() + ";" +
                        codificar(e.getEscolaridade()) + ";" +
                        codificar(e.getGenero()) + ";" +
                        codificar(e.getClasseSocial()));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível salvar os entrevistados: " + e.getMessage(), e);
        }
    }

    public static List<Entrevistado> carregar() {
        List<Entrevistado> entrevistados = new ArrayList<>();
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return entrevistados;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(arquivo), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                try {
                    String[] campos = linha.split(";", -1);
                    if (campos.length != 5) continue;

                    entrevistados.add(new Entrevistado(
                            Integer.parseInt(campos[0]),
                            Integer.parseInt(campos[1]),
                            decodificar(campos[2]),
                            decodificar(campos[3]),
                            decodificar(campos[4])));
                } catch (IllegalArgumentException e) {
                    System.err.println("Linha inválida em entrevistados.txt: " + linha);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível carregar entrevistados.txt: " + e.getMessage(), e);
        }
        return entrevistados;
    }
}
