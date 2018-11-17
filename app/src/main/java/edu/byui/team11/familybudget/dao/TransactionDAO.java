package edu.byui.team11.familybudget.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.byui.team11.familybudget.model.Transaction;

@Dao
public interface TransactionDAO {

    @Insert
    void create(Transaction... transactions);

    @Update
    void update(Transaction... transactions);

    @Delete
    void delete(Transaction... transactions);

    @Query("SELECT * FROM transaction")
    List<Transaction> findAll();
}
