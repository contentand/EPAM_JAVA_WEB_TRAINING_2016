package com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.transaction;

import com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.Account;

class AccountUnlockStep extends TransactionStep {
    AccountUnlockStep(TransactionStep next) {
        super(next);
    }

    // unlock source and destination Account
    @Override
    void handleRequest(PaymentRequest request) {

        System.out.print("Breaking connection between the accounts...");

        Account sourceAccount = request.getSourceAccount();
        Account destinationAccount = request.getDestinationAccount();

        try {
            sourceAccount.lockAccount(destinationAccount);
            destinationAccount.lockAccount(sourceAccount);
        } catch (Exception e) {
            System.out.println("\nERROR. O.O"); // Actually, this should never happen. It's just in case...
            e.printStackTrace();
        }

        System.out.println(" Success!");

        // this is final step.
    }
}
