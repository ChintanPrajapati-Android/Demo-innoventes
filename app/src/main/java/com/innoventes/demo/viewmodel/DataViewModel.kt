package com.innoventes.demo.viewmodel

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoventes.demo.base.BaseViewModel
import com.innoventes.demo.data.roomdatabase.userdata.UserDataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class DataViewModel : BaseViewModel() {

    var pattern: Pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}")
    var mInsertData = MutableLiveData<Boolean>()

    fun verifyPan(panNumber: String?): Boolean {
        panNumber?.let {
            val matcher: Matcher = pattern.matcher(it)
            return matcher.matches()
        } ?: kotlin.run {
            return false
        }
    }

    fun verifyDate(day: Editable?, month: Editable?, year: Editable?): String? {
        day?.let { d ->
            month?.let { m ->
                year?.let { y ->
                    val sDay = if (d.toString().isEmpty()) 0 else d.toString().toInt()
                    val sMonth = if (m.toString().isEmpty()) 0 else m.toString().toInt()
                    val sYear = if (y.toString().isEmpty()) 0 else y.toString().toInt()
                    if (sDay == 0 || sMonth == 0 || sYear == 0) {
                        return null
                    } else {
                        val calendar = Calendar.getInstance()

                        return if (sYear > calendar.get(Calendar.YEAR) || sYear.toString().length < 4) {
                            null
                        } else if (sMonth > 12) {
                            null
                        } else {
                            val date = Calendar.getInstance()
                            date.set(Calendar.YEAR, sYear)
                            date.set(Calendar.MONTH, sMonth)
                            val totalDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                            if (sDay > totalDay) {
                                null
                            } else {
                                sDay.toString().plus("/").plus(sMonth.toString()).plus("/")
                                    .plus(sYear.toString())
                            }
                        }
                    }
                } ?: kotlin.run {
                    return null
                }
            } ?: kotlin.run {
                return null
            }
        } ?: kotlin.run {
            return null
        }
    }

    fun insertData(pan: String?, verifyDate: String?) {
        viewModelScope.launch {
            getDatabase().getUserDetailDao()
                .insertUserDetail(UserDataItem(pan = pan.toString(), date = verifyDate.toString()))
            withContext(Dispatchers.Main) {
                mInsertData.value = true
            }
        }
    }
}