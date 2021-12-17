package com.grushevskyi.interestingfactsaboutnumbers.db

import android.content.Context
import android.os.AsyncTask

class FactRepository(context: Context) {

    var db: Dao = AppDatabase.getInstance(context)?.factDao()!!

    fun getAllFacts(): List<Fact> {
        return db.getAll()
    }

    fun insertFact(fact: Fact) {
        insertAsyncTask(db).execute(fact)
    }

    private class insertAsyncTask internal constructor(private val factsDao: Dao) :
        AsyncTask<Fact, Void, Void>() {

        override fun doInBackground(vararg params: Fact): Void? {
            factsDao.insert(params[0])
            return null
        }
    }
}