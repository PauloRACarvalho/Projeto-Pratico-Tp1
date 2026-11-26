package view;

import Administracao.*;
import Entrevista.*;
import Pesquisas.*;
import Tratamento_de_Excessoes.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import javax.swing.*;

//Operações compartilhadas pelos painéis gráficos de acrdo com os perfis
public final class OperacoesGUI {
    private static final Map<String, List<Entrevista>> entrevistasPorPesquisa = new LinkedHashMap<>();
    private static final List<Entrevistado> entrevistados = ArquivoEntrevistados.carregar();
    private static int proximoIdEntrevista = 1;
    private static int proximoIdResposta = 1;
    private OperacoesGUI() {}

    public static void criarPesquisa(JFrame pai, Usuario criador, List<Pesquisa> pesquisas,
                                     Pesquisador pesquisador) {
        JTextField codigo = new JTextField(), cargo = new JTextField(), periodo = new JTextField("Nov/2026");
        JTextField quantidade = new JTextField("1000"), metodologia = new JTextField("Quantitativa");
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.add(new JLabel("Código:")); form.add(codigo);
        form.add(new JLabel("Cargo pesquisado:")); form.add(cargo);
        form.add(new JLabel("Período de coleta:")); form.add(periodo);
        form.add(new JLabel("Entrevistados previstos:")); form.add(quantidade);
        form.add(new JLabel("Metodologia:")); form.add(metodologia);
        if (JOptionPane.showConfirmDialog(pai, form, "Nova pesquisa", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;
        try {
            String cod = codigo.getText().trim(), car = cargo.getText().trim();
            String per = periodo.getText().trim(), met = metodologia.getText().trim();
            int qtd = Integer.parseInt(quantidade.getText().trim());
            if (cod.isEmpty() || car.isEmpty() || per.isEmpty() || met.isEmpty() || qtd <= 0)
                throw new IllegalArgumentException();
            //duplicata/ja existente
            for (Pesquisa existente : pesquisas) if (existente.getCodigo().equalsIgnoreCase(cod)) {
                JOptionPane.showMessageDialog(pai, "Já existe uma pesquisa com esse código."); return;
            }
            Pesquisa nova = new Pesquisa(cod, car, per, qtd, met);
            nova.setSituacao(new SituacaoPesquisa(pesquisas.size() + 1, "Em andamento"));
            pesquisas.add(nova);
            if (pesquisador != null) pesquisador.cadastrarPesquisa(nova);

            ArquivoPesquisas.salvar(pesquisas);


            JOptionPane.showMessageDialog(pai, "Pesquisa cadastrada com sucesso por " + criador.getNome() + ".");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(pai, "A quantidade de entrevistados deve ser um número inteiro.", "Dados inválidos", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(pai, "Preencha todos os campos e informe uma quantidade maior que zero.", "Dados inválidos", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void realizarEntrevista(JFrame pai, Usuario usuario, List<Pesquisa> pesquisas) {
        if (pesquisas.isEmpty()) {
            aviso(pai, "Cadastre uma pesquisa antes de realizar entrevistas.");
            return;
        }
        Pesquisa pesquisa = (Pesquisa) JOptionPane.showInputDialog(pai, "Selecione a pesquisa:", "Realizar entrevista",
                JOptionPane.QUESTION_MESSAGE, null, pesquisas.toArray(), pesquisas.get(0));
        if (pesquisa == null) return;
        try {
            String idadeTxt = JOptionPane.showInputDialog(pai, "Idade do entrevistado:"); if (idadeTxt == null) return;
            int idade = Integer.parseInt(idadeTxt.trim());
            if (idade <= 0) throw new NumberFormatException();
            String[] escolaridades = {"Fundamental", "Médio", "Superior", "Pós-graduação", "Não informado"};
            String escolaridade = (String) JOptionPane.showInputDialog(pai, "Escolaridade:", "Entrevistado", JOptionPane.QUESTION_MESSAGE, null, escolaridades, escolaridades[1]);
            if (escolaridade == null) return;
            String[] generos = {"Feminino", "Masculino", "Outro", "Não informado"};
            String genero = (String) JOptionPane.showInputDialog(pai, "Gênero:", "Entrevistado", JOptionPane.QUESTION_MESSAGE, null, generos, generos[3]);
            if (genero == null) return;
            String[] classes = {"A", "B", "C", "D", "Não informado"};
            String classe = (String) JOptionPane.showInputDialog(pai, "Classe social:", "Entrevistado", JOptionPane.QUESTION_MESSAGE, null, classes, classes[2]);
            if (classe == null) return;
            if (pesquisa.getPerguntas().isEmpty()) { aviso(pai, "A pesquisa selecionada não possui perguntas cadastradas."); return; }
            Entrevista entrevista = new Entrevista(proximoIdEntrevista++, "Concluída");
            entrevista.setEntrevistado(new Entrevistado(entrevistados.size() + 1, idade, escolaridade, genero, classe));
            entrevista.setEntrevistador(new Entrevistador(1, usuario.getNome()));
            for (Pergunta pergunta : pesquisa.getPerguntas()) {
                List<OpcaoResposta> opcoes = pergunta.getOpcoes();
                if (opcoes == null || opcoes.isEmpty()) continue;
                Object escolha = JOptionPane.showInputDialog(pai, pergunta.getEnunciado(), "Resposta da entrevista",
                        JOptionPane.QUESTION_MESSAGE, null, opcoes.toArray(), opcoes.get(0));
                if (escolha == null) return;
                OpcaoResposta opcao = (OpcaoResposta) escolha;
                entrevista.adicionarResposta(new Resposta(proximoIdResposta++, pergunta.getIdentificacao() + ";" + opcao.getIdentficacao() + ";" + opcao.getDescricao()));
            }
            entrevistados.add(entrevista.getEntrevistado());
            ArquivoEntrevistados.salvar(entrevistados);
            /** quando uma entrevista é realizada ela é adicionada ao dicionário estrevistasPorPesquisa*/
            entrevistasPorPesquisa.computeIfAbsent(pesquisa.getCodigo(), k -> new ArrayList<>()).add(entrevista);
            JOptionPane.showMessageDialog(pai, "Entrevista registrada para a pesquisa " + pesquisa.getCodigo() + ".");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(pai, "Informe uma idade válida.", "Dados inválidos", JOptionPane.ERROR_MESSAGE);
        }
    }
    /** inicializando o método */
    public static void gerarRelatorio(JFrame pai, List<Pesquisa> pesquisas, String titulo) {

        /** validação inicial,, se não tiver pesquisasm mostra um aviso na tela*/
        if (pesquisas.isEmpty()) {
            aviso(pai, "Não há pesquisas cadastradas para gerar relatório.");
            return;
        }
        /** perguntar ao usuário que pesquisa ele quer selecionar,
         * (Pesquisa) está "forçando" o java a trasnformar o objeto genérico do tipo Object criado por showInputDialog para um objeto do tipo Pesquisa */
        Pesquisa pesquisa = (Pesquisa) JOptionPane.showInputDialog(pai, "Selecione a pesquisa:", titulo,
                JOptionPane.QUESTION_MESSAGE, null, pesquisas.toArray(), pesquisas.get(0));

        /** para veridicar se o usuário fechou a janela ou cancelou a pesquisa */
        if (pesquisa == null) return;

        /** quando o relatório é gerado, ele busca as entrevistas correspondente ao código da pesquisa escolhida
         * getOrDefault para evitar um erro de objeto nulo*/
        List<Entrevista> entrevistas = entrevistasPorPesquisa.getOrDefault(pesquisa.getCodigo(), Collections.emptyList());

        /** cabeçalho do relatório */
        /** o metodo .append() retorna o próprio StringBuilder para encadear vádias chamadas e ficar mais legível */
        StringBuilder relatorio = new StringBuilder("RELATÓRIO DE PESQUISA ELEITORAL\n\n")
                .append("Código: ").append(pesquisa.getCodigo()).append('\n')
                .append("Cargo: ").append(pesquisa.getCargo()).append('\n')
                .append("Período: ").append(pesquisa.getPeriodoColeta()).append('\n')
                .append("Metodologia: ").append(pesquisa.getMetodologia()).append('\n')
                .append("Entrevistados previstos: ").append(pesquisa.getQtdeEntrevistados()).append('\n')
                .append("Entrevistas registradas nesta sessão: ").append(entrevistas.size()).append("\n\nRESULTADOS POR PERGUNTA\n");

        /** laço que se repete uma vez a cada pergunta, percorre cada petgunta da pesquisa */
        for (Pergunta pergunta : pesquisa.getPerguntas()) { /** tem um, tem muitos, uma pesquisa tem perguntas*/
            relatorio.append("\n").append(pergunta.getEnunciado()).append('\n');
            int total = 0; /** quantas respostas válidas tem a pergunta */
            Map<Integer, Integer> contagem = new LinkedHashMap<>(); /** "urna", a opção n (candidato) tem n votos*/

            /** laço para percorrer as respostas das entrevistas*/
            for (Entrevista entrevista : entrevistas) { /** para cada entrevista que foi feita na pesquisa */
                for (Resposta resposta : entrevista.getRespostas()) { /** para cada resposta da entrvista */
                    String[] partes = resposta.getConteudo().split(";", 3); /** quebra o tetxo em 3 partes, pergunta, opção escolhida, descrição da opção*/

                    /** verificação da resposta e contegem dos votos*/
                    if (partes.length == 3 && Integer.toString(pergunta.getIdentificacao()).equals(partes[0])) { /** se o vetor tem 3 elementos e verifica se a resposta de uma pergunta está sendo contabilizada no loop da pergunta*/
                        int idOpcao = Integer.parseInt(partes[1]); /** pega o ID da opção escolhida e em texto e transforma para um número inteiro*/
                        contagem.put(idOpcao, contagem.getOrDefault(idOpcao, 0) + 1); /** pega a contagem de uma opção e atualiza no dicionário contagem*/
                        total++;
                    }
                }
            }
            /**cálculo da porcentagem*/
            for (OpcaoResposta opcao : pergunta.getOpcoes()) { /** para cada opção de resposta  da pergunta */
                int n = contagem.getOrDefault(opcao.getIdentficacao(), 0); /**quantas vezez a opção foi votada*/
                double percentual = total == 0 ? 0 : n * 100.0 / total; /** se o total de respostas for 0 o percentual será 0, se não calcula a procentagem*/
                /**formatação*/
                relatorio.append(" - ").append(opcao.getDescricao()).append(": ").append(n)
                        .append(" ( ").append(String.format(Locale.forLanguageTag("pt-BR"), "%.1f", percentual)).append("% )\n");
            }
            relatorio.append("Total de respostas válidas: ").append(total).append('\n');
        }

        /** se bão houver entrevistas*/
        if (entrevistas.isEmpty()) {
            relatorio.append("\nAinda não há entrevistas registradas para esta pesquisa nesta sessão.\n");
        }
        /** para mostrar na tela*/
        JTextArea area = new JTextArea(relatorio.toString(), 22, 65); /** tamanho da janela */
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JOptionPane.showMessageDialog(pai, new JScrollPane(area), titulo, JOptionPane.INFORMATION_MESSAGE);
        
    }

    public static List<Entrevista> getEntrevistas(Pesquisa pesquisa) {
        return new ArrayList<>(entrevistasPorPesquisa.getOrDefault(pesquisa.getCodigo(), Collections.emptyList()));
    }
    private static void aviso(Component pai, String mensagem) { JOptionPane.showMessageDialog(pai, mensagem); }
}
