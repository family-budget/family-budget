package edu.byui.team11.familybudget.entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryTest {
    @Test
    public void shouldHaveAmount() {
        Category category = new Category();
        category.budgetedAmount = 12;
        assertEquals(12, category.budgetedAmount);
    }
}
