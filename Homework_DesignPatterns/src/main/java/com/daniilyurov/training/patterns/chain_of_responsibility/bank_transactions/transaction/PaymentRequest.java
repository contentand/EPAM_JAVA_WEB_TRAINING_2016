package com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.transaction;

import com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.Account;
import com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.Bank;
import com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.PaymentType;

// the form carrying all the necessary information to be passed through the steps of the chains.
class PaymentRequest {
    private long requestedAmount;
    private long taxAmountSourceBank;
    private long commissionAmountSourceBank;
    private long taxAmountDestinationBank;
    private long commissionAmountDestinationBank;
    private Bank sourceBank;
    private Bank destinationBank;
    private Account sourceAccount;
    private Account destinationAccount;
    private PaymentType paymentType;

    PaymentRequest(Account source, Account destination, long amount, PaymentType paymentType) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative!");
        }
        if (paymentType == PaymentType.INTERNAL && source.getBank() != destination.getBank()) {
            throw new IllegalArgumentException("Invalid internal transaction request. Banks do not match!");
        }

        this.sourceAccount = source;
        this.destinationAccount = destination;
        this.sourceBank = source.getBank();
        this.destinationBank = destination.getBank();
        this.requestedAmount = amount;
        this.paymentType = paymentType;


    }

    long getFinalAmountToWithdraw() {
        return requestedAmount + taxAmountDestinationBank + taxAmountSourceBank
                + commissionAmountDestinationBank + commissionAmountSourceBank;
    }

    long getRequestedAmount() {
        return requestedAmount;
    }

    long getTaxAmountSourceBank() {
        return taxAmountSourceBank;
    }

    void setTaxAmountSourceBank(long taxAmountSourceBank) {
        this.taxAmountSourceBank = taxAmountSourceBank;
    }

    long getCommissionAmountSourceBank() {
        return commissionAmountSourceBank;
    }

    void setCommissionAmountSourceBank(long commissionAmountSourceBank) {
        this.commissionAmountSourceBank = commissionAmountSourceBank;
    }

    Bank getSourceBank() {
        return sourceBank;
    }

    Bank getDestinationBank() {
        return destinationBank;
    }

    Account getSourceAccount() {
        return sourceAccount;
    }

    Account getDestinationAccount() {
        return destinationAccount;
    }

    PaymentType getPaymentType() {
        return paymentType;
    }

    public long getTaxAmountDestinationBank() {
        return taxAmountDestinationBank;
    }

    public void setTaxAmountDestinationBank(long taxAmountDestinationBank) {
        this.taxAmountDestinationBank = taxAmountDestinationBank;
    }

    public long getCommissionAmountDestinationBank() {
        return commissionAmountDestinationBank;
    }

    public void setCommissionAmountDestinationBank(long commissionAmountDestinationBank) {
        this.commissionAmountDestinationBank = commissionAmountDestinationBank;
    }
}
