package edu.byui.team11.familybudget.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.byui.team11.familybudget.R;
import edu.byui.team11.familybudget.adapters.TransactionListAdapter;
import edu.byui.team11.familybudget.model.Transaction;
import edu.byui.team11.familybudget.viewmodel.TransactionViewModel;

import static java.util.Objects.requireNonNull;

public class TransactionListFragment extends Fragment {
    private static final String APP_BACKSTACK = "app-back-stack";
    private TransactionListAdapter adapter;

    public static TransactionListFragment newInstance() {
        return new TransactionListFragment();
    }

    private void configureAddTransactionButton(FloatingActionButton bt) {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new TransactionFormFragment())
                        .addToBackStack(APP_BACKSTACK)
                        .commit();
            }
        });
    }

    private void configureChangeBudgetButton(FloatingActionButton bt) {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new BudgetFormFragment())
                        .addToBackStack(APP_BACKSTACK)
                        .commit();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transaction_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        configureAddTransactionButton((FloatingActionButton) getView().findViewById(R.id.add_transaction));
        configureChangeBudgetButton((FloatingActionButton) getView().findViewById(R.id.configure_budget));

        FragmentActivity activity = requireNonNull(getActivity());
        this.adapter = new TransactionListAdapter(activity);
        configureTransactionList(activity);
        updateAdapterWhenTransactionsChange(activity);
    }

    private void configureTransactionList(FragmentActivity activity) {
        RecyclerView transactionsList = getView().findViewById(R.id.transactionsList);
        //transactionsList.setHasFixedSize(true);
        transactionsList.setLayoutManager(new LinearLayoutManager(activity));
        transactionsList.setAdapter(this.adapter);
    }

    private void updateAdapterWhenTransactionsChange(FragmentActivity activity) {
        // Get a new or existing ViewModel from the ViewModelProvider.
        TransactionViewModel transactionViewModel = ViewModelProviders.of(activity).get(TransactionViewModel.class);
        transactionViewModel.getAllTransactions().observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(@Nullable List<Transaction> transactions) {
                adapter.setTransactions(transactions);
            }
        });
    }
}
