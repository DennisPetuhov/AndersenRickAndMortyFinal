package com.example.andersenrickandmortyfinal.presenter.ui.characters.recycler


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.andersenrickandmortyfinal.data.model.character.ResultRickAndMorty
import com.example.andersenrickandmortyfinal.data.repository.Repository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharactersMediator @Inject constructor(
    private val repository: Repository,
//    private val database: MovieDatabase
) : RemoteMediator<Int, ResultRickAndMorty>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ResultRickAndMorty>
    ): MediatorResult {
        try {
            val position = when (loadType) {
                LoadType.REFRESH -> {
                    // Очистите базу данных и начните с первой страницы при обновлении
//                    database.movieDao().clearData()
                    1
                }

                LoadType.PREPEND -> {
                    // Вставьте данные в верхнюю часть списка (например, для свайпа к верху)
                    // В этом примере не реализовано
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    // Определите номер страницы для загрузки в зависимости от текущего состояния
                    val page = state.pages.lastOrNull()?.data?.lastOrNull()
//                    val nextPage = page?.let {
//                        it.info?.next
//                    }
//                    val uri = Uri.parse(nextPage)
//                    val nextPageQuery = uri.getQueryParameter("page")
//                   val nextPageNumber = nextPageQuery?.toInt()

                    //     val nextPage = page?.let { it.page + 1 } ?: 1

//                    var characters = CharacterRickAndMorty(null)
//                    repository.getPagesOfCharacters(nextPageNumber!!).collect {
//                        characters = it
//                    }
//                    val data = characters.results
//                    val result = mutableListOf<ResultRickAndMorty>()
//                    result.addAll(data)

                    // Сохраните данные в локальной базе данных
//                    response.body()?.results?.let { movies ->
//                        database.movieDao().insertAll(movies)
                }

//                    nextPage
            }


        return MediatorResult.Success(endOfPaginationReached = false)
    } catch (exception: IOException)
    {
        return MediatorResult.Error(exception)
    } catch (exception: HttpException)
    {
        return MediatorResult.Error(exception)
    }
}
}