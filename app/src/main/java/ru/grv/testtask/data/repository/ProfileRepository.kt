package ru.grv.testtask.data.repository

import android.content.Context
import io.reactivex.Observable
import ru.grv.testtask.R
import ru.grv.testtask.data.db.TestTaskDatabase
import ru.grv.testtask.data.response.profile.ProfileResponse
import ru.grv.testtask.domain.entity.*
import ru.grv.testtask.data.storage.ProfileStorage
import ru.grv.testtask.domain.repository.IProfileRepository
import ru.grv.testtask.presentation.profile.view.DATE_FORMAT_SHORT_SERVER
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ProfileRepository
@Inject constructor(
    private val storage: ProfileStorage,
    private val db: TestTaskDatabase,
    private val context: Context
) : IProfileRepository, BaseRepository(){

    override fun getProfileInfo(): Observable<ProfileEntity> {
        return storage
            .fetchProfileInfo()
            .map {
                if (it.data == null) {
                    definitionError(it.reason)
                }
                extractProfileInfoFromResponse(it)
            }
    }

    override fun writeProfileInfoInDb(entity: ProfileEntity?) {
        db.profileDao().insertProfile(entity)
    }

    override fun fetchProfileInfoFromDb(): Observable<ProfileEntity> {
        return db.profileDao().getProfile()
    }

    private fun extractProfileInfoFromResponse(response: ProfileResponse): ProfileEntity {
        val profileResponse = response.data
        val noData: String = context.getText(R.string.no_data) as String
        return ProfileEntity(
            birthDate = profileResponse?.birth_date?.let { getNewFormatDate(it) } ?: noData,
            city = profileResponse?.city ?: noData,
            email = profileResponse?.email ?: noData,
            firstName = profileResponse?.first_name ?: noData,
            lastName = profileResponse?.last_name ?: noData,
            gender = profileResponse?.getGenderName() ?: noData,
            phoneNumber = profileResponse?.phone_number ?: noData
        )
    }

    private fun getNewFormatDate(date: String): String {
        val sServerShortFormat = SimpleDateFormat(DATE_FORMAT_SHORT_SERVER, Locale.US)
        var serverShortFormat = sServerShortFormat
        serverShortFormat.timeZone = TimeZone.getTimeZone("Europe/Moscow")
        val dateStrWithTimeZone = serverShortFormat.parse(date)
        var timeDate = dateStrWithTimeZone.time
        val timelineDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return timelineDateFormat.format(Date(timeDate))+"г."
    }
}