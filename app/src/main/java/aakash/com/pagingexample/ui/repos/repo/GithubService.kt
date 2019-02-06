/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package aakash.com.pagingexample.ui.repos.repo

import aakash.com.pagingexample.AppApplication
import aakash.com.pagingexample.common.extension.isInternetAvailable
import aakash.com.pagingexample.ui.repos.model.Repo
import aakash.com.pagingexample.shareddata.endpoints.ApiEndPoints
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

private const val TAG = "GithubService"
private const val IN_QUALIFIER = "in:name,description"

/**
 * Search repos based on a query.
 * Trigger a request to the Github searchRepo API with the following params:
 * @param query searchRepo keyword
 * @param page request page index
 * @param itemsPerPage number of repositories to be returned by the Github API per page
 *
 * The result of the request is handled by the implementation of the functions passed as params
 * @param onSuccess function that defines how to handle the list of repos received
 * @param onError function that defines how to handle request failure
 */
fun searchRepos(
    mApi: ApiEndPoints,
    query: String,
    page: Int,
    itemsPerPage: Int,
    mCompositeDisposable : CompositeDisposable,
    onSuccess: (repos: List<Repo>) -> Unit,
    onError: (error: String) -> Unit
) {
    Log.d(TAG, "query: $query, page: $page, itemsPerPage: $itemsPerPage")

    if (!AppApplication.getAppContext().isInternetAvailable()) {
        onError("Internet is not available")
    } else {
        val apiQuery = query + IN_QUALIFIER
        mApi.fetchRepoList(apiQuery, page, itemsPerPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                ///  TODO stop progress
            }
            .subscribeBy(
                onNext = { response ->
                    if (response.isSuccessful) {
                        val repos = response.body()?.items ?: emptyList()
                        onSuccess(repos)
                    } else {
                        onError(response.errorBody()?.string() ?: "Unknown error")
                    }
                },
                onError = {
                    onError(it.message ?: "Unknown error")
                }
            ).addTo(mCompositeDisposable)
    }
}