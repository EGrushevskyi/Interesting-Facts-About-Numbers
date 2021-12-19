package com.grushevskyi.interestingfactsaboutnumbers.db

import android.content.Context
import android.os.AsyncTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class FactRepository(context: Context) {

    var db: Dao = AppDatabase.getInstance(context)?.factDao()!!

    fun getAllFacts(): List<Fact> {
        return db.getAll()
    }

    fun insertFact(fact: Fact) {
        CoroutineTask(db).doOperation(fact)
    }

    private class CoroutineTask(private val factsDao: Dao): CoroutineScope by MainScope() {
        fun doOperation(vararg params: Fact) {
            factsDao.insert(params[0])
        }
    }
}