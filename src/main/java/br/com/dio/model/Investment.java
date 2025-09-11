package br.com.dio.model;

/**
 * Representa um investimento realizado no banco.
 *
 * @param id Identificador único do investimento
 * @param tax Taxa de rendimento do investimento (em porcentagem)
 * @param initialFunds Valor inicial investido (em centavos)
 */
public record Investment(
        long id,
        long tax,
        long initialFunds
) {
    @Override
    public String toString() {
        return "Investment{" + "id=" + id + ", tax=" + tax + "%" +
                ", initialFunds=" + Money.formatMoney(initialFunds) + '}';
    }

}
