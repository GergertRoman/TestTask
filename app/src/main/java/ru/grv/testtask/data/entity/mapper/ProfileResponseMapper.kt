package ru.grv.testtask.data.entity.mapper

import android.content.Context
import ru.grv.testtask.R
import ru.grv.testtask.data.Constants
import ru.grv.testtask.data.response.profile.ProfileResponse
import ru.grv.testtask.domain.entity.ProfileEntity
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ProfileResponseMapper @Inject constructor() : IProfileResponseMapper {

    private val sServerShortFormat = SimpleDateFormat(Constants.DATE_FORMAT_SHORT_SERVER, Locale.US)

    override fun map(data: ProfileResponse, context: Context): ProfileEntity {
        val profileResponse = data.data
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
        sServerShortFormat.timeZone = TimeZone.getTimeZone("Europe/Moscow")
        val dateStrWithTimeZone = sServerShortFormat.parse(date)
        val timeDate = dateStrWithTimeZone.time
        val timelineDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return "${timelineDateFormat.format(Date(timeDate))} г."
    }
}