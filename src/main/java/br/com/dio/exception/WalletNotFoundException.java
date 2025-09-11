package br.com.dio.exception;

/**
 * Exceção lançada quando uma carteira de investimento específica
 * não é encontrada no sistema.
 */
public class WalletNotFoundException extends RuntimeException {

    /**
     * Construtor que recebe uma mensagem detalhando o motivo da exceção.
     *
     * @param message mensagem explicativa sobre o erro
     */
    public WalletNotFoundException(String message) {
        super(message);
    }
}
