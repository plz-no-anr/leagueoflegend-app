package com.psg.leagueoflegend_app.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.psg.leagueoflegend_app.utils.AppLogger
import com.psg.leagueoflegend_app.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


abstract class BaseActivity<T: ViewDataBinding, V: BaseViewModel>(@LayoutRes val res: Int): AppCompatActivity() {
    lateinit var binding: T
    abstract val TAG: String
    abstract val viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppLogger.i(TAG,"onCreate")
//        Log.i(TAG,"onCreate")
        binding = DataBindingUtil.setContentView(this, res)
        binding.lifecycleOwner = this
        setEventFlow()
    }

    open fun setObserve() {

    }

    open fun initView(){
    }

    open fun setRv(){

    }

    open fun setEventFlow(){
        CoroutineScope(Dispatchers.IO).launch{
            viewModel.eventFlow.collect { event -> handleEvent(event) }
        }
    }

    open fun handleEvent(event: BaseViewModel.Event) = when (event){
        is BaseViewModel.Event.ShowToast ->
            CoroutineScope(Dispatchers.Main).launch {
                makeToast(event.text)
            }
    }

    open fun setToolbar(toolbar: Toolbar){
        toolbar.title = "전적 검색"
        setSupportActionBar(toolbar)
    }

    open fun makeToast(msg:String) = Toast.makeText(this ,msg, Toast.LENGTH_SHORT).show()


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                true
            }
            R.id.option -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        AppLogger.i(TAG,"onStart")
    }

    override fun onResume() {
        super.onResume()
        AppLogger.i(TAG,"onResume")
    }

    override fun onPause() {
        super.onPause()
        AppLogger.i(TAG,"onPause")
    }

    override fun onStop() {
        super.onStop()
        AppLogger.i(TAG,"onStop")
//        Log.i(TAG,"onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        AppLogger.i(TAG,"onDestroy")
    }

}