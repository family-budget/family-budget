package edu.byui.team11.familybudget;




import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryModelTest {


    @Test
    public void CategoryExists() {
        BudgetCategory category = new BudgetCategory();

        category.setCategory = ("Housing");

        assertEquals("Housing", category.getCategory());
    }




}
