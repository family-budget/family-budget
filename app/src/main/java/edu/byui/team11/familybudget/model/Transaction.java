package edu.byui.team11.familybudget.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.widget.EditText;

@Entity(tableName = "transactions")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String category;

    @ColumnInfo
    public float amount;

   // @ColumnInfo
   // public boolean incomeExpense;

}
