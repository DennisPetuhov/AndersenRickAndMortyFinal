//package com.example.andersenrickandmortyfinal.data.paging
//
//import androidx.paging.PagingSource
//import com.example.andersenrickandmortyfinal.data.api.episode.EpisodeApiHelper
//import com.example.andersenrickandmortyfinal.data.db.MainDatabase
//import com.example.andersenrickandmortyfinal.data.model.episode.Episode
//
//class MyPagingSource(
//    private val episodes: MutableList<Episode>,
//    private val database: MainDatabase,
//    private val api: EpisodeApiHelper
//) : PagingSource<Int, Episode>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
//        try {
//            val currentPage = params.key ?: 0
//            val pageSize = params.loadSize
//
////            database.episodeDao().insertAll(episodes)
//            val cachedData =
//                database.episodeDao().getCachedEpisodes(currentPage * pageSize, pageSize,)
////            getCachedEpisodes
////            var listOfEpispdes = listOf<Episode>()
////            api.getListOfEpisodesByCharacter(episodes).collect {
////                listOfEpispdes = it
////
////            }
//
////            if (cachedData.isNotEmpty()) {
////                return LoadResult.Page(
////                    data = cachedData,
////                    prevKey = null,
////                    nextKey = if (currentPage < episodes.size / pageSize) currentPage + 1 else null
////                )
////            }
//
//
//
//
//            return LoadResult.Page(
//                data = cachedData,
//                prevKey = null,
//                nextKey = if (currentPage < episodes.size / pageSize) currentPage + 1 else null
//            )
//        } catch (e: Exception) {
//            return LoadResult.Error(e)
//        }
//    }
//}

//@Query("SELECT * FROM cached_episodes LIMIT :limit OFFSET :offset")
//suspend fun getCachedEpisodes(offset: Int, limit: Int): List<Episode>



