package br.com.dio.exception;

/**
 * Exceção lançada quando não há fundos suficientes em uma conta
 * ou carteira para realizar uma determinada operação, como saque ou transferência.
 */
public class NoFundsEnoughException extends RuntimeException {

    /**
     * Construtor que recebe uma mensagem detalhando o motivo da exceção.
     *
     * @param message mensagem explicativa sobre o erro
     */
    public NoFundsEnoughException(String message) {
        super(message);
    }
}
