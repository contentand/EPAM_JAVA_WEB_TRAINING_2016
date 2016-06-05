package com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.test;

import com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.Account;
import com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.Bank;
import com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.PaymentType;
import com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions.transaction.MainTransaction;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import static org.junit.Assert.*;

import java.util.EnumSet;

public class BankTransactionsIntegrationTest {

    @Rule
    public SystemOutRule outRule = new SystemOutRule().enableLog().mute();

    @Test
    public void test() {
        Account account1 = new Account(Bank.PRIVAT_BANK, 3000_00, EnumSet.allOf(PaymentType.class));
        Account account2 = new Account(Bank.GHJ_BANK, 0,
                EnumSet.of(PaymentType.USUAL, PaymentType.PREFERENTIAL, PaymentType.INTERNAL));

        MainTransaction transaction = new MainTransaction();

        transaction.execute(account1, account2, 200_00, PaymentType.USUAL);

        String expectedOutput = "Establishing connection between the accounts... Success!\n" +
                "Validating payment type consistency... Success!\n" +
                "Calculating transfer expenses... Done!\n" +
                "Checking funds... Success!\n" +
                "Breaking connection between the accounts... Success!\n";
        String actualOutput = outRule.getLogWithNormalizedLineSeparator();
        outRule.clearLog();

        assertEquals(expectedOutput, actualOutput);
        assertEquals(2776_00, account1.getBalance());
        assertEquals(200_00, account2.getBalance());
        assertEquals(6_00, Bank.PRIVAT_BANK.getTaxesAccumulated());
        assertEquals(6_00, Bank.GHJ_BANK.getTaxesAccumulated());
        assertEquals(10_00, Bank.PRIVAT_BANK.getCommissionAccumulated());
        assertEquals(2_00, Bank.GHJ_BANK.getCommissionAccumulated());
    }
}
