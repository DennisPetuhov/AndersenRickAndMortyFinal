//package com.example.andersenrickandmortyfinal.data.paging
//
//class `try` {
//}
//
//@OptIn(ExperimentalPagingApi::class)
//class LocalMediator(
//    private val database: YourRoomDatabase,
//    private val networkApi: YourNetworkApi
//) : RemoteMediator<Int, YourEntity>() {
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, YourEntity>
//    ): MediatorResult {
//        try {
//            val pageNumber = when (loadType) {
//                LoadType.REFRESH -> 1 // Начинаем с первой страницы при обновлении
//                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
//                LoadType.APPEND -> {
//                    // Определите номер страницы для загрузки в зависимости от текущего состояния
//                    val page = state.pages.lastOrNull()
//                    page?.data?.lastOrNull()?.pageNumber?.plus(1) ?: 1
//                }
//            }
//
//            // Загружаем данные из БД
//            val dataFromDb = database.yourEntityDao().loadDataForPage(pageNumber, state.config.pageSize)
//
//            if (dataFromDb.isNotEmpty()) {
//                // Если данные доступны в БД, возвращаем их
//                return MediatorResult.Success(endOfPaginationReached = false)
//            } else {
//                // Если данные отсутствуют в БД, загружаем их из сети
//                val networkData = networkApi.loadDataFromNetwork(pageNumber)
//
//                // Сохраняем данные в БД
//                database.yourEntityDao().insertAll(networkData)
//
//                // Возвращаем данные из сети
//                return MediatorResult.Success(endOfPaginationReached = networkData.isEmpty())
//            }
//        } catch (exception: Exception) {
//            return MediatorResult.Error(exception)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, YourEntity>): Int? {
//        // Этот метод возвращает ключ для обновления данных (например, после обновления)
//        // Возвращаем null, чтобы использовать загрузку с ключом null
//        return null
//    }
//}