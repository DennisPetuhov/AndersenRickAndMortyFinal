package com.example.andersenrickandmortyfinal.data.db.episodes

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(episodes: List<Episode>)

    @Query("SELECT * FROM EPISODE_TABLE WHERE episode LIKE :queryString OR name LIKE :queryString")
    fun getAllEpisodes(queryString: String): PagingSource<Int, Episode>

    @Query("DELETE FROM EPISODE_TABLE")
    fun deleteAll()

    @Query(
        "SELECT * FROM EPISODE_TABLE WHERE name LIKE :queryString  "
    )
    fun findEpisodeByName(queryString: String): PagingSource<Int, Episode>




    @Query("SELECT * FROM EPISODE_TABLE WHERE id IN (:episodeIds)  ")
    fun getCachedEpisodes(episodeIds: List<Int>): PagingSource<Int, Episode>

    @Query("SELECT * FROM EPISODE_TABLE WHERE id LIKE :id ")
    fun getSingleEpisodeById(id: Int): Flow<Episode>

    @Query("SELECT * FROM EPISODE_TABLE WHERE episode LIKE :queryString  ")
    fun findEpisodeByEpisode(queryString: String): PagingSource<Int, Episode>


}