package edu.byui.team11.familybudget.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import edu.byui.team11.familybudget.converter.DateConverter;
import java.util.Date;


/**
 * Transaction represents the transactions that will affect our budgeted budgetedAmount depending on the
 * budgetedAmount for the transaction.
 */
@Entity(tableName = "transactions", indices = {@Index("category"), @Index("budgetedAt")})
public class Transaction {

  @PrimaryKey(autoGenerate = true)
  public long id;

  /**
   * DateConverter is used to store dates for our transactions.
   */
  //TODO: This will be problematic once we share transactions among users in different timezones
  @ColumnInfo
  @TypeConverters({DateConverter.class})
  public Date budgetedAt;

  @ColumnInfo
  public String category;

  @ColumnInfo
  public float amount;
}
