package edu.byui.team11.familybudget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import edu.byui.team11.familybudget.dao.TransactionDAO;
import edu.byui.team11.familybudget.database.ApplicationDatabase;
import edu.byui.team11.familybudget.model.Transaction;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * This is the function to test our TransactionDAO
 **/

@RunWith(AndroidJUnit4.class)
public class TransactionDAOTest {

  private ApplicationDatabase db;
  private TransactionDAO transactionsDao;

  @Before
  public void createDb() {
    Context context = InstrumentationRegistry.getTargetContext();
    this.db = Room.inMemoryDatabaseBuilder(context, ApplicationDatabase.class).build();
    this.transactionsDao = this.db.transactionDAO();
  }

  @After
  public void closeDb() throws IOException {
    this.db.close();
  }

  @Test
  public void shouldPersistTransactions() throws Exception {
    // Assert the initial state
    assertTrue(this.transactionsDao.findAll().isEmpty());

    // Create a transaction
    Transaction transaction = new Transaction();
    transaction.amount = 10;

    // Add it to the database (this is what we are testing)
    this.transactionsDao.insert(transaction);

    // Assert we can retrieve it from the database
    List<Transaction> created = this.transactionsDao.findAll();
    assertEquals(1, created.size());

    // Assert it is retrieved with the same value it was supposed to have
    assertEquals(10, created.get(0).amount);
  }
}
