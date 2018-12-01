package edu.byui.team11.familybudget.fragments;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import edu.byui.team11.familybudget.R;
import edu.byui.team11.familybudget.model.Transaction;
import edu.byui.team11.familybudget.viewmodel.TransactionViewModel;

public class TransactionFormFragment extends Fragment {

    private DateDialog dateDialog;

    class DateDialog {
        private final View.OnClickListener viewListener;
        private TextView view;
        private Context context;
        private Calendar calendar;
        private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        public DateDialog(Context context, TextView view) {
            this.context = context;
            this.view = view;

            this.calendar = Calendar.getInstance(Locale.getDefault());
            this.viewListener = getTextClickListener();

            this.view.setFocusable(false);
            this.view.setOnClickListener(this.viewListener);
        }

        private View.OnClickListener getTextClickListener() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog();
                }
            };
        }

        private void showDatePickerDialog() {
            this.getDatePickerDialog().show();
        }

        private DatePickerDialog getDatePickerDialog() {
            return new DatePickerDialog(this.context, getDateSetListener(), this.getYear(), this.getMonth(), this.getDay());
        }

        private int getDay() {
            return this.calendar.get(Calendar.DAY_OF_MONTH);
        }

        private int getMonth() {
            return this.calendar.get(Calendar.MONTH);
        }

        private int getYear() {
            return this.calendar.get(Calendar.YEAR);
        }

        private DatePickerDialog.OnDateSetListener getDateSetListener() {
            return new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    setDate(year, month, dayOfMonth);
                }
            };
        }

        private void setDate(int year, int month, int dayOfMonth) {
            this.calendar.clear();
            this.calendar.set(year, month, dayOfMonth);
            this.view.setText(formatter.format(this.calendar.getTime()));
        }

        public Date getDate() {
            return this.calendar.getTime();
        }

    }

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
        configureSubmitButton((FloatingActionButton) getView().findViewById(R.id.submit_transaction_form));
        this.dateDialog = new DateDialog(getContext(), (TextView) getView().findViewById(R.id.dateInput));
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

    private void saveTransactions(View view) {
        // Get input fields from the view
        Transaction transaction = new Transaction();

        EditText categoryInput = view.findViewById(R.id.spinnerCategory);
        EditText amountInput = view.findViewById(R.id.amountInput);

        transaction.category = categoryInput.getText().toString();
        transaction.amount = Float.parseFloat(amountInput.getText().toString());
        transaction.budgetedAt = this.dateDialog.getDate();

        this.viewModel.insert(transaction);
    }
}
