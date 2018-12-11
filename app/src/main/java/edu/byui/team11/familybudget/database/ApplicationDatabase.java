package edu.byui.team11.familybudget.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import edu.byui.team11.familybudget.dao.CategoryDAO;
import edu.byui.team11.familybudget.dao.TransactionDAO;
import edu.byui.team11.familybudget.model.Category;
import edu.byui.team11.familybudget.model.Transaction;

@Database(entities = {Category.class, Transaction.class}, version = 3, exportSchema = false)
public abstract class ApplicationDatabase extends RoomDatabase {

  public abstract CategoryDAO getCategoryDAO();
  public abstract TransactionDAO getTransactionDAO();

  private static volatile ApplicationDatabase INSTANCE;

  /**
   * Get the singleton instance for the {@link ApplicationDatabase} class.
   * @param context
   * @return
   */
  public static ApplicationDatabase getInstance(final Context context) {
    if (INSTANCE != null) {
      return INSTANCE;
    }

    synchronized (ApplicationDatabase.class) {
      if (INSTANCE == null) {
        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ApplicationDatabase.class,
            "family-budget")
            .fallbackToDestructiveMigration()
            .build();
      }
    }

    return INSTANCE;
  }
}
