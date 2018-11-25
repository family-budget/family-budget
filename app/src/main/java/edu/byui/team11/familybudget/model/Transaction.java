package edu.byui.team11.familybudget.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.widget.EditText;

import java.util.Date;

import edu.byui.team11.familybudget.converter.DateConverter;

@Entity(tableName = "transactions")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    public long id;

    //TODO: This will be problematic once we share transactions among users in different timezones
    @ColumnInfo @TypeConverters({DateConverter.class})
    public Date budgetedAt;

    @ColumnInfo
    public String category;

    @ColumnInfo
    public float amount;
}
