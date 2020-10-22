package com.example.moshidemo

import android.os.Bundle
import android.util.Log
import com.example.moshidemo.http.Api
import com.example.moshidemo.http.User

class MainActivity2 : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        retrofit<User> {
            api = RetrofitCreater.create(Api::class.java).login()
            onSuccess {
                Log.e(TAG, "result = ${it?.id}")
            }
            onFailed { msg, _ ->
                Log.e(TAG, "onFailed = $msg")
            }
        }


        retrofit<User> {
            api = RetrofitCreater.create(Api::class.java).login()
            onSuccess {
                Log.e(TAG, "result = ${it?.id}")
            }
        }


    }

}