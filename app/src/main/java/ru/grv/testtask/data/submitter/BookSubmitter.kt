package ru.grv.testtask.data.submitter

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url
import ru.grv.testtask.data.response.book.BooksResponse
import ru.grv.testtask.presentation.profile.view.TOKEN

class BookSubmitter: BaseSubmitter<BooksResponse>() {
    private val url: String = "/v0/b/test-f4f35.appspot.com/o/$TOKEN%2Fbooks.json?alt=media"

    override fun createCall(): Call<BooksResponse> {
        return retrofit.create(ITask::class.java).fetchBooks(url)
    }

    private interface ITask {
        @GET
        fun fetchBooks(@Url url: String): Call<BooksResponse>
    }
}