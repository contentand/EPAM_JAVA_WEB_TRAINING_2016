package com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.transaction;

import com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.Account;
import com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.Bank;
import com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.PaymentType;

class PaymentTypeSupportValidationStep extends TransactionStep {

    PaymentTypeSupportValidationStep(TransactionStep next) {
        super(next);
    }

    // Check source Bank, destination Bank, source Account and destination Account support the payment type.
    @Override
    public void handleRequest(PaymentRequest request) {

        System.out.print("Validating payment type consistency...");

        Bank sourceBank = request.getSourceBank();
        Bank destinationBank = request.getDestinationBank();
        Account sourceAccount = request.getSourceAccount();
        Account destinationAccount = request.getDestinationAccount();
        PaymentType paymentType = request.getPaymentType();

        if (!sourceBank.supports(paymentType)) {
            System.out.println("\n\tDeclined: Payment type not supported by your bank.");
            return;
        }
        if (!destinationBank.supports(paymentType)) {
            System.out.println("\n\tDeclined: Payment type not supported by target bank.");
            return;
        }
        if (!sourceAccount.supports(paymentType)) {
            System.out.println("\n\tDeclined: Payment type not supported by your account contract.");
            return;
        }
        if (!destinationAccount.supports(paymentType)) {
            System.out.println("\n\tDeclined: Payment type not supported by target account contract.");
            return;
        }

        System.out.println(" Success!");

        SubTransaction subTransaction = new SubTransaction(request);
        subTransaction.execute();

        super.handleRequest(request);
    }
}
