package com.grushevskyi.interestingfactsaboutnumbers.db


import androidx.room.Update
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


interface Dao {

    @Query("SELECT * FROM fact")
    fun getAll(): List<Fact>

    @Insert
    fun insert(fact: Fact)

    @Delete
    fun delete(fact: Fact)

    @Update
    fun update(fact: Fact)
}