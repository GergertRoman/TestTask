package ru.grv.testtask.data.repository

import android.content.Context
import io.reactivex.Maybe
import io.reactivex.Single
import ru.grv.testtask.data.Constants
import ru.grv.testtask.R
import ru.grv.testtask.data.db.TestTaskDatabase
import ru.grv.testtask.data.entity.mapper.IProfileResponseMapper
import ru.grv.testtask.data.response.profile.ProfileResponse
import ru.grv.testtask.domain.entity.*
import ru.grv.testtask.data.storage.ProfileStorage
import ru.grv.testtask.domain.repository.IProfileRepository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ProfileRepository
@Inject constructor(
    private val storage: ProfileStorage,
    private val db: TestTaskDatabase,
    private val context: Context,
    private val userResponseMapper: IProfileResponseMapper
) : IProfileRepository, BaseRepository() {

    override fun getProfileInfo(): Maybe<ProfileEntity> {
        return db.profileDao().getProfile()
    }

    override fun fetchProfileInfo(): Single<ProfileEntity> {
        if (!isNetworkAvailable(context)) {
            definitionError(Constants.NETWORK_UNAVAILABLE_ERROR_TYPE)
        }
        return storage
            .fetchProfileInfo()
            .map {
                if (it.data == null) {
                    definitionError(it.reason)
                }
                userResponseMapper.map(it)
                //extractProfileInfoFromResponse(it)
            }
    }

    override fun writeProfileInfoInDb(entity: ProfileEntity) {
        db.profileDao().deleteProfile()
        db.profileDao().insertProfile(entity)
    }
}