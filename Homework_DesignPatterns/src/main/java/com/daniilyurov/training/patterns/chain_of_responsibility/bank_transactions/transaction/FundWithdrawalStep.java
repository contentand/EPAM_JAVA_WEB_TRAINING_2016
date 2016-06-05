package com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.transaction;

import com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.Account;
import com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.Bank;

class FundWithdrawalStep extends TransactionStep {
    FundWithdrawalStep(TransactionStep next) {
        super(next);
    }

    // withdraw and transfer money to all parties involved.
    @Override
    void handleRequest(PaymentRequest request) {

        Bank sourceBank = request.getSourceBank();
        Bank destinationBank = request.getDestinationBank();
        Account sourceAccount = request.getSourceAccount();
        Account destinationAccount = request.getDestinationAccount();

        long totalAmountToWithdraw = request.getFinalAmountToWithdraw();
        long amount = request.getRequestedAmount();
        long taxAmountSourceBank = request.getTaxAmountSourceBank();
        long commissionAmountSourceBank = request.getCommissionAmountSourceBank();
        long taxAmountDestinationBank = request.getTaxAmountDestinationBank();
        long commissionAmountDestinationBank = request.getCommissionAmountDestinationBank();

        sourceAccount.withdraw(destinationAccount, totalAmountToWithdraw);
        destinationAccount.fund(amount);
        sourceBank.addTaxes(taxAmountSourceBank);
        sourceBank.addCommission(commissionAmountSourceBank);
        destinationBank.addTaxes(taxAmountDestinationBank);
        destinationBank.addCommission(commissionAmountDestinationBank);


        super.handleRequest(request);
    }
}
