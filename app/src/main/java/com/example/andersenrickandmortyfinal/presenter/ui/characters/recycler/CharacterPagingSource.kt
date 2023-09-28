package com.example.andersenrickandmortyfinal.presenter.ui.characters.recycler

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import com.example.andersenrickandmortyfinal.data.model.character.ResultRickAndMorty
import com.example.andersenrickandmortyfinal.data.repository.Repository
import javax.inject.Inject

class CharacterPagingSource
@Inject constructor(val repository: Repository) :
    PagingSource<Int, ResultRickAndMorty>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultRickAndMorty> {
        val currentLoadingPageKey = params.key ?: 1

        return try {
            var characters = PagedResponse<ResultRickAndMorty>(null)
            repository.getPagesOfCharacters(currentLoadingPageKey).collect {
                characters = it
            }
            val data = characters.results
            println("&&&&"+ data)
            val result = mutableListOf<ResultRickAndMorty>()
            result.addAll(data)
            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = result,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultRickAndMorty>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
}