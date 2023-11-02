package com.example.andersenrickandmortyfinal.data.network.api.episode

import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.data.model.episode.EpisodePojo
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import com.example.andersenrickandmortyfinal.data.model.main.TypeOfRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EpisodeApiHelperImpl @Inject constructor(val service: EpisodeService) : EpisodeApiHelper {


    override fun getListOfEpisodesByCharacter(list: List<Int>): Flow<List<EpisodePojo>> {
        return flow {
            val response = service.getListOfEpisodesByCharacter(list as MutableList<Int>)
            emit(response)
        }.flowOn(Dispatchers.IO)

    }

    override fun getAllEpisodesByNameAndEpisode(
        type: TypeOfRequest,
        page: Int,
        query: String,

        ): Flow<PagedResponse<EpisodePojo>> {
        return flow {
            val response = when (type) {
                is TypeOfRequest.Name -> {
                    service.getAllEpisodesByName(page, query)
                }

                is TypeOfRequest.Code -> {
                    service.getAllEpisodesByCode(page, query)
                }

                is TypeOfRequest.None -> {
                    service.getAllEpisodes(page)


                }

                else -> {
                    service.getAllEpisodes(page)
                }
            }

            emit(response)
        }.flowOn(Dispatchers.IO)


    }

    override fun getSingleEpisodesById(id: Int): Flow<EpisodePojo> {
        return flow {
            val response = service.getSingleEpisodesById(id)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

}