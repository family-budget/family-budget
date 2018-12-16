package edu.byui.team11.familybudget.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.byui.team11.familybudget.model.Transaction;


/** This contains the Transaction DAO interface and its methods **/

@Dao
public interface TransactionDAO {

    @Insert
    void insert(Transaction... transactions);

    @Update
    void update(Transaction... transactions);

    @Delete
    void delete(Transaction... transactions);

    @Query("SELECT * FROM transactions")
    List<Transaction> findAll();

    @Query("SELECT * from transactions ORDER BY budgetedAt ASC")
    LiveData<List<Transaction>> getAllTransactions();
}
