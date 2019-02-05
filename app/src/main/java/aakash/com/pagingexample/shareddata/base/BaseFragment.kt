package aakash.com.pagingexample.shareddata.base

import aakash.com.pagingexample.common.extension.isInternetAvailable
import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Created by stllpt031 on 14/8/18.
 */
open class BaseFragment : Fragment(){
    var internetConnectivity : Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            internetConnectivity = it.isInternetAvailable()
        }
    }
}