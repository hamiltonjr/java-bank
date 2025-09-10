package br.com.dio.repository;

import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.exception.PixInUseException;
import br.com.dio.model.AccountWallet;
import br.com.dio.model.Wallet;
import java.util.ArrayList;
import java.util.List;
import static br.com.dio.repository.CommonsRepository.checkFundsForTransaction;

public class AccountRepository {
    private final List<AccountWallet> accounts = new ArrayList<>();

    public List<AccountWallet> list() {
        return this.accounts;
    }

    public Wallet create(final List<String> pix, final long initialFunds) {
        if (!accounts.isEmpty()) {
            var pixInUse = accounts.stream()
                    .flatMap(a -> a.getPix().stream())
                    .toList();

            for (var p : pix) {
                if (pixInUse.contains(p)) {
                    throw new PixInUseException("O pix já está em uso!");
                }
            }
        }

        var newAccount = new AccountWallet(initialFunds, pix);
        accounts.add(newAccount);
        return newAccount;
    }

    public void deposit(final String pix, final long fundsAmount) {
        var target = findByPix(pix);
        target.addMoney(fundsAmount, "Depósito feito com sucesso!");
    }

    public long withdraw(final String pix, final long amount) {
        var source = findByPix(pix);
        checkFundsForTransaction(source, amount);
        source.reduceMoney(amount);
        return amount;
    }

    public void transferMoney(final String sourcePix, final String targetPix, final long amount) {
        var source = findByPix(sourcePix);
        checkFundsForTransaction(source, amount);
        var target = findByPix(targetPix);
        checkFundsForTransaction(target, amount);
        target.addMoney(source.reduceMoney(amount), String.format("Pix enviado de %s para %s", sourcePix, targetPix));
    }

    public AccountWallet findByPix(final String pix) {
        return accounts.stream()
                .filter(a -> a.getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException(
                                String.format("A conta com a chave pix %s não existe!", pix)
                        )
                );
    }

}
