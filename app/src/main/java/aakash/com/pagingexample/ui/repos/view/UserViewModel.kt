package aakash.com.pagingexample.ui.repos.view

import aakash.com.pagingexample.shareddata.model.RepoSearchResult
import aakash.com.pagingexample.ui.repos.repo.UserRepo
import android.content.Context
import androidx.lifecycle.ViewModel
import javax.inject.Inject

/**
 * Created by stllpt031 on 28/8/18.
 */
class UserViewModel @Inject constructor(private val mUserRepo: UserRepo) : ViewModel() {
    private var repoSearchResult = RepoSearchResult()
    fun getRepoSearchResult() = repoSearchResult

    fun getData(query: String) {
        // TODO change to search(query)
//        userModel = mUserRepo.getUserData(internetConnectivity)
        repoSearchResult = mUserRepo.search(query)
    }

    override fun onCleared() {
        super.onCleared()
        mUserRepo.mCompositeDisposable.clear()
        mUserRepo.mCompositeDisposable.dispose()
    }

    fun initCache(context: Context) {
        mUserRepo.initCache(context)
    }
}