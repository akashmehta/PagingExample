package aakash.com.pagingexample.ui.repos.model

import aakash.com.pagingexample.ui.repos.repo.UserRepo
import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class UserViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private lateinit var userViewModel: UserViewModel
    @Mock
    private lateinit var queryLiveData: MutableLiveData<String>
    @Mock
    private lateinit var userRepo: UserRepo
    @Captor
    private lateinit var queryCaptor: ArgumentCaptor<String>
    @Before
    fun initialize() {
        MockitoAnnotations.initMocks(this)
        userViewModel = UserViewModel(userRepo)
    }

    @Test
    fun showSearchRepoTest() {
        userViewModel.searchRepo("")
        Mockito.verify(queryLiveData).postValue(queryCaptor.capture())
    }
}