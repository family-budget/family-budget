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
import edu.byui.team11.familybudget.R;
import edu.byui.team11.familybudget.model.Category;
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

    CategoryViewModel categoryViewModel = ViewModelProviders.of(getActivity())
        .get(CategoryViewModel.class);
    categoryViewModel.getAllCategories().observe(getActivity(), getCategoriesObserver(formView));

    configureSubmitButton((FloatingActionButton) formView.findViewById(R.id.submit_budget_button));
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
    //TODO: validate

    // Create budget objects and add them to the "list"
    Category incomeCategory = new Category();
    incomeCategory.name = "income";
    incomeCategory.budgetedAmount = Float.parseFloat(incomeInput.getText().toString());

    Category tithingCategory = new Category();
    tithingCategory.name = "tithing";
    tithingCategory.budgetedAmount = Float.parseFloat(tithingInput.getText().toString());

    Category utilitiesCategory = new Category();
    utilitiesCategory.name = "utilities";
    utilitiesCategory.budgetedAmount = Float.parseFloat(utilitiesInput.getText().toString());

    Category rentCategory = new Category();
    rentCategory.name = "rent/mortgage";
    rentCategory.budgetedAmount = Float.parseFloat(rentInput.getText().toString());

    Category foodCategory = new Category();
    foodCategory.name = "food";
    foodCategory.budgetedAmount = Float.parseFloat(foodInput.getText().toString());

    Category otherCategory = new Category();
    otherCategory.name = "other";
    otherCategory.budgetedAmount = Float.parseFloat(otherInput.getText().toString());

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
