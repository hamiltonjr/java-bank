package br.com.dio.exception;

/**
 * Exceção lançada quando um investimento específico não é encontrado
 * no sistema, por exemplo, ao tentar acessá-lo pelo seu ID.
 */
public class InvestmentNotFoundException extends RuntimeException {

    /**
     * Construtor que recebe uma mensagem detalhando o motivo da exceção.
     *
     * @param message mensagem explicativa sobre o erro
     */
    public InvestmentNotFoundException(String message) {
        super(message);
    }
}
