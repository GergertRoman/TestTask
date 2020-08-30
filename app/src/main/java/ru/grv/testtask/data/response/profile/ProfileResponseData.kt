package ru.grv.testtask.data.response.profile


data class ProfileResponseData(
    var account_id: String? = null,
    var birth_date: String? = null,
    var city: String? = null,
    var country: String? = null,
    var email: String? = null,
    var first_name: String? = null,
    var last_name: String? = null,
    var gender: String? = null,
    var is_full: Boolean? = null,
    var lang: String? = null,
    var phone_number: String? = null,
    var vip: Boolean? = null,
    var vip_expired: String? = null
) {
    fun getGenderName(): String {
        return when(gender) {
            "m" -> "Мужской"
            "w" -> "Женский"
            else -> "Нет данных"
        }
    }
}