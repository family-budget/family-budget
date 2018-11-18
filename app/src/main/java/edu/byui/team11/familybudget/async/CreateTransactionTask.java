package edu.byui.team11.familybudget.async;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import edu.byui.team11.familybudget.MainActivity;
import edu.byui.team11.familybudget.model.Transaction;

public class CreateTransactionTask extends AsyncTask<Transaction, Object, Object> {
    //TODO: This will not be a view, but a presenter?
    private View view;

    public CreateTransactionTask(View view) {
        this.view = view;
    }

    @Override
    protected Object doInBackground(Transaction... transactions) {
        MainActivity.getDatabase().transactionDAO().create(transactions);
        return null;
    }

    @Override
    protected void onPostExecute(Object result) {
        //TODO
        //presenter.transactionSaved() --> display success message and go to main screen
        Log.d("transaction", "Transaction saved");
    }

}
