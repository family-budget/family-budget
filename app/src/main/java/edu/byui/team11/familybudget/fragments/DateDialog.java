package edu.byui.team11.familybudget.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Wraps a {@link TextView} into a {@link DatePickerDialog}
 */
class DateDialog {

  private final View.OnClickListener viewListener;
  private TextView view;
  private Context context;
  private Calendar calendar;
  private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

  DateDialog(Context context, TextView view) {
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
    return new DatePickerDialog(this.context, getDateSetListener(),
        this.getYear(), this.getMonth(), this.getDay());
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
