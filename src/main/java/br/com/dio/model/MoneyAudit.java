package br.com.dio.model;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Representa uma transação financeira com identificação,
 * serviço alvo, descrição e timestamp.
 *
 * @param transactionId Identificador único da transação
 * @param targetService Serviço alvo (ACCOUNT ou INVESTMENT)
 * @param description Descrição da transação
 * @param createdAt Data e hora de criação da transação
 */
public record MoneyAudit(
        UUID transactionId,
        BankService targetService,
        String description,
        OffsetDateTime createdAt) {}
