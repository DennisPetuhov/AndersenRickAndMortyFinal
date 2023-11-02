package com.example.andersenrickandmortyfinal.data.db.location

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(episodes: List<LocationRick>)

    @Query("SELECT * FROM LOCATION_TABLE WHERE name LIKE :query OR dimension LIKE :query OR type LIKE :query")
    fun getAllLocations(query: String): PagingSource<Int, LocationRick>

    @Query("DELETE FROM LOCATION_TABLE")
    fun deleteAllLocations()


    @Query(
        "SELECT * FROM LOCATION_TABLE WHERE name LIKE :queryString  "
    )
    fun findLocationByName(queryString: String): PagingSource<Int, LocationRick>


    @Query(
        "SELECT * FROM LOCATION_TABLE WHERE dimension LIKE :queryString  "
    )
    fun findLocationByDimension(queryString: String): PagingSource<Int, LocationRick>


    @Query(
        "SELECT * FROM LOCATION_TABLE WHERE type LIKE :queryString  "
    )
    fun findLocationByType(queryString: String): PagingSource<Int, LocationRick>

    @Query("SELECT * FROM LOCATION_TABLE WHERE id LIKE :id ")
    fun findLocationById(id: Int): Flow<LocationRick>


}