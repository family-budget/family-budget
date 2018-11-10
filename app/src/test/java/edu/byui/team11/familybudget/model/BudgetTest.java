package edu.byui.team11.familybudget.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BudgetTest {
    @Test
    public void shouldHaveAmount() {
        Budget budget = new Budget();
        budget.amount = 12;
        assertEquals(12, budget.amount);
    }
}
