package edu.byui.team11.familybudget.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import edu.byui.team11.familybudget.entities.Category;
import java.util.List;

/**
 * Data access object for the transactions table
 */
@Dao
public interface CategoryDAO {

  /**
   * Stores {@link Category} into the database
   * @param categories
   */
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void save(Category... categories);

  /**
   * Retrieves all {@link Category} from the database
   * @return
   */
  @Query("SELECT * FROM categories")
  LiveData<List<Category>> getAll();
}
