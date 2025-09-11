package br.com.dio.repository;

import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.exception.PixInUseException;
import br.com.dio.model.AccountWallet;
import br.com.dio.model.MoneyAudit;
import br.com.dio.model.Wallet;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static br.com.dio.repository.CommonsRepository.checkFundsForTransaction;
import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * Repositório para gerenciamento de contas bancárias.
 * Permite criar contas, realizar depósitos, saques, transferências e consultar histórico.
 */
public class AccountRepository {

    /** Lista de contas criadas no sistema */
    private final List<AccountWallet> accounts = new ArrayList<>();

    /**
     * Cria uma nova conta com chaves Pix e valor inicial.
     *
     * @param pix Lista de chaves Pix da conta
     * @param initialFunds Valor inicial da conta (em centavos)
     * @return Wallet criada
     * @throws PixInUseException Se alguma chave Pix já estiver em uso
     */
    public Wallet create(final List<String> pix, final long initialFunds) {
        if (!accounts.isEmpty()) {
            var pixInUse = accounts.stream()
                    .flatMap(a -> a.getPix().stream())
                    .toList();
            for (var p : pix) {
                if (pixInUse.contains(p)) {
                    throw new PixInUseException("O pix '" + p + "'já está em uso");
                }
            }
        }
        var newAccount = new AccountWallet(initialFunds, pix);
        accounts.add(newAccount);
        return newAccount;
    }

    /**
     * Realiza depósito em uma conta a partir de uma chave Pix.
     *
     * @param pix Chave Pix da conta
     * @param fundsAmount Valor a ser depositado (em centavos)
     * @throws AccountNotFoundException Se a conta não for encontrada
     */
    public void deposit(final String pix, final long fundsAmount) {
        var target = findByPix(pix);
        target.addMoney(fundsAmount, "Depósito");
    }


    /**
     * Realiza saque de uma conta a partir de uma chave Pix.
     *
     * @param pix Chave Pix da conta
     * @param amount Quantidade a ser sacada (em centavos)
     * @return Valor sacado
     * @throws AccountNotFoundException Se a conta não for encontrada
     */
    public long withdraw(final String pix, final long amount) {
        var source = findByPix(pix);
        checkFundsForTransaction(source, amount);
        source.reduceMoney(amount);
        return amount;
    }

    /**
     * Transfere valores entre contas.
     *
     * @param sourcePix Chave Pix da conta de origem
     * @param targetPix Chave Pix da conta de destino
     * @param amount Valor a ser transferido (em centavos)
     * @throws AccountNotFoundException Se alguma das contas não existir
     */
    public void transferMoney(final String sourcePix, final String targetPix, final long amount) {
        var source = findByPix(sourcePix);
        checkFundsForTransaction(source, amount);
        var target = findByPix(targetPix);
        target.addMoney(source.reduceMoney(amount), source.getService(), String.format("Pix enviado de %s para %s", sourcePix, targetPix));
    }

    /**
     * Busca uma conta pelo Pix.
     *
     * @param pix Chave Pix
     * @return AccountWallet encontrada
     * @throws AccountNotFoundException Se a conta não for encontrada
     */
    public AccountWallet findByPix(final String pix) {
        return accounts.stream()
                .filter(a -> a.getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException(
                                String.format("A conta com a chave pix %s não existe!", pix)
                        )
                );
    }

    /**
     * Lista todas as contas do repositório.
     *
     * @return Lista de AccountWallet
     */
    public List<AccountWallet> list() {
        return this.accounts;
    }

    /**
     * Retorna o histórico de transações agrupado por timestamp.
     *
     * @param pix Chave Pix da conta
     * @return Mapa com chave OffsetDateTime e lista de MoneyAudit
     * @throws AccountNotFoundException Se a conta não for encontrada
     */
    public Map<OffsetDateTime, List<MoneyAudit>> getHistory(final String pix){
        var wallet = findByPix(pix);
        var audit = wallet.getFinancialTransactions();
        return audit.stream()
                .collect(Collectors.groupingBy(t -> t.createdAt().truncatedTo(SECONDS)));
    }

}
