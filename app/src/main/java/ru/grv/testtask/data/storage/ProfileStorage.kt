package ru.grv.testtask.data.storage

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import ru.grv.testtask.data.response.profile.ProfileResponse
import ru.grv.testtask.data.submitter.ProfileSubmitter
import javax.inject.Inject

class ProfileStorage @Inject constructor() {
    fun fetchProfileInfo(): Single<ProfileResponse> {
        return ProfileSubmitter().single
    }
}