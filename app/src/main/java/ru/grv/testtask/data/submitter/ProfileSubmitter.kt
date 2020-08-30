package ru.grv.testtask.data.submitter

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url
import ru.grv.testtask.data.response.profile.ProfileResponse
import ru.grv.testtask.presentation.profile.view.TOKEN

class ProfileSubmitter: BaseSubmitter<ProfileResponse>() {
    private val url: String = "/v0/b/test-f4f35.appspot.com/o/$TOKEN%2Fprofile.json?alt=media"

    override fun createCall(): Call<ProfileResponse> {
        return retrofit.create(ITask::class.java).fetchProfile(url)
    }

    private interface ITask {
        @GET
        fun fetchProfile(@Url url: String): Call<ProfileResponse>
    }
}