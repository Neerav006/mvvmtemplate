package com.example.mvvm.ui

import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.data.repository.PostRepository
import com.example.mvvm.data.session.SessionManager
import com.example.mvvm.model.Post
import com.example.mvvm.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
        private val sessionManager: SessionManager,
        private val repository: PostRepository
): ViewModel() {

    private val _counter = MutableLiveData<Int>().apply { value = 0 }
    val counter :LiveData<Int> get() = _counter

    private val _postsLiveData = MutableLiveData<State<List<Post>>>()

    val postsLiveData: LiveData<State<List<Post>>> = _postsLiveData

    fun showToast(){
        sessionManager.saveToken("Hello world${_counter.value}")
        Log.e("TAG","toast show")
        Handler().postDelayed({
           if (_counter.value!=null){
               _counter.value = _counter.value!! + 1
           }

        },2000)


    }

    fun getPosts() {
        viewModelScope.launch {

                repository.getAllPosts()
                    .flowOn(Dispatchers.IO)
                    .onStart { _postsLiveData.value = State.loading() }
                    .map { resource -> State.fromResource(resource) }
                    .collect { state -> _postsLiveData.value = state }


        }
    }

}