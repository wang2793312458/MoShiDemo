package com.example.moshidemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moshidemo.http.RetrofitCoroutineDSL
import com.example.moshidemo.http.retrofit
import kotlinx.coroutines.*
import java.io.IOException
import java.net.ConnectException
import kotlin.coroutines.CoroutineContext

/**
 * @author wang
 * date:  2020/10/22
 * Tip : 严格要求自己，对每一行代码负责
 * description：(用一句话描述)
 */
open class BaseActivity : AppCompatActivity(),CoroutineScope{

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 关闭页面后，结束所有协程任务
        job.cancel()
    }
}
public fun <T> CoroutineScope.retrofit(dsl: RetrofitCoroutineDSL<T>.() -> Unit) {
    //在主线程中开启协程
    this.launch(Dispatchers.Main) {
        val coroutine = RetrofitCoroutineDSL<T>().apply(dsl)
        coroutine.api?.let { call ->
            //async 并发执行 在IO线程中
            val deferred = async(Dispatchers.IO) {
                try {
                    call.execute() //已经在io线程中了，所以调用Retrofit的同步方法
                } catch (e: ConnectException) {
                    coroutine.onFail?.invoke("网络连接出错", -1)
                    null
                } catch (e: IOException) {
                    coroutine.onFail?.invoke("未知网络错误", -1)
                    null
                }
            }
            //当协程取消的时候，取消网络请求
            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    call.cancel()
                    coroutine.clean()
                }
            }
            //await 等待异步执行的结果
            val response = deferred.await()
            if (response == null) {
                coroutine.onFail?.invoke("返回为空", -1)
            } else {
                response.let {
                    if (response.isSuccessful) {
                        //访问接口成功
                        if (response.body()?.status == 1) {
                            //判断status 为1 表示获取数据成功
                            coroutine.onSuccess?.invoke(response.body()!!.data)
                        } else {
                            coroutine.onFail?.invoke(
                                response.body()?.msg ?: "返回数据为空",
                                response.code()
                            )
                        }
                    } else {
                        coroutine.onFail?.invoke(
                            response.errorBody().toString(),
                            response.code()
                        )
                    }
                }
            }
            coroutine.onComplete?.invoke()
        }
    }
}