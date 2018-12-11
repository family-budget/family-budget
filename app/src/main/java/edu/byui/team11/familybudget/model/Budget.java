package edu.byui.team11.familybudget.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


/**
 * Budget represents the budgeted amount for each category.
 **/

@Entity(tableName = "budgets")
public class Budget {

  @PrimaryKey
  @NonNull
  public String category;

  @ColumnInfo
  public float amount;
}


