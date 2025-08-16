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

class AsyncTaskManager {
    interface OnAllTasksCompleteListener {
        fun onAllComplete()
        fun onError(error: String)
        fun onProgress(completed: Int, total: Int)
    }

    class TaskCounter(
        private val totalTasks: Int,
        private val listener: OnAllTasksCompleteListener
    ) {
        private var completedTasks = 0
        private var hasError = false

        @Synchronized
        fun onTaskComplete() {
            if (!hasError) {
                completedTasks++
                listener.onProgress(completedTasks, totalTasks)
                if (completedTasks == totalTasks) {
                    listener.onAllComplete()
                }
            }
        }

        @Synchronized
        fun onTaskError(error: String) {
            if (!hasError) {
                hasError = true
                listener.onError(error)
            }
        }
    }
}