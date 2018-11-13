package edu.byui.team11.familybudget.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.team11.familybudget.R;

public class TransactionListFragment extends Fragment {
    private MainViewModel mViewModel;

    public static TransactionListFragment newInstance() {
        return new TransactionListFragment();
    }

    private void configureAddTransactionButton(FloatingActionButton bt) {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new TransactionFormFragment())
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

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

}
