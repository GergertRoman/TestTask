package ru.grv.testtask.data.submitter

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

abstract class BaseSubmitter<T> {
    val observable: Observable<T> = initObservable()
    val maybe: Maybe<T> = initMaybe()
    val single: Single<T> = initSingle()

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
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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

    private fun initMaybe(): Maybe<T> {
        return Maybe.create<T> { e: MaybeEmitter<T> ->
            run {
                try {
                    if (e.isDisposed)
                        return@run
                    val response: T? = submit()
                    if (response != null) {
                        e.onSuccess(response)
                        e.onComplete()
                    } else {
                        throw Exception()
                    }
                } catch (t: Throwable) {

                }
            }
        }
    }

    private fun initSingle(): Single<T> {
        return Single.create<T> { e: SingleEmitter<T> ->
            run {
                try {
                    if (e.isDisposed)
                        return@run
                    val response: T? = submit()
                    if (response != null) {
                        e.onSuccess(response)
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