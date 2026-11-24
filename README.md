# Trabalho Prático 1 - Técnicas de Programação 1
**Repositório:** [https://github.com/PauloRACarvalho/Projeto-Pratico-Tp1](https://github.com/PauloRACarvalho/Projeto-Pratico-Tp1)

## Sobre a disciplina

**Universidade de Brasília (UnB), Campus Darcy Ribeiro**
**Departamento:** Ciência da Computação
**Disciplina:** Técnicas de Programação 1 (CIC0197)
**Professora:** Roberta Barbosa Oliveira

O trabalho prático da disciplina tem como objetivo aplicar, de forma prática, técnicas de programação baseadas em orientação a objetos, percorrendo as etapas de um processo de desenvolvimento de software, da definição do domínio e das regras de negócio até a implementação final. Os principais conceitos envolvidos são:

- Programação orientada a objetos (herança, polimorfismo, encapsulamento e abstração)
- Tratamento de exceções
- Técnicas de design e boas práticas de programação
- Modularização, com separação em camadas e pacotes
- Persistência de dados
- Colaboração em grupo, com divisão de responsabilidades e integração das partes

O desenvolvimento foi dividido em quatro etapas: Estrutura Inicial, Design e Modelagem, Implementação da Lógica do Software e Integração e Navegabilidade, além da apresentação final.

## Equipe (Grupo 5)

| Integrante | Módulo responsável |
|---|---|
| Anna Clara Faria Gomes | Administração |
| Audrey Coutinho Soares | Pesquisas |
| Paulo Ricardo Almeida Carvalho | Coleta e Amostra |
| Isabele Sandra Gontijo dos Santos | Análise Estatística e Relatórios |

## Sobre o projeto

**Sistema de Análise de Pesquisas Eleitorais** é o tema escolhido pelo grupo. O objetivo é desenvolver uma solução computacional que apoie a coleta, a organização, o processamento e a análise de dados de pesquisas eleitorais simuladas, integrando cadastro, coleta, análise e apresentação de resultados em uma única ferramenta, com interface gráfica e persistência em arquivos.

> **Aviso:** este é um projeto exclusivamente acadêmico e de caráter simulado. Todos os candidatos, partidos, eleitores, respostas e resultados são fictícios. O sistema não deve ser usado para campanha eleitoral, propaganda política ou avaliação real de candidatos e partidos.

## Tecnologias

- **Linguagem:** Java 21
- **Interface gráfica:** Java Swing, desenvolvida no NetBeans
- **IDE do projeto:** IntelliJ IDEA
- **Modelagem:** Astah (diagrama de classes)
- **Prototipação das telas:** Figma
- **Controle de versão:** Git e GitHub
- **Persistência:** arquivos de texto (`usuarios.txt`, `pesquisas.txt` e `entrevistados.txt`)

## Status do desenvolvimento

- [x] **Etapa 1:** Estrutura inicial (repositório, pacotes e protótipos das telas)
- [x] **Etapa 2:** Design e modelagem (diagrama de classes)
- [x] **Etapa 3:** Implementação da lógica do software (regras de negócio, persistência e exceções)
- [x] **Etapa 4:** Integração e navegabilidade (interface gráfica integrada à lógica)

## Funcionalidades

O sistema possui autenticação obrigatória por e-mail e senha e três perfis de acesso, cada um com as telas e opções compatíveis com sua função:

| Perfil | Funcionalidades |
|---|---|
| **Administrador** | Visão geral do sistema, gerenciar usuários (cadastrar, listar, ativar e desativar), criar pesquisas, cadastrar perguntas, gerenciar partidos e candidatos, realizar entrevistas e gerar relatório estatístico |
| **Pesquisador** | Visão geral das próprias pesquisas, criar pesquisas, cadastrar perguntas, gerenciar partidos e candidatos, realizar entrevistas e gerar relatório estatístico |
| **Analista** | Gerar relatório estatístico |

### Telas

- **Login e cadastro de usuário**, com escolha de perfil e confirmação de senha.
- **Visão geral**, com indicadores e as permissões do perfil logado.
- **Gerenciar usuários**, com tabela de usuários e opções de cadastrar, atualizar a lista e ativar ou desativar o usuário selecionado.
- **Criar pesquisa**, com código, cargo pesquisado, período de coleta, entrevistados previstos e metodologia.
- **Perguntas da pesquisa**, com cadastro e remoção de perguntas e de suas opções de resposta.
- **Partidos e candidatos**, com abas de partidos e candidatos e controle da situação de cada registro.
- **Realizar entrevista**, com seleção da pesquisa e registro do entrevistado.
- **Relatório estatístico**, com os dados da pesquisa e os resultados por pergunta.

### Regras de negócio

- O e-mail deve seguir o formato `usuario@gmail.com` e não pode ser repetido (sem diferenciar maiúsculas de minúsculas).
- A senha não pode ser vazia e deve ter ao menos uma letra maiúscula e um caractere especial.
- Usuários desativados não conseguem acessar o sistema.
- Cada perfil acessa apenas as funcionalidades permitidas para a sua função.
- Uma pesquisa só é cadastrada se tiver código, e só pode ser editada ou excluída se existir na lista do pesquisador.
- Uma pesquisa concluída não volta para "Em andamento", e uma pesquisa cancelada não pode mais ser alterada.
- Mensagens de erro de validação são exibidas em caixas de diálogo, sem interromper o programa.

## Conceitos de programação aplicados

- **Herança:** `Administrador`, `Analista` e `Pesquisador` herdam de `Usuario`.
- **Polimorfismo:** o objeto retornado por `login()` é tratado como `Usuario`, e as opções disponíveis na interface se ajustam à subclasse real. A lista de usuários é uma `List<Usuario>` com objetos das três subclasses.
- **Encapsulamento:** atributos privados ou protegidos, acessados por getters e setters.
- **Composição:** `Pesquisa` possui `Pergunta`, que possui `OpcaoResposta`; `Entrevista` se relaciona com `Entrevistador`, `Entrevistado` e `Resposta`.
- **Tratamento de exceções:** exceções personalizadas para validação de e-mail e senha, capturadas nos eventos da interface.
- **Persistência em arquivos:** os dados de usuários, pesquisas (com perguntas, opções, partidos e candidatos associados) e entrevistados são salvos e carregados de arquivos de texto, com codificação UTF-8 e Base64 nos campos serializados.
- **Modularização:** separação em pacotes por módulo e em classes específicas para persistência e interface.

## Estrutura do projeto

O sistema possui 19 classes, organizadas em pacotes por módulo:

```
├── Administracao/            # Usuario, Administrador, Pesquisador, Analista, GerenciarUsuarios
├── Candidatos_Partidos/      # Candidatos, Partidos
├── Entrevista/               # Entrevista, Entrevistador, Entrevistado, Resposta
├── Pesquisas/                # Pesquisa, Pergunta, OpcaoResposta, PlanoAmostral,
│                             # SituacaoPesquisa, TipoPesquisa, Estatistica
└── Tratamento_de_Excessoes/  # ValidarEmailException, ValidarSenhaTipo1Exception,
                              # ValidarSenhaTipo2Exception
```

Além dos pacotes de domínio, o projeto inclui:

- as classes de persistência `ArquivoPesquisas` e `ArquivoEntrevistados`, responsáveis por gravar e recuperar os dados nos arquivos de texto;
- as telas da interface gráfica em Java Swing e a classe de inicialização que abre a tela de login.

## Como executar

**Pré-requisitos:** JDK 21 instalado e uma IDE Java (IntelliJ IDEA ou NetBeans).

1. Clone o repositório:
   ```bash
   git clone https://github.com/PauloRACarvalho/Projeto-Pratico-Tp1.git
   ```
2. Abra a pasta do projeto na IDE.
3. Confirme que o SDK do projeto é o Java 21.
4. Execute a classe de inicialização da interface, que abre a tela de login.
5. Na tela de login, use a opção **Cadastre-se** para criar um usuário (por exemplo, com perfil Administrador) e entre no sistema.

Os arquivos `usuarios.txt`, `pesquisas.txt` e `entrevistados.txt` são criados automaticamente na pasta de execução quando há dados para salvar.

## Limitações conhecidas

- As senhas são gravadas em texto puro no arquivo `usuarios.txt`.
- O relatório estatístico considera as entrevistas registradas na sessão atual.
- A exportação de relatórios em PDF ou CSV e a exibição de gráficos não foram implementadas.
- O suporte opcional à concorrência não foi implementado.

## Próximos passos

- Gravar as senhas com criptografia ou *hash*.
- Ampliar o módulo de análise estatística, com comparações entre pesquisas, gráficos e exportação de relatórios.
- Aplicar filtros por região, faixa etária e escolaridade nos relatórios.
- Avaliar o uso de padrões de projeto e de concorrência no processamento de grandes conjuntos de respostas.
