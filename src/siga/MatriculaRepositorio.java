package siga;

/**
 * Abstração para persistência de dados de matrícula.
 *
 * Cumpre o Princípio da Inversão de Dependência (DIP): módulos de alto nível
 * (como Matricula) passam a depender desta interface em vez de implementações
 * concretas (como GravadorMySQL).
 */
public interface MatriculaRepositorio {
    void gravar(String dados);
}
