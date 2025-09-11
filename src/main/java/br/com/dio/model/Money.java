package br.com.dio.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa unidades monetárias em forma de objetos Money,
 * armazenando o histórico de transações.
 */
@EqualsAndHashCode
@ToString
@Getter
public class Money {

    /** Histórico de auditoria das transações */
    private final List<MoneyAudit> history = new ArrayList<>();

    /**
     * Construtor que adiciona uma transação ao histórico.
     *
     * @param history Objeto MoneyAudit representando a transação
     */
    public Money(MoneyAudit history) {
        this.history.add(history);
    }

    /**
     * Adiciona uma transação ao histórico.
     *
     * @param history Objeto MoneyAudit a ser adicionado
     */
    public void addHistory(MoneyAudit history) {
        this.history.add(history);
    }

    /**
     * Formata o valor monetário armazenado em centavos para string legível.
     *
     * @param amount Valor em centavos
     * @return String formatada no padrão R$X,XX
     */
    public static String formatMoney(long amount) {
        return String.format("R$%d,%02d", (amount / 100), (amount % 100));
    }

}
