package br.com.dio.model;

import lombok.Getter;
import lombok.ToString;
import java.util.List;
import static br.com.dio.model.BankService.ACCOUNT;

@Getter
public class AccountWallet extends Wallet {
    private final List<String> pix;

    public AccountWallet(final List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
    }

    public AccountWallet(final long amount, final List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
        addMoney(amount, "Valor de criação da conta");
    }

    public void addMoney(final long amount, final String description) {
        var money = generatedMoney(amount, description);
        this.money.addAll(money);
    }

    @Override
    public String toString() {
        return "AccountWallet{" + "pix=" + pix + ", " +
                "money=" + String.format("R$%1.2f", this.getFunds()/100.0) + '}';
    }

}
