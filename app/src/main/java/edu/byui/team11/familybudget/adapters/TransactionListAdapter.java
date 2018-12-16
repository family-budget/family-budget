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
import edu.byui.team11.familybudget.entities.Transaction;

/**
 * TransactionListAdapter adapts {@link Transaction} objects into {@link TransactionViewHolder}
 * objects.
 */
public class TransactionListAdapter extends
    RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder> {

  private final LayoutInflater inflater;
  private List<Transaction> transactions = Collections.emptyList(); // Cached copy of transactions

  /**
   * @param context Context used by the LayoutInflater
   */
  public TransactionListAdapter(Context context) {
    this.inflater = LayoutInflater.from(context);
  }

  /**
   * Set transactions to be adapted
   */
  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
    notifyDataSetChanged();
  }

  /**
   * Called when {@link RecyclerView} needs a new {@link RecyclerView.ViewHolder} of the given type
   * to represent an item.
   *
   * @param parent The {@link ViewGroup} into which the new {@link View} will be added.
   * @param i The view type of the new View
   * @return A {@link TransactionViewHolder} that holds a {@link View} for the {@link Transaction}
   */
  @NonNull
  @Override
  public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    View itemView = this.inflater.inflate(R.layout.transaction_list_item, parent, false);
    return new TransactionViewHolder(itemView);
  }

  /**
   * Updates the contents of the {@link TransactionViewHolder} to reflect the item at the given
   * position.
   *
   * @param holder The {@link TransactionViewHolder} to be updated
   * @param position The position of the {@link Transaction} it refers to
   */
  @Override
  public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
    // Replace the contents of a view (invoked by the layout manager)
    Transaction current = this.transactions.get(position);
    holder.setDescription(current.category);
    holder.setAmount(current.amount);
    holder.setDate(current.budgetedAt);
  }

  /**
   * Returns the total number of items in the data set held by the adapter.
   *
   * @return The total number of items in this adapter.
   */
  @Override
  public int getItemCount() {
    // Return the size of your dataset (invoked by the layout manager)
    return transactions.size();
  }

  /**
   * Holds a view to represent a {@link Transaction}
   */
  public class TransactionViewHolder extends RecyclerView.ViewHolder {

    private TextView descriptionView;
    private TextView amountView;
    private TextView dateView;
    private Locale locale = Locale.getDefault();

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", locale);

    /**
     * @param itemView A {@link Transaction}'s view
     */
    TransactionViewHolder(View itemView) {
      super(itemView);

      descriptionView = itemView.findViewById(R.id.description);
      amountView = itemView.findViewById(R.id.amount);
      dateView = itemView.findViewById(R.id.date);
    }

    /**
     * Sets the description on the View
     */
    public void setDescription(String description) {
      descriptionView.setText(String.format(locale, "Category: %s", description));
    }

    /**
     * Sets the budgetedAmount on the View
     */
    public void setAmount(float amount) {
      //TODO: Should show everything but incomes as a negative value
      amountView.setText(String.format(locale, "U$ %.2f", amount));
    }

    /**
     * Sets the date on the View
     */
    public void setDate(Date date) {
      dateView.setText(formatter.format(date));
    }
  }
}
