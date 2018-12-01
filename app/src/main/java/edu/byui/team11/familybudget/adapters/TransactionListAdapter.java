package edu.byui.team11.familybudget.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.byui.team11.familybudget.R;
import edu.byui.team11.familybudget.model.Transaction;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder> {
    private final LayoutInflater inflater;
    private List<Transaction> transactions = Collections.emptyList(); // Cached copy of transactions


    public TransactionListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        // Create new views (invoked by the layout manager)
        View itemView = this.inflater.inflate(R.layout.transaction_list_item, parent, false);
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        // Replace the contents of a view (invoked by the layout manager)
        Transaction current = this.transactions.get(position);
        holder.setDescription(current.category);
        holder.setAmount(current.amount);
        holder.setDate(current.budgetedAt);
    }

    @Override
    public int getItemCount() {
        // Return the size of your dataset (invoked by the layout manager)
        return transactions.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionView;
        TextView amountView;
        TextView dateView;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        public TransactionViewHolder(View itemView) {
            super(itemView);

            descriptionView = itemView.findViewById(R.id.description);
            amountView = itemView.findViewById(R.id.amount);
            dateView = itemView.findViewById(R.id.date);
        }

        public void setDescription(String description) {
            descriptionView.setText("Category: " + description);
        }

        public void setAmount(float amount) {
            amountView.setText(String.format(Locale.getDefault(), "U$ %.2f", amount));
        }

        public void setDate(Date date) {
            dateView.setText(String.format(formatter.format(date)));
        }
    }
}
