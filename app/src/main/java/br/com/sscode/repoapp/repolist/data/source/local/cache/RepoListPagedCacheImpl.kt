package br.com.sscode.repoapp.repolist.data.source.local.cache

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import br.com.sscode.cache.base.data.CacheDataKeys
import br.com.sscode.cache.feature.pocketball.PocketBallCache
import br.com.sscode.repoapp.repolist.data.entity.RepoResponse
import br.com.sscode.repoapp.repolist.data.source.local.RepoLocalDataSource
import javax.inject.Inject

class RepoListPagedCacheImpl @Inject constructor(
    dataStore: DataStore<Preferences>
) : PocketBallCache<RepoResponse>(
    preferencesDataStore = dataStore,
    preferencesBaseDataKey = CacheDataKeys.REPO_LIST_PAGED
), RepoLocalDataSource {

    override suspend fun putPage(pageNumber: Int, repoPage: RepoResponse) {
        fromSpecificDataKey(pageNumber.toString())
        put(repoPage)
    }

    override suspend fun getPage(pageNumber: Int): RepoResponse? {
        fromSpecificDataKey(pageNumber.toString())
        return get(RepoResponse::class.java)
    }
}