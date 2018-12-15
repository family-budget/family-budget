package edu.byui.team11.familybudget.fragments;

import static java.util.Objects.requireNonNull;

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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import edu.byui.team11.familybudget.R;
import edu.byui.team11.familybudget.adapters.TransactionListAdapter;
import edu.byui.team11.familybudget.model.Transaction;
import edu.byui.team11.familybudget.viewmodel.TransactionViewModel;
import java.util.ArrayList;
import java.util.List;

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

    configureAddTransactionButton(
        (FloatingActionButton) getView().findViewById(R.id.add_transaction));
    configureChangeBudgetButton(
        (FloatingActionButton) getView().findViewById(R.id.configure_budget));

    FragmentActivity activity = requireNonNull(getActivity());
    this.adapter = new TransactionListAdapter(activity);
    configureTransactionList(activity);
    updateAdapterWhenTransactionsChange(activity);

    drawChart();
  }

  private void drawChart() {
    BarChart chart = getView().findViewById(R.id.chart);

    List<BarEntry> entries = new ArrayList<>();
    entries.add(new BarEntry(0f, 13));
    entries.add(new BarEntry(1f, 12));
    entries.add(new BarEntry(2f, 3));

    BarDataSet set = new BarDataSet(entries, "Utilities");
    BarData data = new BarData(set);
    data.setBarWidth(0.8f); // set custom bar width
    chart.setFitBars(true); // make the x-axis fit exactly all bars

    chart.setTouchEnabled(false);
    chart.setDragEnabled(false);
    chart.setScaleEnabled(false);
    chart.setDrawBarShadow(false);
    chart.setDrawValueAboveBar(true);
    chart.getDescription().setEnabled(false);
    chart.setDrawGridBackground(false);

    String[] values = new String[]{"Utilities", "second", "third", "fourth"};
    XAxis xAxis = chart.getXAxis();
    xAxis.setValueFormatter(new MyXAxisValueFormatter(values));
    //sxAxis.setAxisMinimum(0);
    xAxis.setDrawAxisLine(false);
    xAxis.setDrawGridLines(false);
    xAxis.setPosition(XAxisPosition.BOTTOM);
    xAxis.setGranularity(1f); // minimum axis-step (interval) is 1

    YAxis axisLeft = chart.getAxisLeft();
    axisLeft.setDrawGridLines(false);
    axisLeft.setSpaceTop(15f);
    axisLeft.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
    axisLeft.setAxisMinimum(0f); // this replaces setStartAtZero(true)

    YAxis axisRight = chart.getAxisRight();
    axisRight.setDrawGridLines(false);
    axisRight.setSpaceTop(15f);
    //axisRight.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
    axisRight.setAxisMinimum(0f); // this replaces setStartAtZero(true)
    axisRight.setDrawLabels(false);

    Legend l = chart.getLegend();
    l.setEnabled(false);

    chart.setData(data);
    chart.invalidate(); // refresh
  }

  public class MyXAxisValueFormatter implements IAxisValueFormatter {

    private String[] mValues;

    public MyXAxisValueFormatter(String[] values) {
      this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
      return mValues[(int) value];
    }
  }


  private void configureTransactionList(FragmentActivity activity) {
    RecyclerView transactionsList = getView().findViewById(R.id.transactionsList);
    //transactionsList.setHasFixedSize(true);
    transactionsList.setLayoutManager(new LinearLayoutManager(activity));
    transactionsList.setAdapter(this.adapter);
  }

  private void updateAdapterWhenTransactionsChange(FragmentActivity activity) {
    // Get a new or existing ViewModel from the ViewModelProvider.
    TransactionViewModel transactionViewModel = ViewModelProviders.of(activity)
        .get(TransactionViewModel.class);
    transactionViewModel.getAllTransactions().observe(this, new Observer<List<Transaction>>() {
      @Override
      public void onChanged(@Nullable List<Transaction> transactions) {
        adapter.setTransactions(transactions);
      }
    });
  }
}
