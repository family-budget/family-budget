package edu.byui.team11.familybudget.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.team11.familybudget.R;

public class BudgetFormFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.budget_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureSubmitButton((FloatingActionButton) getView().findViewById(R.id.submit_budget_form));
    }


    private void configureSubmitButton(FloatingActionButton bt) {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Save the values to the database

                getFragmentManager().beginTransaction()
                        .replace(R.id.container, TransactionListFragment.newInstance())
                        .commit();

                Snackbar.make(view, "Budget saved", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }

}
