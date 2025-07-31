package com.creativeitinstitute.olxcloneanroidapp

import android.widget.EditText

fun EditText.isEmpty(): Boolean{
    return if (this.text.toString().isEmpty()){
        this.error = "This place Need to be Fill up"
        true
    }else{
        false
    }

}

fun EditText.extract():String{

    return this.text.toString()
}