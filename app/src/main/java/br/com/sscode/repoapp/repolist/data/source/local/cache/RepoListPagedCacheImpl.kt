package br.com.sscode.repoapp.repolist.data.source.local.cache

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import br.com.sscode.cache.base.data.CacheDataKeys
import br.com.sscode.cache.feature.pocketball.PocketBallCache
import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.repoapp.repolist.data.source.local.RepoLocalDataSource
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class RepoListPagedCacheImpl @Inject constructor(
    dataStore: DataStore<Preferences>
) : PocketBallCache<PagingData<ItemDomain>>(
    preferencesDataStore = dataStore,
    preferencesBaseDataKey = CacheDataKeys.REPO_LIST_PAGED
), RepoLocalDataSource {

    override suspend fun putPage(pageNumber: Int, repoPage: PagingData<ItemDomain>) {
        fromSpecificDataKey(pageNumber.toString())
        put(repoPage)
    }

    override suspend fun getPage(pageNumber: Int): PagingData<ItemDomain>? {
        return object : TypeToken<PagingData<ItemDomain>>() {}.type.run {
            fromSpecificDataKey(pageNumber.toString())
            get(this)
        }
    }
}