package br.com.dio.exception;

/**
 * Exceção lançada quando uma conta não é encontrada no sistema.
 *
 * Esta exceção é usada, por exemplo, ao tentar acessar ou operar
 * em uma conta via chave Pix que não existe.
 */
public class AccountNotFoundException extends RuntimeException {

    /**
     * Construtor que recebe uma mensagem detalhando o motivo da exceção.
     *
     * @param message mensagem explicativa sobre o erro
     */
    public AccountNotFoundException(String message) {
        super(message);
    }
}
