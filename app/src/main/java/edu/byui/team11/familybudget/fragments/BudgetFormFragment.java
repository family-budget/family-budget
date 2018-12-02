package edu.byui.team11.familybudget.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import java.util.Locale;

import edu.byui.team11.familybudget.MainActivity;
import edu.byui.team11.familybudget.R;
import edu.byui.team11.familybudget.model.Budget;
import edu.byui.team11.familybudget.model.Transaction;
import edu.byui.team11.familybudget.viewmodel.BudgetViewModel;
import edu.byui.team11.familybudget.viewmodel.TransactionViewModel;

public class BudgetFormFragment extends Fragment {

    private List<Budget> ourBudgetsDatabase = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.budget_form, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final View formView = getView();

        BudgetViewModel budgetViewModel = ViewModelProviders.of(getActivity()).get(BudgetViewModel.class);
        budgetViewModel.getAllBudgets().observe(getActivity(), new Observer<List<Budget>>() {
            @Override
            public void onChanged(@Nullable List<Budget> budgets) {

                EditText incomeInput = formView.findViewById(R.id.incomeInput);
                EditText tithingInput = formView.findViewById(R.id.tithingInput);
                EditText utilitiesInput = formView.findViewById(R.id.utilitiesInput);
                EditText rentInput = formView.findViewById(R.id.rentInput);
                EditText foodInput = formView.findViewById(R.id.foodInput);
                EditText otherInput = formView.findViewById(R.id.otherInput);

                for (Budget b : budgets) {
                    if (b.category.equalsIgnoreCase("income")) {
                        incomeInput.setText(String.format(Locale.getDefault(), "%.2f", b.amount));
                    }

                    if (b.category.equalsIgnoreCase("tithing")) {
                        tithingInput.setText(String.format(Locale.getDefault(), "%.2f", b.amount));
                    }

                    if (b.category.equalsIgnoreCase("utilities")) {
                        utilitiesInput.setText(String.format(Locale.getDefault(), "%.2f", b.amount));
                    }

                    if (b.category.equalsIgnoreCase("rent/mortgage")) {
                        rentInput.setText(String.format(Locale.getDefault(), "%.2f", b.amount));
                    }

                    if (b.category.equalsIgnoreCase("food")) {
                        foodInput.setText(String.format(Locale.getDefault(), "%.2f", b.amount));
                    }

                    if (b.category.equalsIgnoreCase("other")) {
                        otherInput.setText(String.format(Locale.getDefault(), "%.2f", b.amount));
                    }
                }
            }
        });

        configureSubmitButton((FloatingActionButton) getView().findViewById(R.id.submit_budget_button));
    }

    public void onClickSubmitButton() {
        View formView = getView();
        saveBudget(formView);
        showSuccessMessage(formView);
        goToTransactionList();
    }

    private void configureSubmitButton(FloatingActionButton bt) {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                onClickSubmitButton();
            }
        });
    }

    private void saveBudget(View formView) {
        // Get input fields from the view
        EditText incomeInput = formView.findViewById(R.id.incomeInput);
        EditText tithingInput = formView.findViewById(R.id.tithingInput);
        EditText utilitiesInput = formView.findViewById(R.id.utilitiesInput);
        EditText rentInput = formView.findViewById(R.id.rentInput);
        EditText foodInput = formView.findViewById(R.id.foodInput);
        EditText otherInput = formView.findViewById(R.id.otherInput);

        //TODO: validate

        // Create budget objects and add them to the "list"
        Budget incomeBudget = new Budget();
        incomeBudget.category = "income";
        incomeBudget.amount = Float.parseFloat(incomeInput.getText().toString());

        Budget tithingBudget = new Budget();
        tithingBudget.category = "tithing";
        tithingBudget.amount = Float.parseFloat(tithingInput.getText().toString());

        Budget utilitiesBudget = new Budget();
        utilitiesBudget.category = "utilities";
        utilitiesBudget.amount = Float.parseFloat(utilitiesInput.getText().toString());

        Budget rentBudget = new Budget();
        rentBudget.category = "rent/mortgage";
        rentBudget.amount = Float.parseFloat(rentInput.getText().toString());

        Budget foodBudget = new Budget();
        foodBudget.category = "food";
        foodBudget.amount = Float.parseFloat(foodInput.getText().toString());

        Budget otherBudget = new Budget();
        otherBudget.category = "other";
        otherBudget.amount = Float.parseFloat(otherInput.getText().toString());

        BudgetViewModel budgetViewModel = ViewModelProviders.of(getActivity()).get(BudgetViewModel.class);
        budgetViewModel.insert(
                incomeBudget,
                tithingBudget,
                utilitiesBudget,
                rentBudget,
                foodBudget,
                otherBudget
        );
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
