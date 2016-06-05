package com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions;

public enum PaymentType {
    USUAL, // usual commission and taxation.
    PREFERENTIAL, // special commission.
    STATE, // special taxation.
    INTERNAL // source Bank is destination Bank, special commission.
}
