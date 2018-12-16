package edu.byui.team11.familybudget.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import edu.byui.team11.familybudget.dao.BudgetDAO;
import edu.byui.team11.familybudget.database.ApplicationDatabase;
import edu.byui.team11.familybudget.pojo.Budget;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BudgetViewModel extends AndroidViewModel {

  private final BudgetDAO repository;

  public BudgetViewModel(@NonNull Application application) {
    super(application);
    this.repository = ApplicationDatabase.getInstance(application).getBudgetDAO();
  }

  public void refreshChart(BarChart chart) {
    Calendar begin = Calendar.getInstance();
    begin.set(Calendar.DAY_OF_MONTH, 1);
    begin.set(Calendar.HOUR, 0);
    begin.set(Calendar.MINUTE, 0);
    begin.set(Calendar.SECOND, 0);

    Calendar end = Calendar.getInstance();
    end.set(Calendar.DAY_OF_MONTH, begin.getActualMaximum(Calendar.DAY_OF_MONTH));
    end.set(Calendar.HOUR, 23);
    end.set(Calendar.MINUTE, 59);
    end.set(Calendar.SECOND, 59);

    GetBudgetTask task = new GetBudgetTask(this.repository, chart);
    task.execute(begin.getTime(), end.getTime());
  }

  private static class GetBudgetTask extends AsyncTask<Date, Void, List<Budget>> {

    private final BudgetDAO repository;
    private final BarChart chart;

    GetBudgetTask(BudgetDAO repository, BarChart chart) {
      this.repository = repository;
      this.chart = chart;
    }

    @Override
    protected List<Budget> doInBackground(Date... dates) {
      return this.repository.getByDate(dates[0], dates[1]);
    }

    @Override
    protected void onPostExecute(List<Budget> result) {
      //Convert budgets to entries
      List<String> labels = new ArrayList<>();
      List<BarEntry> entries = new ArrayList<>();

      for (int i = 0; i < result.size(); i++) {
        labels.add(result.get(i).category);
        entries.add(new BarEntry(i, result.get(i).amount));
      }

      BarDataSet set = new BarDataSet(entries, "budget");
      set.setColors(ColorTemplate.MATERIAL_COLORS);

      BarData data = new BarData(set);
      data.setBarWidth(0.8f); // set custom bar width
      data.setDrawValues(false);

      chart.setData(data);
      chart.getXAxis().setValueFormatter(new MyXAxisValueFormatter(labels));

      chart.invalidate(); // refresh
    }

    public class MyXAxisValueFormatter implements IAxisValueFormatter {

      private List<String> mValues;

      MyXAxisValueFormatter(List<String> values) {
        this.mValues = values;
      }

      @Override
      public String getFormattedValue(float value, AxisBase axis) {
        return mValues.get((int) value);
      }
    }
  }
}
