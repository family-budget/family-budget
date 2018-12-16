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
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;
import edu.byui.team11.familybudget.R;
import edu.byui.team11.familybudget.entities.Category;
import edu.byui.team11.familybudget.viewmodel.CategoryViewModel;
import java.util.List;
import java.util.Locale;

/**
 * Fragment used to manage categories and their budgeted amounts
 */
public class BudgetFormFragment extends Fragment {

  private EditText incomeInput;
  private EditText tithingInput;
  private EditText utilitiesInput;
  private EditText rentInput;
  private EditText foodInput;
  private EditText otherInput;
  private EditText balanceInput;

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
    incomeInput = formView.findViewById(R.id.incomeInput);
    tithingInput = formView.findViewById(R.id.tithingInput);
    utilitiesInput = formView.findViewById(R.id.utilitiesInput);
    rentInput = formView.findViewById(R.id.rentInput);
    foodInput = formView.findViewById(R.id.foodInput);
    otherInput = formView.findViewById(R.id.otherInput);
    balanceInput = formView.findViewById(R.id.balanceInput);
    balanceInput.setFocusable(false);

    updateBalanceWhenSomethingElseChanges();

    CategoryViewModel categoryViewModel = ViewModelProviders.of(getActivity())
        .get(CategoryViewModel.class);
    categoryViewModel.getAllCategories().observe(getActivity(), getCategoriesObserver(formView));

    configureSubmitButton((FloatingActionButton) formView.findViewById(R.id.submit_budget_button));
  }

  private void updateBalanceWhenSomethingElseChanges() {
    OnFocusChangeListener listener = new OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
          return;
        }

        balanceInput.setText(calculateBalance());
      }
    };

    incomeInput.setOnFocusChangeListener(listener);
    tithingInput.setOnFocusChangeListener(listener);
    utilitiesInput.setOnFocusChangeListener(listener);
    rentInput.setOnFocusChangeListener(listener);
    foodInput.setOnFocusChangeListener(listener);
    otherInput.setOnFocusChangeListener(listener);
  }

  private String calculateBalance() {
    Float income = 0f;
    Float tithing = 0f;
    Float utilities = 0f;
    Float rent = 0f;
    Float food = 0f;
    Float other = 0f;

    if (!incomeInput.getText().toString().isEmpty()) {
      income = Float.parseFloat(incomeInput.getText().toString());
    }

    if (!tithingInput.getText().toString().isEmpty()) {
      tithing = Float.parseFloat(tithingInput.getText().toString());
    }

    if (!utilitiesInput.getText().toString().isEmpty()) {
      utilities = Float.parseFloat(utilitiesInput.getText().toString());
    }

    if (!rentInput.getText().toString().isEmpty()) {
      rent = Float.parseFloat(rentInput.getText().toString());
    }

    if (!foodInput.getText().toString().isEmpty()) {
      food = Float.parseFloat(foodInput.getText().toString());
    }

    if (!otherInput.getText().toString().isEmpty()) {
      other = Float.parseFloat(otherInput.getText().toString());
    }

    Float balance = income - tithing - utilities - rent - food - other;
    return String.format(Locale.getDefault(), "%.2f", balance);
  }

  /**
   * Builds a {@link Observer} to update the inputs in the form when {@link Category} are changed.
   */
  @NonNull
  private Observer<List<Category>> getCategoriesObserver(final View formView) {

    return new Observer<List<Category>>() {
      @Override
      public void onChanged(@Nullable List<Category> categories) {

        for (Category b : categories) {
          if (b.name.equalsIgnoreCase("income")) {
            incomeInput.setText(String.format(Locale.getDefault(), "%.2f", b.budgetedAmount));
          }

          if (b.name.equalsIgnoreCase("tithing")) {
            tithingInput.setText(String.format(Locale.getDefault(), "%.2f", b.budgetedAmount));
          }

          if (b.name.equalsIgnoreCase("utilities")) {
            utilitiesInput.setText(String.format(Locale.getDefault(), "%.2f", b.budgetedAmount));
          }

          if (b.name.equalsIgnoreCase("rent/mortgage")) {
            rentInput.setText(String.format(Locale.getDefault(), "%.2f", b.budgetedAmount));
          }

          if (b.name.equalsIgnoreCase("food")) {
            foodInput.setText(String.format(Locale.getDefault(), "%.2f", b.budgetedAmount));
          }

          if (b.name.equalsIgnoreCase("other")) {
            otherInput.setText(String.format(Locale.getDefault(), "%.2f", b.budgetedAmount));
          }
        }

        balanceInput.setText(calculateBalance());
      }
    };
  }

  private void configureSubmitButton(FloatingActionButton bt) {
    final View formView = getView();

    bt.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View button) {
        saveBudget();

        Snackbar.make(formView, "Category saved", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();

        getFragmentManager().beginTransaction()
            .replace(R.id.container, TransactionListFragment.newInstance())
            .commit();
      }
    });
  }

  /**
   * Get user input and stores the categories on the database
   */
  private void saveBudget() {
    Float income = 0f;
    Float tithing = 0f;
    Float utilities = 0f;
    Float rent = 0f;
    Float food = 0f;
    Float other = 0f;

    if (!incomeInput.getText().toString().isEmpty()) {
      income = Float.parseFloat(incomeInput.getText().toString());
    }

    if (!tithingInput.getText().toString().isEmpty()) {
      tithing = Float.parseFloat(tithingInput.getText().toString());
    }

    if (!utilitiesInput.getText().toString().isEmpty()) {
      utilities = Float.parseFloat(utilitiesInput.getText().toString());
    }

    if (!rentInput.getText().toString().isEmpty()) {
      rent = Float.parseFloat(rentInput.getText().toString());
    }

    if (!foodInput.getText().toString().isEmpty()) {
      food = Float.parseFloat(foodInput.getText().toString());
    }

    if (!otherInput.getText().toString().isEmpty()) {
      other = Float.parseFloat(otherInput.getText().toString());
    }

    // Create budget objects and add them to the "list"
    Category incomeCategory = new Category();
    incomeCategory.name = "income";
    incomeCategory.budgetedAmount = income;

    Category tithingCategory = new Category();
    tithingCategory.name = "tithing";
    tithingCategory.budgetedAmount = tithing;

    Category utilitiesCategory = new Category();
    utilitiesCategory.name = "utilities";
    utilitiesCategory.budgetedAmount = utilities;

    Category rentCategory = new Category();
    rentCategory.name = "rent/mortgage";
    rentCategory.budgetedAmount = rent;

    Category foodCategory = new Category();
    foodCategory.name = "food";
    foodCategory.budgetedAmount = food;

    Category otherCategory = new Category();
    otherCategory.name = "other";
    otherCategory.budgetedAmount = other;

    CategoryViewModel categoryViewModel = ViewModelProviders.of(getActivity())
        .get(CategoryViewModel.class);

    categoryViewModel.insert(
        incomeCategory,
        tithingCategory,
        utilitiesCategory,
        rentCategory,
        foodCategory,
        otherCategory
    );
  }

}
