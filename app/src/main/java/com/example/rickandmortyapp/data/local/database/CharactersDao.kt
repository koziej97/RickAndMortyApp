package com.example.rickandmortyapp.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.rickandmortyapp.data.local.database.model.CharacterEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CharactersDao {

    @Insert
    suspend fun insert(entity: CharacterEntity)

    @Update
    suspend fun update(character: CharacterEntity)

    @Delete
    suspend fun delete(entity: CharacterEntity)

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacter(id: Int): CharacterEntity?
}