package com.example.andersenrickandmortyfinal.data.db.episodes

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.andersenrickandmortyfinal.data.model.episode.Episode

@Dao
interface EpisodesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(episodes: List<Episode>)

    @Query("SELECT * FROM EPISODE_TABLE")
    fun getAllEpisodes(): PagingSource<Int, Episode>

    @Query("DELETE FROM EPISODE_TABLE")
    fun deleteAll()

    @Query(
        "SELECT * FROM EPISODE_TABLE WHERE name LIKE :queryString  "
    )
    fun findEpisodeByName(queryString: String): PagingSource<Int, Episode>

    @Query(
        "SELECT * FROM EPISODE_TABLE WHERE episode  LIKE :queryString  "
    )
    fun findEpisodeByCode(queryString: String): PagingSource<Int, Episode>


    @Query("SELECT * FROM EPISODE_TABLE WHERE id IN (:episodeIds)  ")
   fun getCachedEpisodes(episodeIds:List<Int>): PagingSource<Int, Episode>

//    @Query("SELECT * FROM EPISODE_TABLE  WHERE (:limit > 0 AND :offset >= 0) OR id IN (:episodeIds)")
//    suspend fun getCachedEpisodesTWO (offset: Int, limit: Int, episodeIds: List<String>): List<Episode>
}