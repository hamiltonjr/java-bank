package br.com.dio.exception;

/**
 * Exceção lançada quando uma conta já possui um investimento ativo
 * e se tenta criar outro investimento vinculado à mesma conta.
 */
public class AccountWithInvestmentException extends RuntimeException {

    /**
     * Construtor que recebe uma mensagem detalhando o motivo da exceção.
     *
     * @param message mensagem explicativa sobre o erro
     */
    public AccountWithInvestmentException(String message) {
        super(message);
    }
}
