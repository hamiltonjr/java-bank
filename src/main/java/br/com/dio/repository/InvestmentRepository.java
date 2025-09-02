package br.com.dio.repository;

import br.com.dio.exception.AccountWithInvestmentException;
import br.com.dio.exception.InvestmentNotFoundException;
import br.com.dio.exception.WalletNotFoundException;
import br.com.dio.model.AccountWallet;
import br.com.dio.model.Investment;
import br.com.dio.model.InvestmentWallet;
import java.util.ArrayList;
import java.util.List;
import static br.com.dio.repository.CommonsRepository.checkFundsForTransaction;

public class InvestmentRepository {
    private long nextId;
    private final List<Investment> investments = new ArrayList<>();
    private final List<InvestmentWallet> wallets = new ArrayList<>();

    public List<Investment> list() {
        return this.investments;
    }

    public List<InvestmentWallet> listWallets() {
        return this.wallets;
    }

    public Investment create(final long tax, final long initialFunds) {
        this.nextId++;
        var investment = new Investment(nextId, tax, initialFunds);
        investments.add(investment);
        return investment;
    }

    public InvestmentWallet initInvestment(final AccountWallet account, final long id) {
        var accountsInUse = wallets.stream().map(InvestmentWallet::getAccount).toList();
        if (accountsInUse.contains(account)) {
            throw new AccountWithInvestmentException("A conta já possui investimento!");
        }

        var investment = findById(id);
        checkFundsForTransaction(account, investment.);
        var wallet = new InvestmentWallet(investment, account, investment.initialFunds());
        wallets.add(wallet);
        return wallet;
    }

    public InvestmentWallet fimdWalletByAccountPix(final String pix) {
        return wallets.stream()
                .filter(w -> w.getAccount().getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new WalletNotFoundException("A carteira não foi encontrada!"));
    }

    public Investment findById(final long id) {
        return investments.stream()
                .filter(a -> a.id() == id)
                .findFirst()
                .orElseThrow(() -> new InvestmentNotFoundException("Investimento não encontrado!"));
    }

    public void updateAmount() {
        wallets.forEach(w -> w.updateAmount(w.getInvestment().tax()));
    }

    public InvestmentWallet deposit(final String pix, final long funds) {
        var wallet = fimdWalletByAccountPix(pix);
        wallet.addMoney(wallet.getAccount().reduceMoney(funds), "Investimento");
        return wallet;
    }

    public InvestmentWallet withdraw(final String pix, final long funds) {
        var wallet = fimdWalletByAccountPix(pix);
        checkFundsForTransaction(wallet, funds);
        wallet.getAccount().addMoney(wallet.reduceMoney(funds), "saque de investimento");
        if (wallet.getFunds() == 0) {
            wallets.remove(wallet);
        }
        return wallet;
    }

}
