package com.example.mvvm.ui.calllog

import android.content.Context
import android.database.Cursor
import android.provider.CallLog.Calls.*
import androidx.core.database.getStringOrNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.model.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CallLogViewModel : ViewModel() {

    private var _callLogMap = MutableLiveData<State<Map<String, Array<String?>>>>()
    val callLogMap = _callLogMap

  //  https://stackoverflow.com/questions/51154786/android-implement-search-with-view-model-and-live-data/56394003#56394003
    // https://stackoverflow.com/questions/6786666/how-do-i-access-call-log-for-android
    suspend fun getCallLogs(context: Context) {
        viewModelScope.launch {
            _callLogMap.value = State.loading()
            withContext(Dispatchers.IO) {
                try {

                    val c = context.applicationContext
                    val projection = arrayOf(CACHED_NAME, NUMBER, TYPE, DATE, DURATION)

                    val cursor = c.contentResolver.query(
                        CONTENT_URI,
                        projection,
                        null,
                        null,
                        null,
                        null
                    )
                    viewModelScope.launch {
                        _callLogMap.value = State.success(cursorToMap(cursor))

                    }

                } catch (e: Exception) {
                    viewModelScope.launch {
                        _callLogMap.value = State.error(e.message.orEmpty())

                    }
                }
            }

        }


    }

    private fun cursorToMap(cursor: Cursor?): Map<String, Array<String?>> {
        val arraySize = cursor?.count ?: 0
        val map = mapOf(
            CACHED_NAME to Array<String?>(arraySize) { "" },
            NUMBER to Array<String?>(arraySize) { "" },
            TYPE to Array<String?>(arraySize) { "" },
            DATE to Array<String?>(arraySize) { "" },
            DURATION to Array<String?>(arraySize) { "" }
        )

        cursor?.use {
            for (i in 0 until arraySize) {
                it.moveToNext()

                map[CACHED_NAME]?.set(i, it.getStringOrNull(it.getColumnIndex(CACHED_NAME)))
                map[NUMBER]?.set(i, it.getStringOrNull(it.getColumnIndex(NUMBER)))
                map[TYPE]?.set(i, it.getStringOrNull(it.getColumnIndex(TYPE)))
                map[DATE]?.set(i, it.getStringOrNull(it.getColumnIndex(DATE)))
                map[DURATION]?.set(i, it.getStringOrNull(it.getColumnIndex(DURATION)))
            }
        }

        return map
    }
}