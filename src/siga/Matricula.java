package siga;

/**
 * Código INICIAL da atividade — contém violações PROPOSITAIS do SOLID.
 *
 * PROBLEMA 2 — Violação do Princípio Aberto/Fechado (OCP):
 * o método calcularMensalidade usa um bloco de condicionais por TIPO de desconto
 * que cresce a cada novo tipo. Adicionar "convênio", "funcionário" ou qualquer
 * outro desconto exige MODIFICAR este método e testá-lo novamente por inteiro.
 *
 * PROBLEMA 3 — Violação do Princípio da Inversão de Dependência (DIP):
 * a classe depende DIRETAMENTE de uma implementação concreta de persistência
 * (GravadorMySQL), instanciada com "new" dentro dela. Deveria depender de uma
 * abstração (uma interface), permitindo trocar a implementação sem alterá-la.
 *
 * Tarefa (etapas 3 e 4 da ficha):
 *   - substituir o bloco condicional por polimorfismo (interface Desconto e uma
 *     classe por tipo de desconto), tornando o cálculo aberto para extensão;
 *   - inverter a dependência concreta de GravadorMySQL, fazendo a classe depender
 *     de uma interface (ex.: MatriculaRepositorio).
 */
public class Matricula {

    private Aluno aluno;
    private double valorBase;
    private Desconto desconto;

    private MatriculaRepositorio repositorio;

    public Matricula(Aluno aluno, double valorBase, Desconto desconto, MatriculaRepositorio repositorio) {
        this.aluno = aluno;
        this.valorBase = valorBase;
        this.desconto = desconto != null ? desconto : new SemDesconto();
        this.repositorio = repositorio;
    }

    // OCP aplicado: aberto para extensão (novos descontos), fechado para modificação.
    public double calcularMensalidade() {
        return desconto.aplicar(valorBase);
    }

    // DIP aplicado: persiste através da abstração (MatriculaRepositorio), desacoplado de implementação concreta.
    public void salvar() {
        repositorio.gravar("Matrícula de " + aluno.getNome()
                + " - mensalidade: " + calcularMensalidade());
    }
}
