package com.example.demo_celsius_activities_db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_CONVERSION_TABLE = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_RESULT REAL," +
                "$COLUMN_TYPE TEXT)")
        db.execSQL(CREATE_CONVERSION_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addConversionResult(result: Double) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_RESULT, result)
            put(COLUMN_TYPE, ConversionType.tipoConv)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun deleteAllResults() {
        writableDatabase.use { it.delete(TABLE_NAME, null, null) }
    }

    fun getAllResults(): List<Resultado> {
        val resultList = mutableListOf<Resultado>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_ID, $COLUMN_RESULT, $COLUMN_TYPE FROM $TABLE_NAME", null)
        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow(COLUMN_ID))
                val result = it.getDouble(it.getColumnIndexOrThrow(COLUMN_RESULT))
                val tipoC = it.getString(it.getColumnIndexOrThrow(COLUMN_TYPE))
                resultList.add(Resultado(id, result, tipoC))
            }
        }
        return resultList
    }

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "conversiones.db"
        private const val TABLE_NAME = "conversion_temperatura"
        private const val COLUMN_ID = "id"
        private const val COLUMN_RESULT = "resultado"
        private const val COLUMN_TYPE = "tipo"
    }
}
