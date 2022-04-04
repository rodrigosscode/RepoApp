package br.com.sscode.repoapp.repolist.data.source.local.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import br.com.sscode.cache.core.pocketball.PocketBall
import br.com.sscode.repoapp.repolist.data.source.local.RepoLocalDataSource
import br.com.sscode.repoapp.repolist.domain.entity.RepoDomain

const val PREFERENCES_KEY = "REPO_LIST_PAGED_CACHE"
private val Context.dataStoreRepoListPaged by preferencesDataStore("dataStoreRepoListPaged")

class RepoListPagedCache(
    private val context: Context,
    override var preferencesKey: String = PREFERENCES_KEY,
    dataStoreCache: DataStore<Preferences> = context.dataStoreRepoListPaged
) : PocketBall<RepoDomain>(
    preferencesDataStore = dataStoreCache
), RepoLocalDataSource {

    override suspend fun saveRepo(repoDomain: RepoDomain) {
        super.save(repoDomain)
    }
}