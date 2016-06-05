package com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.transaction;

/**
 * Handler
 */
abstract class TransactionStep {

    TransactionStep next;

    TransactionStep(TransactionStep next) {
        this.next = next;
    }

    void handleRequest(PaymentRequest request) {
        if (next != null) {
            next.handleRequest(request);
        }
    }
}
