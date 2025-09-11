package br.com.dio.model;

import lombok.Getter;
import java.util.List;
import static br.com.dio.model.BankService.ACCOUNT;

/**
 * Representa uma carteira de uma conta bancária.
 * Esta classe estende Wallet e adiciona suporte a chaves Pix associadas à conta.
 */
@Getter
public class AccountWallet extends Wallet {

    /** Lista de chaves Pix associadas a esta conta */
    private final List<String> pix;

    /**
     * Construtor que inicializa a conta com a lista de chaves Pix.
     *
     * @param pix Lista de chaves Pix da conta
     */
    public AccountWallet(final List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
    }

    /**
     * Construtor que inicializa a conta com um valor inicial e chaves Pix.
     *
     * @param amount Valor inicial da conta (em centavos)
     * @param pix Lista de chaves Pix da conta
     */
    public AccountWallet(final long amount, final List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
        addMoney(amount, "Valor de criação da conta");
    }

    /**
     * Adiciona valor à conta, gerando registros de transação.
     *
     * @param amount Quantidade a ser adicionada (em centavos)
     * @param description Descrição da transação
     */
    public void addMoney(final long amount, final String description) {
        var money = generatedMoney(amount, description);
        this.money.addAll(money);
    }

    /**
     * Retorna uma representação em string da AccountWallet.
     *
     * @return String representando a carteira e suas chaves Pix
     */
    @Override
    public String toString() {
        return super.toString() + "AccountWallet{" + "pix=" + pix + '}';
    }

}
