package ru.grv.testtask.data.response.profile

data class ProfileResponse(
    var data: ProfileResponseData? = null,
    var status: String? = null,
    var reason: String? = null
)