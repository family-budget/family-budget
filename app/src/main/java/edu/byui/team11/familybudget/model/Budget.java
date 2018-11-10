package edu.byui.team11.familybudget.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Budget {
    @PrimaryKey
    public int id;

    @ColumnInfo
    public int amount;
}
