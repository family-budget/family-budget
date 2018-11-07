package edu.byui.team11.familybudget;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionModelTest {

    @Test
    public void TransactionTest() {
        Transaction transaction = new Transaction();
        transaction.setAmount(12.65);

        assertEquals(12.65, transaction.getAmount(), 1.0);
    }

}
