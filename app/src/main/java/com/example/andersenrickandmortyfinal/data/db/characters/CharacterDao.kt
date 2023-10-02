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
        "SELECT * FROM CHARACTER_TABLE WHERE " +
                "name LIKE :queryString  " +
                "ORDER BY  name ASC"
    )
    fun findCharacterByName(queryString: String):PagingSource<Int, CharacterRickAndMorty>




}
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertPersonFlow(personEntity: PersonEntity)
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertPerson(personEntity: PersonEntity)
//
//    @Update
//    fun updatePerson(personEntity: PersonEntity)
//
//    @Delete
//    fun deletePerson(personEntity: PersonEntity)
//
//    @Query("SELECT * FROM $PERSON_TABLE ORDER BY personId DESC")
//    fun getAllPersons() : MutableList<PersonEntity>
//
//    @Query("SELECT * FROM $PERSON_TABLE WHERE personId  LIKE :id")
//    fun getPerson(id : Int) : PersonEntity

