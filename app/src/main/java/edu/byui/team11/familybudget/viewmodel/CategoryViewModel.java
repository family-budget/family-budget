package edu.byui.team11.familybudget.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import edu.byui.team11.familybudget.dao.CategoryDAO;
import edu.byui.team11.familybudget.database.ApplicationDatabase;
import edu.byui.team11.familybudget.entities.Category;
import java.util.List;

/**
 *
 */
public class CategoryViewModel extends AndroidViewModel {

  private final CategoryDAO repository;
  private final LiveData<List<Category>> categories;

  public CategoryViewModel(@NonNull Application application) {
    super(application);

    this.repository = ApplicationDatabase.getInstance(application).getCategoryDAO();
    this.categories = repository.getAll();
  }

  /**
   * Get all categories from the database
   */
  public LiveData<List<Category>> getAllCategories() {
    return this.categories;
  }

  /**
   * Insert categories into the database in a {@link AsyncTask}
   */
  public void insert(Category... categories) {
    new insertAsyncTask(this.repository).execute(categories);
  }

  /**
   * {@link AsyncTask} that makes a call to the database outside of the main thread
   */
  private static class insertAsyncTask extends AsyncTask<Category, Void, Void> {

    private final CategoryDAO repository;

    insertAsyncTask(CategoryDAO repository) {
      this.repository = repository;
    }

    @Override
    protected Void doInBackground(Category... categories) {
      this.repository.save(categories);
      return null;
    }
  }
}
