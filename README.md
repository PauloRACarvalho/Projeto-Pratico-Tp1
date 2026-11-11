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

O desenvolvimento é dividido em quatro etapas: Estrutura Inicial, Design e Modelagem, Implementação da Lógica do Software e Integração e Navegabilidade, além da apresentação final.

## Equipe (Grupo 5)

| Integrante | Módulo responsável |
|---|---|
| Audrey Coutinho Soares - 232020994 | Administração |
| Anna Clara Faria Gomes - 232000347 | Pesquisas |
| Paulo Ricardo Almeida Carvalho - 232022068 | Coleta e Amostra |
| Isabele Sandra Gontijo dos Santos - 232026871 | Análise Estatística e Relatórios |

## Sobre o projeto

**Sistema de Análise de Pesquisas Eleitorais** é o tema escolhido pela professora. O objetivo é desenvolver uma solução computacional que apoie a coleta, a organização, o processamento e a análise de dados de pesquisas eleitorais simuladas, integrando cadastro, coleta, análise e apresentação de resultados em uma única ferramenta.

> **Aviso:** este é um projeto exclusivamente acadêmico e de caráter simulado. Todos os candidatos, partidos, eleitores, respostas e resultados são fictícios. O sistema não deve ser usado para campanha eleitoral, propaganda política ou avaliação real de candidatos e partidos.

## Tecnologias

- **Linguagem:** Java 21
- **IDE:** IntelliJ IDEA
- **Modelagem:** Astah (diagrama de classes)
- **Prototipação das telas:** Figma
- **Controle de versão:** Git e GitHub
- **Persistência:** arquivo de texto (`usuarios.txt`)

## Status do desenvolvimento

- [x] **Etapa 1:** Estrutura inicial (repositório, pacotes e protótipos das telas)
- [x] **Etapa 2:** Design e modelagem (diagrama de classes)
- [x] **Etapa 3:** Implementação da lógica do software (regras de negócio, persistência e exceções)
- [ ] **Etapa 4:** Integração e navegabilidade (interface gráfica)

## Estrutura do projeto

O código está organizado em pacotes por módulo, o que separa as responsabilidades e facilita a manutenção:

```
├── Administracao/            # Usuario, Administrador, Pesquisador, Analista, GerenciarUsuarios
├── Candidatos_Partidos/      # Candidatos, Partidos
├── Entrevista/               # Entrevista, Entrevistador, Entrevistado, Resposta
├── Pesquisas/                # Pesquisa, Pergunta, OpcaoResposta, PlanoAmostral,
│                             # SituacaoPesquisa, TipoPesquisa, Estatistica
├── Tratamento_de_Excessoes/  # ValidarEmailException, ValidarSenhaTipo1Exception,
│                             # ValidarSenhaTipo2Exception
└── Main.java                 # Ponto de entrada (menu no console)
```

## Funcionalidades implementadas (Etapa 3)

Nesta etapa, a interação com o sistema é feita por **menu no console**. A interface gráfica será implementada na Etapa 4.

- **Cadastro e login de usuários**, com validação de e-mail e senha e bloqueio de usuários desativados.
- **Perfis de acesso**, com menus diferentes para cada perfil:

| Perfil | Funcionalidades |
|---|---|
| **Administrador** | Criar pesquisas, realizar entrevistas, gerar relatórios, cadastrar e listar usuários, ativar e desativar contas |
| **Pesquisador** | Criar pesquisas, realizar entrevistas e gerar relatórios |
| **Analista** | Gerar relatório estatístico |

- **Gerenciamento de pesquisas:** cadastro, edição, exclusão, listagem, percentual de conclusão e encerramento da coleta.
- **Controle da situação da pesquisa**, com validação das transições de status.
- **Relatório estatístico** de uma pesquisa (código, cargo, metodologia, período, quantidade de entrevistados e total de opções de resposta).
- **Simulação de entrevistas**, com entrevistador, entrevistado e resposta.

### Regras de negócio

- O e-mail deve seguir o formato `usuario@gmail.com`.
- A senha não pode ser vazia e deve ter ao menos uma letra maiúscula e um caractere especial.
- Não é possível cadastrar dois usuários com o mesmo e-mail (sem diferenciar maiúsculas de minúsculas).
- Uma pesquisa só é cadastrada se tiver código, e só pode ser editada ou excluída se existir na lista do pesquisador.
- Uma pesquisa concluída não volta para "Em andamento", e uma pesquisa cancelada não pode mais ser alterada.
- Um administrador não pode desativar a própria conta.

## Conceitos de programação aplicados

- **Herança:** `Administrador`, `Analista` e `Pesquisador` herdam de `Usuario`.
- **Polimorfismo:** a lista de usuários é tratada como `List<Usuario>`, e o fluxo do menu se ajusta à subclasse real do objeto logado.
- **Encapsulamento:** atributos privados ou protegidos, acessados por getters e setters.
- **Composição:** `Pesquisa` possui `Pergunta`, que possui `OpcaoResposta`; `Entrevista` se relaciona com `Entrevistador`, `Entrevistado` e `Resposta`.
- **Tratamento de exceções:** exceções personalizadas para validação de e-mail e senha.
- **Persistência em arquivo:** os usuários são salvos e carregados do arquivo `usuarios.txt`.
- **Modularização:** separação em pacotes por módulo.

## Como executar

**Pré-requisitos:** JDK 21 instalado e, de preferência, IntelliJ IDEA.

1. Clone o repositório:
   ```bash
   git clone https://github.com/PauloRACarvalho/Projeto-Pratico-Tp1.git
   ```
2. Abra a pasta do projeto no IntelliJ IDEA.
3. Confirme que o SDK do projeto é o Java 21 (*File > Project Structure > Project*).
4. Execute a classe `Main`.
5. No menu inicial, cadastre um usuário (por exemplo, com perfil `Administrador`) e faça login.

Os usuários cadastrados são gravados no arquivo `usuarios.txt`, criado automaticamente na pasta de execução na primeira vez que alguém se cadastra.

## Limitações conhecidas

- A persistência em arquivo abrange, por ora, apenas os usuários. Pesquisas e entrevistas ficam em memória durante a execução.
- A coleta de entrevistas é simulada.
- As senhas são gravadas em texto puro no arquivo.
- A interface é de console, e a interface gráfica será entregue na Etapa 4, etapa final do projeto.

## Próximos passos

- Implementar a interface gráfica e conectar os eventos à lógica de negócio (Etapa 4).
- Ampliar a persistência para pesquisas, entrevistas, candidatos e partidos.
- Evoluir o módulo de análise estatística, com comparações entre pesquisas, gráficos e exportação de relatórios.
- Finalizar o relatório do projeto
- Apresentação final
