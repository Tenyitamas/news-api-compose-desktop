package com.tenyitamas.kip.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.tenyitamas.kip.Database
import java.io.File

object DatabaseDriverFactory {
    fun createDriver(): SqlDriver {
        val databasePath = File(System.getProperty("user.home"), "articles.db")
        val databaseFile = File(databasePath.absolutePath)
        val driver = JdbcSqliteDriver(url = "jdbc:sqlite:${databasePath.absolutePath}")

        // Only create the schema if the database doesn't exist yet
        if (!databaseFile.exists()) {
            Database.Schema.create(driver)
        }

        return driver
    }
}
