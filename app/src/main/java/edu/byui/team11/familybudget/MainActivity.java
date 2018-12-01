package edu.byui.team11.familybudget;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.byui.team11.familybudget.adapters.TransactionListAdapter;
import edu.byui.team11.familybudget.database.ApplicationDatabase;
import edu.byui.team11.familybudget.fragments.TransactionListFragment;

public class MainActivity extends AppCompatActivity {
public static ApplicationDatabase budgetDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        budgetDatabase = Room.databaseBuilder(getApplicationContext(),ApplicationDatabase.class, "appDB").build();

        if (savedInstanceState != null) {
            return;
        }

        //TODO: If there is no budget configured, show the BudgetFormFragment instead of TransactionListFragment

        // Start the activity with the TransactionListFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, TransactionListFragment.newInstance())
                .commitNow();
    }
}