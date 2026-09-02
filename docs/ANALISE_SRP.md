# Análise da Classe `RelatorioAluno`, Violação do SRP e Code Smells

---

## 1. Visão Geral da Classe Original

No código inicial, a classe `RelatorioAluno.java` estáva estruturada da seguinte forma:

```java
public class RelatorioAluno {

    // Responsabilidade 1: Formatação de texto / Apresentação
    public String formatar(List<Aluno> alunos) { ... }

    // Responsabilidade 2: Persistência / I/O em disco
    public void salvarEmArquivo(String conteudo, String caminho) { ... }

    // Responsabilidade 3: Comunicação / Envio de e-mail
    public void enviarPorEmail(String conteudo, String destinatario) { ... }
}
```

---

## 2. Identificação das Responsabilidades Misturadas (Violação do SRP)

A classe `RelatorioAluno` acumula três responsabilidades distintas que pertencem a domínios e camadas de arquitetura diferentes:

### 1. Formatação e Apresentação (`formatar`)
* **Domínio:** Apresentação / Geração de visualização dos dados.
* **O que faz:** Itera sobre a coleção de objetos `Aluno` e monta uma representação em texto `String` com cabeçalho, matrícula, nome e média.

### 2. Persistência e Armazenamento (`salvarEmArquivo`)
* **Domínio:** Sistema de Arquivos (I/O).
* **O que faz:** Realiza a escrita do conteúdo do relatório no sistema de arquivos local ou armazenamento em disco.

### 3. Comunicação e Notificação (`enviarPorEmail`)
* **Domínio:** Infraestrutura e Mensageria / Serviços de Terceiros.
* **O que faz:** Responsável pela entrega do relatório aos destinatários por correio eletrônico.

---

## 3. Impactos Negativos da Violação

A concentração dessas três responsabilidades em uma única classe gera diversos problemas arquiteturais:

1. **Alto Acoplamento:** A classe precisa conhecer regras de formatação de strings, diretórios e arquivos do sistema operacional e mecanismos de e-mail.
2. **Baixa Coesão:** Tratam de preocupações operacionais não relacionadas.
3. **Fragilidade e Risco de Regressão:** Qualquer alteração na lógica de envio de e-mail pode quebrar ou introduzir efeitos colaterais na formatação ou persistência.
5. **Baixa Reutilização:** Se outra parte do sistema precisar enviar e-mails ou salvar arquivos em disco, não poderá reutilizar esses comportamentos sem levar consigo a dependência do relatório de alunos.

---

## 4. Lista de *Code Smells* Identificados no Código Original

### 1. *Divergent Change* (Modificação Divergente)
* **Local:** `RelatorioAluno.java`
* **Descrição:** Uma mesma classe precisa ser frequentemente modificada por razões totalmente diferentes, como mudança na forma de salvar arquivos em disco ou mudança nas credenciais/protocolo de envio de e-mail.

### 2. *Large Class / God Class* (Classe Deus)
* **Local:** `RelatorioAluno.java`
* **Descrição:** A classe assume o controle de múltiplos processos que deveriam pertencer a módulos distintos do sistema.

### 3. *Switch Statements / Complex Conditionals* (Comandos Condicionais Complexos)
* **Local:** `Matricula.java` no método `calcularMensalidade()`
* **Descrição:** Uso de blocos `if-else if-else` encadeados para selecionar algoritmos de cálculo de desconto com base em strings literais (`"BOLSISTA"`, `"CONVENIO"`, `"FUNCIONARIO"`). Cada novo tipo de desconto exige alterar o código existente, violando o **OCP**.

### 4. *Hardcoded Dependency* (Dependência Rígida)
* **Local:** `Matricula.java` (`private GravadorMySQL gravador = new GravadorMySQL();`)
* **Descrição:** A classe de domínio `Matricula` instancia diretamente uma classe concreta de infraestrutura (`GravadorMySQL`) com o operador `new`, acoplando o domínio ao banco de dados específico e violando o **DIP**.

### 5. *Violation of Separation of Concerns* (Violação da Separação de Preocupações)
* **Local:** `Matricula.java` no método `salvar()`
* **Descrição:** A entidade de domínio `Matricula` é responsável por formatar a mensagem de log/persistência e acionar diretamente a gravação, misturando regra de negócio com persistência.
