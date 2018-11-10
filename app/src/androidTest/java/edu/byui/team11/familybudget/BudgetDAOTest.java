package edu.byui.team11.familybudget;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import edu.byui.team11.familybudget.dao.BudgetDAO;
import edu.byui.team11.familybudget.database.ApplicationDatabase;
import edu.byui.team11.familybudget.model.Budget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class BudgetDAOTest {
    private ApplicationDatabase db;
    private BudgetDAO budgetsDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        this.db = Room.inMemoryDatabaseBuilder(context, ApplicationDatabase.class).build();
        this.budgetsDao = this.db.budgetDAO();
    }

    @After
    public void closeDb() throws IOException {
        this.db.close();
    }

    @Test
    public void shouldPersistBudgets() throws Exception {
        // Assert the initial state
        assertTrue(this.budgetsDao.findAll().isEmpty());

        // Create a budget
        Budget budget = new Budget();
        budget.amount = 10;

        // Add it to the database (this is what we are testing)
        this.budgetsDao.create(budget);

        // Assert we can retrieve it from the database
        List<Budget> created = this.budgetsDao.findAll();
        assertEquals(1, created.size());

        // Assert it is retrieved with the same value it was supposed to have
        assertEquals(10, created.get(0).amount);
    }
}