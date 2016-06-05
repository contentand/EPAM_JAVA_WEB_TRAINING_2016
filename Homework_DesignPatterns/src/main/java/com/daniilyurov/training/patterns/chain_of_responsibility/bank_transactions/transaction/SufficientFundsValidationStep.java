package com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.transaction;

import com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.Account;

class SufficientFundsValidationStep extends TransactionStep {
    SufficientFundsValidationStep(TransactionStep next) {
        super(next);
    }

    // check source Account has enough funds to cover account and additional costs.
    @Override
    void handleRequest(PaymentRequest request) {

        System.out.print("Checking funds...");

        Account sourceAccount = request.getSourceAccount();
        long finalAmountToWithdraw = request.getFinalAmountToWithdraw();
        long currentBalance = sourceAccount.getBalance();

        if (currentBalance < finalAmountToWithdraw) {
            System.out.println("\nDeclined: Insufficient funds!");
            return;
        }

        System.out.println(" Success!");

        super.handleRequest(request);
    }
}
