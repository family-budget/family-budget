package edu.byui.team11.familybudget.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import edu.byui.team11.familybudget.MainActivity;
import edu.byui.team11.familybudget.R;
import edu.byui.team11.familybudget.async.CreateTransactionTask;
import edu.byui.team11.familybudget.model.Transaction;

public class TransactionFormFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transaction_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureSubmitButton((FloatingActionButton) getView().findViewById(R.id.submit_transaction_form));
    }


    private void configureSubmitButton(FloatingActionButton bt) {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTransactions(getView());
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, TransactionListFragment.newInstance())
                        .commit();
            }
        });
    }

    private void saveTransactions(View view) {
        // Get input fields from the view
        Transaction transaction = new Transaction();

        EditText categoryInput = view.findViewById(R.id.categoryInput);
        EditText amountInput = view.findViewById(R.id.amountInput);
        //Switch IncomeExpense = view.findViewById(R.id.IncomeExpense);

        transaction.category = categoryInput.getText().toString();
        transaction.amount = Float.parseFloat(amountInput.getText().toString());

        //TODO: configurable by the user
        transaction.budgetedAt = Calendar.getInstance().getTime();

        CreateTransactionTask task = new CreateTransactionTask(TransactionListFragment.newInstance().getView());
        task.execute(transaction);
    }
}
