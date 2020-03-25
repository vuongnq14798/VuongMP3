package com.vuongnq14798.vuongmp3.data.source.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.vuongnq14798.vuongmp3.R
import com.vuongnq14798.vuongmp3.data.model.Genre
import com.vuongnq14798.vuongmp3.util.Constants

class DbHelper(val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)

        addGenre(
            db,
            Genre(context.getString(R.string.all_music), R.mipmap.genre_all, Constants.Genre.GENRES_ALL_MUSIC)
        )
        addGenre(
            db,
            Genre(context.getString(R.string.all_audio), R.mipmap.genre_audio, Constants.Genre.GENRES_ALL_AUDIO)
        )
        addGenre(
            db,
            Genre(context.getString(R.string.ambient), R.mipmap.genre_ambient, Constants.Genre.GENRES_AMBIENT)
        )
        addGenre(
            db,
            Genre(context.getString(R.string.classical), R.mipmap.genre_classical, Constants.Genre.GENRES_CLASSICAL)
        )
        addGenre(
            db,
            Genre(context.getString(R.string.country), R.mipmap.genre_country, Constants.Genre.GENRES_COUNTRY)
        )
        addGenre(
            db,
            Genre(context.getString(R.string.rock), R.mipmap.genre_rock, Constants.Genre.GENRES_ROCK)
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun addGenre(db: SQLiteDatabase?, genre: Genre) {
        val values = ContentValues().apply {
            put(GenreContract.GenreEntry.COLUMN_NAME_NAME, genre.genreName)
            put(GenreContract.GenreEntry.COLUMN_NAME_IMGAGE, genre.genreImage)
            put(GenreContract.GenreEntry.COLUMN_URL_GENRE, genre.genreURL)
        }

        db?.insert(GenreContract.GenreEntry.TABLE_NAME, null, values)
    }

    fun getAllGenre(): MutableList<Genre> {

        val db = this.readableDatabase
        val listGenre = mutableListOf<Genre>()

        val selectQuery = "SELECT * FROM " + GenreContract.GenreEntry.TABLE_NAME
        val cursor = db.rawQuery(selectQuery, null)

        with(cursor) {
            while (moveToNext()) {
                val genre = Genre(
                    cursor.getString(cursor.getColumnIndex(GenreContract.GenreEntry.COLUMN_NAME_NAME)),
                    cursor.getInt(cursor.getColumnIndex(GenreContract.GenreEntry.COLUMN_NAME_IMGAGE)),
                    cursor.getString(cursor.getColumnIndex(GenreContract.GenreEntry.COLUMN_URL_GENRE))
                )
                listGenre.add(genre)
            }
        }

        cursor.close()
        db.close()
        return listGenre
    }

    companion object {
        const val DATABASE_NAME = "vuong_mp3.db"
        const val DATABASE_VERSION = 1
        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${GenreContract.GenreEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${GenreContract.GenreEntry.COLUMN_NAME_NAME} TEXT," +
                    "${GenreContract.GenreEntry.COLUMN_NAME_IMGAGE} INTEGER," +
                    "${GenreContract.GenreEntry.COLUMN_URL_GENRE} TEXT)"
        private const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS ${GenreContract.GenreEntry.TABLE_NAME}"
    }
}
