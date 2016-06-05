package com.daniilyurov.training.patterns.chain_of_responsibility.bank_transactions;

public enum Bank {
    PRIVAT_BANK {
        @Override
        public boolean supports(PaymentType type) {
            // supports all types
            return true;
        }

        @Override
        public long calculateCommission(PaymentType type, Bank otherBank, long amount) {
            if (otherBank != this) {
                switch (type) {
                    case PREFERENTIAL:
                        return (long)(amount * .005);
                    default:
                        if (amount < 1_000_00) {
                            return 10_00;
                        }
                        if (amount < 10_000_00) {
                            return 100_00;
                        }
                        if (amount < 100_000_00) {
                            return 1_000_00;
                        }
                        return (long)(amount * .007);
                }
            }
            return (long)(amount * .001);
        }
    },
    GHJ_BANK {
        @Override
        public boolean supports(PaymentType type) {
            // does not support STATE_PAYMENTS
            return type != PaymentType.STATE;
        }

        @Override
        public long calculateCommission(PaymentType type, Bank otherBank, long amount) {
            if (otherBank != this) {
                switch (type) {
                    case PREFERENTIAL:
                        return (long)(amount * .009);
                    default:
                        return (long)(amount * .01);
                }
            }
            return (long)(amount * .005);
        }
    };

    private long taxesAccumulated;
    private long commissionAccumulated;

    public abstract boolean supports(PaymentType type);
    public abstract long calculateCommission(PaymentType type, Bank otherBank, long amount);

    public long calculateTax(PaymentType type, Bank otherBank, long amount) {
        otherBank.getTaxesAccumulated(); // just to get the IDE warning away. This field is left for the future.
        switch (type) {
            case STATE:
                return 0;
            default:
                return (long)(amount * .03);
        }
    }

    public void addTaxes(long amount) {
        if (amount < 0) throw new IllegalArgumentException("Only positive amounts allowed!");
        this.taxesAccumulated += amount;
    }
    public void addCommission(long amount) {
        if (amount < 0) throw new IllegalArgumentException("Only positive amounts allowed!");
        this.commissionAccumulated += amount;
    }

    public long getTaxesAccumulated() {
        return taxesAccumulated;
    }

    public long getCommissionAccumulated() {
        return commissionAccumulated;
    }
}
