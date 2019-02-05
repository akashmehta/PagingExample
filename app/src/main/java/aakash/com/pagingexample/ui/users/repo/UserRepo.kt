package aakash.com.pagingexample.ui.users.repo

import aakash.com.pagingexample.shareddata.endpoints.ApiEndPoints
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import aakash.com.pagingexample.ui.users.model.Error
import aakash.com.pagingexample.ui.users.model.ResponseData
import aakash.com.pagingexample.ui.users.model.UserStates
import javax.inject.Inject

/**
 * Created by stllpt031 on 28/8/18.
 */
class UserRepo @Inject constructor(private val mApi: ApiEndPoints) {
    val mCompositeDisposable = CompositeDisposable()

    fun getUserData(internetConnectivity: Boolean): MutableLiveData<UserStates> {
        val data = MutableLiveData<UserStates>()
        if (!internetConnectivity) {
            data.value = UserStates(
                    error = Error("Internet is not connected", true)
            )
        } else {
            mApi.fetchUserList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { UserStates(progress = false) }
                    .subscribeBy(
                            onNext = { it ->
                                data.value = UserStates(
                                        progress = false,
                                        data = it.body()?.let { it1 ->
                                            val nameList = ArrayList<String>()
                                            it1.items?.map {
                                                it.name
                                            }?.forEach { it ->
                                                it?.let { nameList.add(it) }
                                            }
                                            ResponseData(nameList)
                                        }
                                )
                            },
                            onError = {
                                data.value = UserStates(
                                        progress = false,
                                        error = it.message?.let { it1 -> Error(msg = it1, show = true) }
                                )
                            }
                    ).addTo(mCompositeDisposable)
        }

        return data
    }
}