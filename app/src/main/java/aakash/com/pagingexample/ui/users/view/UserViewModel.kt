package aakash.com.pagingexample.ui.users.view

import android.util.Log
import aakash.com.pagingexample.ui.users.model.UserStates
import aakash.com.pagingexample.ui.users.repo.UserRepo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

/**
 * Created by stllpt031 on 28/8/18.
 */
class UserViewModel  @Inject constructor(private val mUserRepo: UserRepo) : ViewModel() {
    private var userModel = MutableLiveData<UserStates>()
    fun getUserModel() = userModel

    fun getData(internetConnectivity: Boolean) {
        userModel.value?.let {
            return
        }
        userModel = mUserRepo.getUserData(internetConnectivity)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("DISPOSE", "----- disposed -------")
        mUserRepo.mCompositeDisposable.clear()
        mUserRepo.mCompositeDisposable.dispose()
    }

}