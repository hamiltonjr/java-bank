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

/**
 * Repositório para gerenciamento de investimentos.
 * Permite criar investimentos, inicializar carteiras, depósitos, saques e atualização de rendimentos.
 */
public class InvestmentRepository {

    /** Próximo ID de investimento */
    private long nextId = 0;

    /** Lista de investimentos criados */
    private final List<Investment> investments = new ArrayList<>();

    /** Lista de carteiras de investimento */
    private final List<InvestmentWallet> wallets = new ArrayList<>();

    /**
     * Cria um novo investimento.
     *
     * @param tax Taxa de rendimento (%)
     * @param initialFunds Valor inicial investido
     * @return Investimento criado
     */
    public Investment create(final long tax, final long initialFunds) {
        this.nextId++;
        var investment = new Investment(nextId, tax, initialFunds);
        investments.add(investment);
        return investment;
    }

    /**
     * Inicializa uma carteira de investimento para uma conta e investimento existente.
     *
     * @param account Conta que realizará o investimento
     * @param id ID do investimento
     * @return InvestmentWallet criada
     * @throws AccountWithInvestmentException Se a conta já tiver investimento
     */
    public InvestmentWallet initInvestment(final AccountWallet account, final long id) {
        if (!wallets.isEmpty()) {
            var accountsInUse = wallets.stream().map(InvestmentWallet::getAccount).toList();
            if (accountsInUse.contains(account)) {
                throw new AccountWithInvestmentException("A conta'" + account + "'já possui um investimento");
            }
        }
        var investment = findById(id);
        checkFundsForTransaction(account, investment.initialFunds());
        var wallet = new InvestmentWallet(investment, account, investment.initialFunds());
        wallets.add(wallet);
        return wallet;
    }

    /**
     * Busca uma carteira de investimento pela chave Pix da conta.
     *
     * @param pix Chave Pix da conta
     * @return InvestmentWallet encontrada
     * @throws WalletNotFoundException Se não encontrar a carteira
     */
    public InvestmentWallet fimdWalletByAccountPix(final String pix) {
        return wallets.stream()
                .filter(w -> w.getAccount().getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new WalletNotFoundException("A carteira não foi encontrada!"));
    }

    /**
     * Busca um investimento pelo ID.
     *
     * @param id Identificador do investimento
     * @return Investimento encontrado
     * @throws InvestmentNotFoundException Se não encontrar o investimento
     */
    public Investment findById(final long id) {
        return investments.stream()
                .filter(a -> a.id() == id)
                .findFirst()
                .orElseThrow(() -> new InvestmentNotFoundException("O investimento '" + id + "' não foi encontrado"));
    }

    /**
     * Atualiza valores depositados em carteiras de investimento.
     */
    public void updateAmount() {
        wallets.forEach(w -> w.updateAmount(w.getInvestment().tax()));
    }

    /**
     * Deposita valores em carteira de investimentos.
     * @param pix
     * @param funds
     * @return
     */
    public InvestmentWallet deposit(final String pix, final long funds) {
        var wallet = fimdWalletByAccountPix(pix);
        wallet.addMoney(wallet.getAccount().reduceMoney(funds), wallet.getService(), "Investimento");
        return wallet;
    }

    /**
     * Saca valores de carteira de invesyimentos.
     * @param pix
     * @param funds
     * @return
     */
    public InvestmentWallet withdraw(final String pix, final long funds) {
        var wallet = fimdWalletByAccountPix(pix);
        checkFundsForTransaction(wallet, funds);
        wallet.getAccount().addMoney(wallet.reduceMoney(funds), wallet.getService(), "Saque de investimento");
        if (wallet.getFunds() == 0) {
            wallets.remove(wallet);
        }
        return wallet;
    }

    /**
     * Lista todos os investimentos existentes.
     *
     * @return Lista de investimentos
     */
    public List<Investment> list() {
        return this.investments;
    }

    /**
     * Lista todas as carteiras de investimento existentes.
     *
     * @return Lista de InvestmentWallet
     */
    public List<InvestmentWallet> listWallets() {
        return this.wallets;
    }

}
