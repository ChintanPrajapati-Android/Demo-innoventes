package com.innoventes.demo.ui.panverify

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.innoventes.demo.R
import com.innoventes.demo.base.BaseActivity
import com.innoventes.demo.viewmodel.DataViewModel
import kotlinx.android.synthetic.main.activity_pan_verify.*

class PanVerifyActivity : BaseActivity(R.layout.activity_pan_verify), TextWatcher {

    private lateinit var dataViewModel: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataViewModel = ViewModelProvider(this)[DataViewModel::class.java]
        edtPan.addTextChangedListener(this)
        edtDate.addTextChangedListener(this)
        edtMonth.addTextChangedListener(this)
        edtYear.addTextChangedListener(this)

        btnNext.setOnClickListener {
            dataViewModel.insertData(edtPan.text.toString(),dataViewModel.verifyDate(
                edtDate.text,
                edtMonth.text,
                edtYear.text
            ))

        }
        tvLabelNoPan.setOnClickListener {
            finish()
        }

        dataViewModel.mInsertData.observe(this, Observer {
            if (it){
                makeToast(getString(R.string.insert_msg))
                edtPan.text =  null
                edtDate.text =  null
                edtMonth.text =  null
                edtYear.text =  null
            }
        })
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        btnNext.isEnabled =
            dataViewModel.verifyPan(edtPan.text.toString()) && dataViewModel.verifyDate(
                edtDate.text,
                edtMonth.text,
                edtYear.text
            ) != null

        btnNext.alpha = if (btnNext.isEnabled)  1.0f else 0.5f
    }

    override fun afterTextChanged(p0: Editable?) {

    }
}