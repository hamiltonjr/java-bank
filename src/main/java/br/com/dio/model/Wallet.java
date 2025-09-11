package br.com.dio.model;

import lombok.Getter;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Classe abstrata que representa uma carteira genérica (Wallet),
 * podendo ser de conta ou investimento.
 */
public abstract class Wallet {

    /** Tipo de serviço associado à carteira */
    @Getter
    private final BankService service;

    /** Lista de unidades monetárias armazenadas na carteira */
    protected final List<Money> money;

    /**
     * Construtor que inicializa a carteira com o tipo de serviço.
     *
     * @param serviceType Tipo do serviço (ACCOUNT ou INVESTMENT)
     */
    public Wallet(BankService serviceType) {
        this.service = serviceType;
        this.money = new ArrayList<>();
    }

    /**
     * Gera um valor em dinheiro com histórico de transação.
     *
     * @param amount Quantidade a ser gerada
     * @param description Descrição da transação
     * @return Lista de objetos Money gerados
     */
    protected List<Money> generatedMoney(final long amount, final String description) {
        var history = new MoneyAudit(UUID.randomUUID(), service, description, OffsetDateTime.now());
        return Stream.generate(() -> new Money(history)).limit(amount).toList();
    }

    /**
     * Retorna a quantidade de unidades monetárias na carteira.
     *
     * @return Quantidade de dinheiro na carteira
     */
    public long getFunds() {
        return money.size();
    }

    /**
     * Adiciona valores à carteira com histórico de transação.
     *
     * @param money Lista de objetos Money a adicionar
     * @param service Tipo de serviço da transação
     * @param description Descrição da transação
     */
    public void addMoney(final List<Money> money, final BankService service, final String description) {
        var history = new MoneyAudit(UUID.randomUUID(), service, description, OffsetDateTime.now());
        money.forEach(m -> m.addHistory(history));
        this.money.addAll(money);
    }

    /**
     * Remove uma quantidade de dinheiro da carteira.
     *
     * @param amount Quantidade a ser removida
     * @return Lista de objetos Money removidos
     */
    public List<Money> reduceMoney(final long amount) {
        List<Money> toRemove = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            toRemove.add(this.money.removeFirst());
        }
        return toRemove;
    }

    /**
     * Retorna todas as transações financeiras da carteira.
     *
     * @return Lista de MoneyAudit representando todas as transações
     */
    public List<MoneyAudit> getFinancialTransactions() {
        return money.stream().flatMap(m -> m.getHistory().stream()).toList();
    }

    /**
     * Retorna uma representação em string da carteira.
     *
     * @return String representando a carteira
     */
    @Override
    public String toString() {
        return "Wallet{" + "service=" + service +
                ", money=" + Money.formatMoney(money.size()) + '}';
    }
}
