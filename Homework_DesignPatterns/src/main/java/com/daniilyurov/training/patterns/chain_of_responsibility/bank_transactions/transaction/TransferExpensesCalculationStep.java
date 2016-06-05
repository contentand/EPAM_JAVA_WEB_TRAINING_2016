package com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.transaction;

import com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.Bank;
import com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.PaymentType;

class TransferExpensesCalculationStep extends TransactionStep {

    TransferExpensesCalculationStep(TransactionStep next) {
        super(next);
    }

    // adds commissions of source Bank and destination Bank to the amount.
    @Override
    void handleRequest(PaymentRequest request) {

        System.out.print("Calculating transfer expenses...");

        long amount = request.getRequestedAmount();
        Bank sourceBank = request.getSourceBank();
        Bank destinationBank = request.getDestinationBank();
        PaymentType paymentType = request.getPaymentType();

        long taxAmountSourceBank = sourceBank.calculateTax(paymentType, destinationBank, amount);
        long commissionAmountSourceBank = sourceBank.calculateCommission(paymentType, destinationBank, amount);
        long taxAmountDestinationBank = destinationBank.calculateTax(paymentType, sourceBank, amount);
        long commissionAmountDestinationBank = destinationBank.calculateCommission(paymentType, sourceBank, amount);

        request.setCommissionAmountDestinationBank(commissionAmountDestinationBank);
        request.setCommissionAmountSourceBank(commissionAmountSourceBank);
        request.setTaxAmountDestinationBank(taxAmountDestinationBank);
        request.setTaxAmountSourceBank(taxAmountSourceBank);

        System.out.println(" Done!");

        super.handleRequest(request);
    }
}
