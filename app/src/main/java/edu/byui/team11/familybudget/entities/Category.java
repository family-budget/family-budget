package edu.byui.team11.familybudget.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


/**
 * Category represents budgeted amounts for a "label".
 **/

@Entity(tableName = "categories")
public class Category {

  @PrimaryKey
  @NonNull
  public String name;

  @ColumnInfo
  public float budgetedAmount;
}


