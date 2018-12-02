package edu.byui.team11.familybudget.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

import edu.byui.team11.familybudget.dao.BudgetDAO;
import edu.byui.team11.familybudget.dao.TransactionDAO;
import edu.byui.team11.familybudget.database.ApplicationDatabase;
import edu.byui.team11.familybudget.model.Budget;
import edu.byui.team11.familybudget.model.Transaction;

public class BudgetViewModel extends AndroidViewModel {

    private final BudgetDAO repository;
    private final LiveData<List<Budget>> budgets;

    public BudgetViewModel(@NonNull Application application) {
        super(application);

        this.repository = ApplicationDatabase.getInstance(application).budgetDAO();
        this.budgets = repository.findAll();
    }

    public LiveData<List<Budget>> getAllBudgets() { return this.budgets; }

    public void insert(Budget ...budgets) {
        new insertAsyncTask(this.repository).execute(budgets);
    }

    private static class insertAsyncTask extends AsyncTask<Budget, Void, Void> {
        private final BudgetDAO repository;

        public insertAsyncTask(BudgetDAO repository) {
            this.repository = repository;
        }

        @Override
        protected Void doInBackground(Budget... budgets) {
            this.repository.create(budgets);
            return null;
        }
    }
}
