package edu.byui.team11.familybudget;

import org.junit.Test;
import static org.junit.Assert.*;

public class BudgetModelTest {
    @Test
    public void shouldHaveAmount() {
        Budget budget = new Budget();
        budget.setAmount(12.34);
        assertEquals(12.34, budget.getAmount(), 0.1);
    }
}
