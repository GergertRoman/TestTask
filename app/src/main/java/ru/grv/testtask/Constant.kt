package ru.grv.testtask

object Constants {
    //ErrorsType
    const val TOKEN_EXPIRED_TYPE = "token_expired"
    const val INTERNAL_BACKEND_ERROR_TYPE = "internal_backend_error"
    const val NETWORK_UNAVAILABLE_ERROR_TYPE = "network_unavailable_error"

    //Key
    const val BOOKS_KEY = "booksKey"

    //Tag
    const val DATA_BASE_TAG = "DataBase"
    const val PROFILE_TAG = "Profile"
    const val BOOK_TAG = "Book"

    //ERROR
    const val ERROR = "Ошибка"
    const val TOKEN_EXPIRED = "Сессия устарела, авторизуйтесь заново"
    const val INTERNAL_BACKEND_ERROR = "Ошибка сервера, попробуйте повторить запрос позже"
    const val NETWORK_UNAVAILABLE_ERROR = "Отсутствует интернет соединение"
    const val DATA_NOT_FOUND = "Данные не найдены"
}