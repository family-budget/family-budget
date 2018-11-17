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

import java.util.ArrayList;
import java.util.List;

import edu.byui.team11.familybudget.R;
import edu.byui.team11.familybudget.model.Budget;

public class BudgetFormFragment extends Fragment {

    private final List<Budget> ourBudgetsDatabase = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.budget_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //TODO
        //loadBudgetFromTheDatabase();
        displayBudgetOnForm();

        configureSubmitButton((FloatingActionButton) getView().findViewById(R.id.submit_budget_button));
    }

    private void displayBudgetOnForm() {
        //Next step
        // Use this.ourBudgetsDatabase to load.
    }


    private void configureSubmitButton(FloatingActionButton bt) {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBudget(view);
                showSuccessMessage(view);
                goToTransactionList();
            }
        });
    }

    private void saveBudget(View view) {
        // Get input fields from the view
        EditText incomeInput = view.findViewById(R.id.incomeInput);
        EditText tithingInput = view.findViewById(R.id.tithingInput);
        EditText utilitiesInput = view.findViewById(R.id.utilitiesInput);
        EditText rentInput = view.findViewById(R.id.rentInput);
        EditText foodInput = view.findViewById(R.id.foodInput);

        //TODO: Put in the database instead of a List

        // Create budget objects and add them to the "list"
        Budget incomeBudget = new Budget();
        incomeBudget.category = "income";
        incomeBudget.amount = Float.parseFloat(incomeInput.getText().toString());
        ourBudgetsDatabase.add(incomeBudget);

        Budget tithingBudget = new Budget();
        tithingBudget.category = "tithing";
        tithingBudget.amount = Float.parseFloat(tithingInput.getText().toString());
        ourBudgetsDatabase.add(tithingBudget);

        Budget utilitiesBudget = new Budget();
        utilitiesBudget.category = "utilities";
        utilitiesBudget.amount = Float.parseFloat(utilitiesInput.getText().toString());
        ourBudgetsDatabase.add(utilitiesBudget);

        Budget rentBudget = new Budget();
        rentBudget.category = "rent";
        rentBudget.amount = Float.parseFloat(rentInput.getText().toString());
        ourBudgetsDatabase.add(rentBudget);

        Budget foodBudget = new Budget();
        foodBudget.category = "food";
        foodBudget.amount = Float.parseFloat(foodInput.getText().toString());
        ourBudgetsDatabase.add(foodBudget);
    }

    private void showSuccessMessage(View view) {
        Snackbar.make(view, "Budget saved", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void goToTransactionList() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, TransactionListFragment.newInstance())
                .commit();
    }

}
