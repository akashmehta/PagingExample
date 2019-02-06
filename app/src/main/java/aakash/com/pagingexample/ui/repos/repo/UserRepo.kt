package aakash.com.pagingexample.ui.repos.repo

import aakash.com.pagingexample.data.RepoBoundaryCallback
import aakash.com.pagingexample.db.GithubLocalCache
import aakash.com.pagingexample.db.RepoDatabase
import aakash.com.pagingexample.shareddata.model.RepoSearchResult
import aakash.com.pagingexample.shareddata.endpoints.ApiEndPoints
import android.content.Context
import android.util.Log
import androidx.paging.LivePagedListBuilder
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by stllpt031 on 28/8/18.
 */
class UserRepo @Inject constructor(private val mApi: ApiEndPoints) {
    val mCompositeDisposable = CompositeDisposable()
    private lateinit var cache: GithubLocalCache

    fun initCache(context: Context) {
        cache = provideCache(context)
    }

    /**
     * Creates an instance of [GithubLocalCache] based on the database DAO.
     */
    private fun provideCache(context: Context): GithubLocalCache {
        val database = RepoDatabase.getInstance(context)
        return GithubLocalCache(database.reposDao())
    }

    /**
     * Search repositories whose names match the query.
     */
    fun search(query: String) : RepoSearchResult {
        Log.d("GithubRepository", "New query: $query")
        val dataSourceFactory = cache.reposByName(query)
        val boundaryCallback = RepoBoundaryCallback(query, mApi, cache, mCompositeDisposable)
        val networkErrors = boundaryCallback.networkErrors

        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        // Get the network errors exposed by the boundary callback
        return RepoSearchResult(data, networkErrors)
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 10
    }
}