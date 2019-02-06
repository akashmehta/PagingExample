package aakash.com.pagingexample.ui.repos.view

import aakash.com.pagingexample.shareddata.model.RepoSearchResult
import aakash.com.pagingexample.ui.repos.model.Repo
import aakash.com.pagingexample.ui.repos.repo.UserRepo
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import javax.inject.Inject

/**
 * Created by stllpt031 on 28/8/18.
 */
class UserViewModel @Inject constructor(private val mUserRepo: UserRepo) : ViewModel() {
    fun initCache(context: Context) {
        mUserRepo.initCache(context)
    }

    private val queryLiveData = MutableLiveData<String>()
    private val repoResult: LiveData<RepoSearchResult> = Transformations.map(queryLiveData) {
        mUserRepo.search(it)
    }

    val repos: LiveData<PagedList<Repo>> = Transformations.switchMap(repoResult) { it -> it.data }
    val networkErrors: LiveData<String> = Transformations.switchMap(repoResult) { it ->
        it.networkErrors
    }

    /**
     * Search a repository based on a query string.
     */
    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    /**
     * Get the last query value.
     */
    fun lastQueryValue(): String? = queryLiveData.value

    override fun onCleared() {
        super.onCleared()
        mUserRepo.mCompositeDisposable.clear()
        mUserRepo.mCompositeDisposable.dispose()
    }
}