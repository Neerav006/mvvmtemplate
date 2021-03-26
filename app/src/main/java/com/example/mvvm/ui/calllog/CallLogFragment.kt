package com.example.mvvm.ui.calllog

import android.Manifest
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.mvvm.R
import com.example.mvvm.databinding.CallLogFragmentBinding
import com.example.mvvm.model.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CallLogFragment : Fragment() {

    val PERMISSION_REQUEST_CODE = 202

    companion object {
        fun newInstance() = CallLogFragment()
    }

    private val viewModel: CallLogViewModel by viewModels()
    private lateinit var binding: CallLogFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CallLogFragmentBinding.inflate(inflater,container,false)
        observeData()
        return binding.root
    }

    private fun observeData() {
        viewModel.callLogMap.observe(viewLifecycleOwner, Observer { it ->
            when(it) {
                is State.Loading -> {
                   binding.progress.visibility = View.VISIBLE
                }
                is State.Success->{
                    it.data["name"]?.let { callerList ->
                        callerList.forEach { name ->
                            Log.e("caller_name",name?:"")
                        }
                    }
                    binding.progress.visibility = View.GONE
                }
                is State.Error ->{
                    binding.progress.visibility = View.GONE
                }

            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCallLog()
    }

    private fun getCallLog() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CALL_LOG
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getCallLogs(requireContext())

                }
            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_CALL_LOG) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.

            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_CALL_LOG
                )
            }
        }

    }

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getCallLog()
                // Permission is granted. Continue the action or workflow in your
                // app.
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }
}