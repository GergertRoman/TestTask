package ru.grv.testtask.data.entity.mapper

import android.content.Context
import ru.grv.testtask.data.response.profile.ProfileResponse
import ru.grv.testtask.domain.entity.ProfileEntity

interface IProfileResponseMapper {
    fun map(data: ProfileResponse): ProfileEntity
}