package edu.byui.team11.familybudget.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import edu.byui.team11.familybudget.dao.BudgetDAO;
import edu.byui.team11.familybudget.dao.TransactionDAO;
import edu.byui.team11.familybudget.model.Budget;
import edu.byui.team11.familybudget.model.Transaction;

@Database(entities = {Budget.class, Transaction.class}, version = 2, exportSchema = false)
public abstract class ApplicationDatabase extends RoomDatabase {
    public abstract BudgetDAO budgetDAO();

    public abstract TransactionDAO transactionDAO();

    private static volatile ApplicationDatabase INSTANCE;

    public static ApplicationDatabase getInstance(final Context context) {
        if (INSTANCE != null) {
            return INSTANCE;
        }

        synchronized (ApplicationDatabase.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ApplicationDatabase.class, "family-budget")
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return INSTANCE;
    }
}
