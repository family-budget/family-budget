package edu.byui.team11.familybudget.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import edu.byui.team11.familybudget.dao.BudgetDAO;
import edu.byui.team11.familybudget.dao.TransactionDAO;
import edu.byui.team11.familybudget.model.Budget;

@Database(entities = {Budget.class}, version = 1, exportSchema = false)
public abstract class ApplicationDatabase extends RoomDatabase {
        public abstract BudgetDAO budgetDAO();
        public abstract TransactionDAO transactionDAO();
}
