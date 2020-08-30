package ru.grv.testtask.data.submitter

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

abstract class BaseSubmitter<T> {
    val observable: Observable<T> = initObservable()

    // Code
    private val HTTP_OK = 200
    private val HTTP_FAILED = 400

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)

    val retrofit: Retrofit = Retrofit.Builder()
        .client(httpClient.build())
        .baseUrl(Companion.URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun initObservable(): Observable<T> {
        return Observable.create<T> { e: ObservableEmitter<T> ->
            run {
                try {
                    if (e.isDisposed)
                        return@run
                    val response: T? = submit()
                    if (response != null) {
                        e.onNext(response)
                        e.onComplete()
                    } else {
                        throw Exception()
                    }
                } catch (t: Throwable) {

                }
            }
        }
    }

    protected abstract fun createCall(): Call<T>

    private fun submit(): T? {
        val retrofitResponse = createCall().execute()
        return when (retrofitResponse.code()) {
            HTTP_OK -> retrofitResponse.body()
            HTTP_FAILED -> throw Exception()
            else -> null
        }
    }

    companion object {
        private const val URL = "https://firebasestorage.googleapis.com"
    }
}