package com.example.rickandmortyapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rickandmortyapp.data.local.database.model.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false)
abstract class CharactersDatabase: RoomDatabase() {
    abstract fun charactersDao(): CharactersDao

    companion object {
        @Volatile
        private var INSTANCE: CharactersDatabase? = null

        fun getDatabase(context: Context): CharactersDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharactersDatabase::class.java,
                    "characters_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}