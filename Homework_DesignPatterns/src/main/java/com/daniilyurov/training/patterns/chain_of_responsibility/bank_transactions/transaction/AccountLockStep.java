package com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.transaction;


import com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.Account;

class AccountLockStep extends TransactionStep {
    AccountLockStep(TransactionStep next) {
        super(next);
    }

    // lock source Account and destination Account
    @Override
    void handleRequest(PaymentRequest request) {

        System.out.print("Establishing connection between the accounts...");

        Account sourceAccount = request.getSourceAccount();
        Account destinationAccount = request.getDestinationAccount();

        if (!sourceAccount.lockAccount(destinationAccount)) {
            System.out.println("\nDeclined: Your account is busy with other transaction.");
            return;
        }
        if (!destinationAccount.lockAccount(sourceAccount)) {
            sourceAccount.unlockAccount(destinationAccount);
            System.out.println("\nDeclined: Destination account is busy with other transaction.");
        }

        System.out.println(" Success!");

        super.handleRequest(request);
    }
}
