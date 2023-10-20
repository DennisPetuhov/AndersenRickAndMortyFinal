package com.example.andersenrickandmortyfinal.data.db

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.data.model.episode.EpisodesRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.location.LocationRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseHelperImpl @Inject constructor(private val db: MainDatabase) :
    DatabaseHelper {
    override suspend fun insertAllCharacters(list: List<Character>) {
        return db.withTransaction {
            db.characterDao().insertAll(list)
        }

    }

    override suspend fun insertAllCharactersKeys(list: List<CharacterRemoteKeys>) {
        return db.characterKeyDao().insertAllCharacterKeys(list)
    }

    override suspend fun deleteAllCharacters() {
        return db.withTransaction {
            db.characterDao().deleteAllCharacters()
        }
    }

    override suspend fun deleteAllCharactersKeys() {
        return db.withTransaction {
            db.characterKeyDao().deleteAllCharactersKey()
        }

    }

    override fun getNextPageKey(id: Int): Flow<CharacterRemoteKeys?> {
        return db.characterKeyDao().getNextPageKey(id)
    }

    override suspend fun getNextPageKeySimple(id: Int): CharacterRemoteKeys? {
        return db.characterKeyDao().getNextPageKeySimple(id)
    }

    override fun pagingSource(): PagingSource<Int, Character> {
        return db.characterDao().pagingSource()
    }

    override fun findCharacterByName(
        queryString: String, gender: String,
        status: String
    ): PagingSource<Int, Character> {
        return db.characterDao().findCharacterByName(queryString)
    }

    override fun findCharacterBySpecies(
        queryString: String, gender: String,
        status: String
    ): PagingSource<Int, Character> {
        return db.characterDao().findCharacterBySpecies(queryString)
    }

    override fun findCharacterByType(
        queryString: String, gender: String,
        status: String
    ): PagingSource<Int, Character> {
        return db.characterDao().findCharacterByType(queryString, gender, status)
    }

    override fun findAllCharacters(
        queryString: String, gender: String,
        status: String
    ): PagingSource<Int, Character> {
        return db.characterDao().findALLCharacters(queryString)
    }

    override suspend fun insertAllEpisodes(list: List<Episode>) {
        return db.withTransaction {
            db.episodeDao().insertAll(list)
        }
    }

    override suspend fun insertAllEpisodesKeys(list: List<EpisodesRemoteKeys>) {
        return db.withTransaction {
            db.episodeKeyDao().insertAllKeys(list)
        }
    }

    override suspend fun deleteAllEpisodes() {
        db.withTransaction { db.episodeDao().deleteAll() }
    }

    override suspend fun deleteAllEpisodesKeys() {
        db.withTransaction { db.episodeKeyDao().deleteAllKey() }
    }

    override fun getAllEpisodes(): PagingSource<Int, Episode> {
        return db.episodeDao().getAllEpisodes()
    }

    override fun findEpisodeByName(queryString: String): PagingSource<Int, Episode> {
       return db.episodeDao().findEpisodeByName(queryString)
    }

    override fun findEpisodeByCode(queryString: String): PagingSource<Int, Episode> {
       return db.episodeDao().findEpisodeByCode(queryString)
    }

    override  fun getCachedEpisodes(episodeIds:List<Int>): PagingSource<Int, Episode> {
      return  db.episodeDao().getCachedEpisodes(episodeIds)
    }

    override fun getCachedCharters(charactersIds: List<Int>): PagingSource<Int, Character> {
        return  db.characterDao().findCharactersById(charactersIds)
    }


    override suspend fun insertAllLocations(list: List<LocationRick>) {
        db.locationDao().insertAll(list)
    }

    override suspend fun insertAllLocationsKeys(list: List<LocationRemoteKeys>) {
        db.locationKeyDao().insertAllKeys(list)
    }

    override suspend fun deleteAllLocations() {
        db.locationDao().deleteAllLocations()
    }

    override suspend fun deleteAllLocationsKeys() {
        db.locationKeyDao().deleteAllKey()
    }

    override fun getAllLocations(): PagingSource<Int, LocationRick> {
    return  db.locationDao().getAllLocations()
    }

    override fun findLocationByName(queryString: String): PagingSource<Int, LocationRick> {
     return   db.locationDao().findLocationByName(queryString)
    }

    override fun findLocationByDimension(queryString: String): PagingSource<Int, LocationRick> {
       return db.locationDao().findLocationByDimension(queryString)
    }

    override fun findLocationByType(queryString: String): PagingSource<Int, LocationRick> {
       return db.locationDao().findLocationByType(queryString)
    }

    override fun getEpisodeById(id: Int): Flow<Episode> {
     return   db.episodeDao().getSingleEpisodeById(id)
    }

//    override suspend fun getCachedEpisodes(offset: Int, limit: Int, episodes:List<Int>): PagingSource<Int,Episode> {
//        return  db.episodeDao().getCachedEpisodes(offset,limit,episodes)
//    }


}