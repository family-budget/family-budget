package edu.byui.team11.familybudget.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

import edu.byui.team11.familybudget.dao.TransactionDAO;
import edu.byui.team11.familybudget.database.ApplicationDatabase;
import edu.byui.team11.familybudget.model.Transaction;

public class TransactionViewModel extends AndroidViewModel {

    private final TransactionDAO repository;
    private final LiveData<List<Transaction>> transactions;

    public TransactionViewModel(@NonNull Application application) {
        super(application);

        this.repository = ApplicationDatabase.getInstance(application).getTransactionDAO();
        this.transactions = repository.getAllTransactions();
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return this.transactions;
    }

    public void insert(Transaction transaction) {
        new insertAsyncTask(this.repository).execute(transaction);
    }

    private static class insertAsyncTask extends AsyncTask<Transaction, Void, Void> {
        private final TransactionDAO repository;

        public insertAsyncTask(TransactionDAO repository) {
            this.repository = repository;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            this.repository.insert(transactions);
            return null;
        }
    }
}
