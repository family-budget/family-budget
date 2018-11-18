package edu.byui.team11.familybudget;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.byui.team11.familybudget.database.ApplicationDatabase;
import edu.byui.team11.familybudget.fragments.TransactionListFragment;

public class MainActivity extends AppCompatActivity {
    private static ApplicationDatabase db;

    public static ApplicationDatabase getDatabase() {
        return MainActivity.db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Here we have an instance of the database
        MainActivity.db = Room.databaseBuilder(getApplicationContext(),
                ApplicationDatabase.class, "database-name").build();

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