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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import edu.byui.team11.familybudget.R;
import edu.byui.team11.familybudget.entities.Transaction;
import edu.byui.team11.familybudget.viewmodel.TransactionViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransactionFormFragment extends Fragment {

  private DateDialog dateDialog;
  private String selectedCategory = "";
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

    configureSubmitButton(
        (FloatingActionButton) getView().findViewById(R.id.submit_transaction_form));

    this.dateDialog = new DateDialog(getContext(),
        (TextView) getView().findViewById(R.id.dateInput));

    addItemsOnSpinner(getView());
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

  public void addItemsOnSpinner(View view) {
    final List<String> categories = new ArrayList<String>();
    categories.add("Income");
    categories.add("Tithing");
    categories.add("Utilities");
    categories.add("Rent/Mortgage");
    categories.add("Food");
    categories.add("Other");

    Spinner spinner = view.findViewById(R.id.spinnerCategory);
    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCategory = categories.get(position);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        selectedCategory = "";
      }
    });

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
        android.R.layout.simple_spinner_item, categories);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
  }

  private void saveTransactions(View view) {
    // Get input fields from the view
    Transaction transaction = new Transaction();
    EditText amountInput = view.findViewById(R.id.amountInput);
    Float amount = 0f;

    if (!amountInput.getText().toString().isEmpty()) {
      amount = Float.parseFloat(amountInput.getText().toString());
    }

    transaction.category = this.selectedCategory;
    transaction.amount = amount;
    transaction.budgetedAt = this.dateDialog.getDate();

    this.viewModel.insert(transaction);
  }

}
