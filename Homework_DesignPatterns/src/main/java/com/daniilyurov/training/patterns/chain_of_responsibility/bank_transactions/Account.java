package com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions;

import java.util.EnumSet;

public class Account {
    private Bank bank; // bank the account belongs to.
    private long balance; // the account balance in cents
    private Account lockingAcount; // the bank that is currently working with the account.
    private EnumSet<PaymentType> supportedPaymentTypes; // payment types supported by the account

    public Account(Bank bank, long balance, EnumSet<PaymentType> supportedPaymentTypes) {
        this.bank = bank;
        this.balance = balance;
        this.supportedPaymentTypes = supportedPaymentTypes;
    }

    public boolean supports(PaymentType paymentType) {
        return supportedPaymentTypes.contains(paymentType);
    }

    public boolean lockAccount(Account account) {
        if (lockingAcount != null) {
            return false;
        }
        lockingAcount = account;
        return true;
    }

    public void unlockAccount(Account account) {
        if (lockingAcount == null) {
            throw new IllegalArgumentException("The account is already unlocked!");
        }
        if (lockingAcount != account) {
            throw new IllegalArgumentException("This bank is not locking the account!");
        }
        lockingAcount = null;
    }

    public long getBalance() {
        return balance;
    }

    public void withdraw(Account account, long amount) {
        if (account != lockingAcount) {
            throw new IllegalStateException("The account is locked by a different bank!");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative!");
        }
        balance -= amount;
    }

    public void fund(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative!");
        }
        balance += amount;
    }

    public Bank getBank() {
        return bank;
    }
}
