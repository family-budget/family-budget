package edu.byui.team11.familybudget.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


/**
 Budget represents the budgeted amount for each category.
 **/

@Entity(tableName = "budgets")
public class Budget {
    @PrimaryKey
    public int id;

    @ColumnInfo
    public String category;

    @ColumnInfo
    public float amount;
}


