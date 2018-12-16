package edu.byui.team11.familybudget.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import edu.byui.team11.familybudget.converter.DateConverter;
import edu.byui.team11.familybudget.pojo.Budget;
import java.util.Date;
import java.util.List;


/**
 * This contains the Budget DAO interface and its methods
 */
@Dao
public interface BudgetDAO {

  @Query("SELECT category, SUM(amount) as amount FROM transactions GROUP BY category")
  List<Budget> getAll();

  @TypeConverters({DateConverter.class})
  @Query("SELECT category, SUM(amount) as amount FROM transactions WHERE budgetedAt >= :begin AND budgetedAt <= :end GROUP BY category")
  List<Budget> getByDate(Date begin, Date end);
}
