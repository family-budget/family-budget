package edu.byui.team11.familybudget.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import edu.byui.team11.familybudget.model.Budget;
import java.util.List;

@Dao
public interface BudgetDAO {

  /**
   * Stores {@link Budget} into the database
   * @param budgets
   */
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void save(Budget... budgets);

  /**
   * Retrieves all {@link Budget} from the database
   * @return
   */
  @Query("SELECT * FROM budgets")
  LiveData<List<Budget>> getAll();
}
