package com.example.mvvm.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.mvvm.R
import com.example.mvvm.data.session.SessionManager
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.model.State
import com.example.mvvm.ui.base.BaseActivity
import com.example.mvvm.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity: BaseActivity<MainViewModel, ActivityMainBinding>() {

    @Inject
    lateinit var sessionManager: SessionManager

    override val mViewModel: MainViewModel by viewModels()

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        // test session manager token
        sessionManager.saveToken("test auth")

       // observePosts()

        val fadeOut = ObjectAnimator.ofFloat(mViewBinding.btnShow, "alpha", .6f, .4f)
        fadeOut.duration = 1000
        val fadeIn = ObjectAnimator.ofFloat(mViewBinding.btnShow, "alpha", .4f, .6f)
        fadeIn.duration = 1000

        val mAnimationSet = AnimatorSet()

        mAnimationSet.play(fadeIn).after(fadeOut)

        mAnimationSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                mAnimationSet.start()
            }
        })

        mAnimationSet.start()

        mViewBinding.btnShow.setOnClickListener {
            mViewModel.getPosts()
        }

        mViewModel.counter.observe(this, Observer {
               Log.e("observe data",it.toString())
               Toast.makeText(this,sessionManager.getToken()?:"blank",Toast.LENGTH_SHORT).show()
        })
    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
    private fun observePosts() {

        mViewModel.postsLiveData.observe(this, Observer {
            when (it) {
                is State.Loading -> {

                }
                is State.Success -> {

                    if (it.data.isNotEmpty()) {
                       showToast("Total record: "+it.data.size.toString())
                       // mAdapter.submitList(state.data.toMutableList())
                       // showLoading(false)
                    }
                }
                is State.Error -> {
                    showToast(it.message)
                    //showLoading(false)
                }
            }
        })


    }




    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}