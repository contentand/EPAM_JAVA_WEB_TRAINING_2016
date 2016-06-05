package com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.transaction;

import com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.Account;
import com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.PaymentType;

public class MainTransaction {
    private TransactionStep step1;

    // main chain invoked by client.
    public MainTransaction() {
        AccountUnlockStep step3 = new AccountUnlockStep(null);
        PaymentTypeSupportValidationStep step2 = new PaymentTypeSupportValidationStep(step3);
        this.step1 = new AccountLockStep(step2);
    }

    public void execute(Account source, Account destination, long amount, PaymentType paymentType) {
        PaymentRequest request = new PaymentRequest(source, destination, amount, paymentType);
        step1.handleRequest(request);
    }
}
