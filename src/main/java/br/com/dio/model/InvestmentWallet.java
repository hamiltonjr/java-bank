package br.com.dio.model;

import lombok.Getter;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.stream.Stream;
import static br.com.dio.model.BankService.INVESTMENT;

/**
 * Representa a carteira de um investimento específico associado a uma conta.
 */
@Getter
public class InvestmentWallet extends Wallet {

    /** Investimento associado a esta carteira */
    private final Investment investment;

    /** Conta associada a este investimento */
    private final AccountWallet account;

    /**
     * Construtor que inicializa uma carteira de investimento,
     * retirando o valor da conta e registrando na carteira de investimento.
     *
     * @param investment Investimento a ser aplicado
     * @param account Conta que realizará o investimento
     * @param amount Valor investido (em centavos)
     */
    public InvestmentWallet(final Investment investment, final AccountWallet account, final long amount) {
        super(INVESTMENT);
        this.investment = investment;
        this.account = account;
        addMoney(account.reduceMoney(amount), getService(), "investment");
    }

    /**
     * Atualiza o valor da carteira aplicando um rendimento percentual.
     *
     * @param percent Percentual de rendimento a ser aplicado
     */
    public void updateAmount(final long percent) {
        var amount = getFunds() * percent / 100;
        var history = new MoneyAudit(UUID.randomUUID(), getService(), "rRndimentos", OffsetDateTime.now());
        var money = Stream.generate(() -> new Money(history)).limit(amount).toList();
        this.money.addAll(money);
    }

    /**
     * Retorna uma representação em string da InvestmentWallet.
     *
     * @return String representando a carteira de investimento
     */
    @Override
    public String toString() {
        return super.toString() + "InvestmentWallet{" +
                "investment=" + investment + ", account=" + account + '}';
    }

}