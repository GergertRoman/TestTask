package ru.grv.testtask.data.storage

import io.reactivex.Observable
import ru.grv.testtask.data.response.profile.ProfileResponse
import ru.grv.testtask.data.submitter.ProfileSubmitter
import javax.inject.Inject

class ProfileStorage @Inject constructor() {

    fun fetchProfileInfo(): Observable<ProfileResponse> {
        return ProfileSubmitter().observable
    }
}