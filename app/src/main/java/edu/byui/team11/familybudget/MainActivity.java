package edu.byui.team11.familybudget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.byui.team11.familybudget.ui.main.TransactionListFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState != null) {
            return;
        }

        // Start the activity with the TransactionListFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, TransactionListFragment.newInstance())
                .commitNow();
    }
}