package edu.byui.team11.familybudget.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Objects;

import edu.byui.team11.familybudget.R;
import edu.byui.team11.familybudget.model.Budget;
import edu.byui.team11.familybudget.model.Transaction;
import edu.byui.team11.familybudget.viewmodel.TransactionViewModel;

public class TransactionFormFragment extends Fragment {

    private TransactionViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()))
                .get(TransactionViewModel.class);
    }

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

    public void addItemsOnSpinner() {


        String[] categoryArray = {"Income", "Tithing", "Utilities", "Rent/Mortgage", "Food", "Other"};
        Spinner spinner = view.findViewById(R.id.categorySpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter.<String> (this,
                R.array.categoryArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    private void saveTransactions(View view) {


        // Get input fields from the view
        Transaction transaction = new Transaction();

        EditText categoryInput = view.findViewById(R.id.categorySpinner);
        EditText amountInput = view.findViewById(R.id.amountInput);

        transaction.category = categoryInput.getText().toString();
        transaction.amount = Float.parseFloat(amountInput.getText().toString());

        //TODO: configurable by the user
        transaction.budgetedAt = Calendar.getInstance().getTime();

        this.viewModel.insert(transaction);
    }
}
