package com.sahidev.maknyuss.data.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object QueryUtils {

    fun getRecipeQuery(query: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM Recipe WHERE ")
        if (query.isNotBlank()) simpleQuery.append("title LIKE :${query}")

        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}