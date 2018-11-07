package edu.byui.team11.familybudget.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.byui.team11.familybudget.model.Budget;

@Dao
public interface BudgetDAO {

    @Insert
    public void create(Budget... budgets);

    @Update
    public void update(Budget... budgets);

    @Delete
    public void delete(Budget... budgets);

    @Query("SELECT * FROM budget")
    public List<Budget> findAll();
}
