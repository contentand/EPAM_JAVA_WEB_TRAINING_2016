package com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.transaction;

class SubTransaction {

    private PaymentRequest request;
    private TransactionStep step1;

    // nested chain invoked by main transaction in case lock up is successful.
    public SubTransaction(PaymentRequest request) {
        this.request = request;
        FundWithdrawalStep step3 = new FundWithdrawalStep(null);
        SufficientFundsValidationStep step2 = new SufficientFundsValidationStep(step3);
        this.step1 = new TransferExpensesCalculationStep(step2);
    }

    public void execute() {
        step1.handleRequest(request);
    }
}
