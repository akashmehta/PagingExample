package aakash.com.pagingexample

import aakash.com.pagingexample.common.extension.gone
import aakash.com.pagingexample.common.extension.isInternetAvailable
import aakash.com.pagingexample.common.extension.isVisible
import aakash.com.pagingexample.ui.users.view.UserViewModel
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private val stringItemList = ArrayList<String>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as AppApplication).mComponent.inject(this)
        mViewModel = ViewModelProviders.of(this, viewModelFactory)[UserViewModel::class.java]

        mViewModel.getData(isInternetAvailable())
        mViewModel.getUserModel().observe(this, Observer { it ->
            it?.let { userStates ->
                pbContent.visibility = userStates.progress.isVisible()
                userStates.data?.let {
                    //                    lvContent.visible()
                    stringItemList.clear()
                    stringItemList.addAll(it.data)
                    lvContent.adapter = ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_expandable_list_item_1,
                        stringItemList
                    )

                } ?: lvContent.gone()
                userStates.error?.let { it ->
                    tvError.visibility = it.show.isVisible()
                    tvError.text = it.msg
                }
            }
        })
    }
}
