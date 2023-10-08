package com.example.andersenrickandmortyfinal.data.db.characters

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty


@Dao
interface CharacterDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(episodes: List<CharacterRickAndMorty>)

    @Query("SELECT * FROM CHARACTER_TABLE")
    fun pagingSource(): PagingSource<Int, CharacterRickAndMorty>

    @Query("DELETE FROM CHARACTER_TABLE")
    fun deleteAllCharacters()

    @Query(
        "SELECT * FROM CHARACTER_TABLE WHERE name LIKE :queryString  "
    )
    fun findCharacterByName(queryString: String): PagingSource<Int, CharacterRickAndMorty>

    @Query(
        "SELECT * FROM CHARACTER_TABLE WHERE species LIKE :queryString  "
    )
    fun findCharacterBySpecies(queryString: String): PagingSource<Int, CharacterRickAndMorty>

    @Query(
        "SELECT * FROM CHARACTER_TABLE WHERE type LIKE :queryString" +
                " AND gender LIKE :gender AND status LIKE :status "
    )
    fun findCharacterByType(
        queryString: String, gender: String,
        status: String
    ): PagingSource<Int, CharacterRickAndMorty>

    @Query(
        "SELECT * FROM CHARACTER_TABLE WHERE" +
                " name LIKE :queryString OR species LIKE :queryString Or type LIKE :queryString  "
    )
    fun findALLCharacters(queryString: String): PagingSource<Int, CharacterRickAndMorty>


}


