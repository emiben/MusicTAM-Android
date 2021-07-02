package com.theagilemonkeys.musictam.viewmodels

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theagilemonkeys.musictam.utils.getRandomLetter
import java.util.*

class SearchFragmentViewModel() : ViewModel() {

    val searchQueryLiveData = MutableLiveData("")
    val recommendedLiveData = MutableLiveData(true)
    private var timer = Timer()

    fun afterTextChanged(etext: Editable?) {
        var text = etext.toString()

        if(text.isEmpty()){
            text = getRandomLetter()
            recommendedLiveData.postValue(true)
        } else {
            recommendedLiveData.postValue(false)
        }

        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                searchQueryLiveData.postValue(text)
            }
        }, 350)
    }

    fun onTextChanged() {
        if(timer != null){
            timer.cancel()
        }
    }
}