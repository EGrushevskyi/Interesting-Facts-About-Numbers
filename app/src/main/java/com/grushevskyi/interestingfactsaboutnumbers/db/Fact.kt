package com.grushevskyi.interestingfactsaboutnumbers.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fact")
data class Fact (
    @PrimaryKey(autoGenerate = true)var factId: Int? = null,
    val factText: String
    )