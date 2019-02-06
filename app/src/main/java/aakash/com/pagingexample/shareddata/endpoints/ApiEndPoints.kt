package aakash.com.pagingexample.shareddata.endpoints

import aakash.com.pagingexample.ui.repos.model.RepoSearchResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by stllpt031 on 14/8/18.
 */
interface ApiEndPoints {
    @GET("search/repositories?sort=stars")
    fun fetchRepoList(@Query("q") user: String = "addClass+user:mozilla",
                      @Query("per_page") perPage: Int = 10,
                      @Query("page") page: Int = 1): Observable<Response<RepoSearchResponse>>
}