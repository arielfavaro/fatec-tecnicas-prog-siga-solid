package siga;

/**
 * Implementação concreta de persistência em banco de dados MySQL.
 * 
 * Cumpre o Princípio da Inversão de Dependência (DIP) ao implementar a abstração
 * MatriculaRepositorio, permitindo que a classe Matricula permaneça desacoplada
 * dos detalhes de persistência.
 */
public class GravadorMySQL implements MatriculaRepositorio {

    @Override
    public void gravar(String dados) {
        // Simulação de gravação em banco MySQL.
        System.out.println("[MySQL] Gravando: " + dados);
    }
}
