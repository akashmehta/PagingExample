package aakash.com.pagingexample.ui.repos.view

import aakash.com.pagingexample.AppApplication
import aakash.com.pagingexample.R
import aakash.com.pagingexample.common.extension.gone
import aakash.com.pagingexample.common.extension.visible
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as AppApplication).mComponent.inject(this)
        mViewModel = ViewModelProviders.of(this, viewModelFactory)[UserViewModel::class.java]
        mViewModel.initCache(this)
        mViewModel.getData("")
        mViewModel.getRepoSearchResult().data.observe(this, Observer {it->
            lvContent.visible()
          // TODO setup data
//            stringItemList.clear()
//            stringItemList.addAll(it.data)
//            lvContent.adapter = ArrayAdapter<String>(
//                this,
//                android.R.layout.simple_expandable_list_item_1,
//                stringItemList
//            )
        })
        mViewModel.getRepoSearchResult().networkErrors.observe(this, Observer {
            lvContent.gone()
            tvError.visible()
            tvError.text = it
        })
    }
}
