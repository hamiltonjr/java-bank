package br.com.dio.exception;

/**
 * Exceção lançada quando uma chave Pix informada já está em uso
 * por outra conta no sistema.
 */
public class PixInUseException extends RuntimeException {

    /**
     * Construtor que recebe uma mensagem detalhando o motivo da exceção.
     *
     * @param message mensagem explicativa sobre o erro
     */
    public PixInUseException(String message) {
        super(message);
    }
}
