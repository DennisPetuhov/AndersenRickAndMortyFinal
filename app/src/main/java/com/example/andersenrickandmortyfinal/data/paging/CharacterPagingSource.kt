package com.example.andersenrickandmortyfinal.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import com.example.andersenrickandmortyfinal.data.repository.Repository
import javax.inject.Inject

class CharacterPagingSource
 (val repository: Repository) :
    PagingSource<Int, CharacterRickAndMorty>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterRickAndMorty> {
        val currentLoadingPageKey = params.key ?: 1

        return try {
            var characters = PagedResponse<CharacterRickAndMorty>(null)
            repository.getPagesOfCharacters(currentLoadingPageKey).collect {
                characters = it
            }
            val data = characters.results
//            println("&&&&" + data)
            val result = mutableListOf<CharacterRickAndMorty>()
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

    override fun getRefreshKey(state: PagingState<Int, CharacterRickAndMorty>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
}