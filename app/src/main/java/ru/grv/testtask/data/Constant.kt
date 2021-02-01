package ru.grv.testtask.data

object Constants {
    const val DEFAULT_STRING = ""
    const val DATE_FORMAT_SHORT_SERVER = "yyyy-MM-dd"

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

    //TOKEN
    // можно получить корректные данные по профилю и списку книг.
    const val BOOKS_PROFILE_TOKEN = "ziPZ63IYeuRiHz2ytNlzDw0h98Ve6A7I"

    // можно получить профиль с незаполненными данными и списком книг из одной книги.
    const val EMPTY_PROFILE_TOKEN = "hldktIc3tiGx3Tu6UlhpXNH993u8vifT"

    // можно получить профиль с заполненными данными и с пустым списком книг.
    const val EMPTY_BOOKS_TOKEN = "RDhi7k79GSmdZcW4Gg7X0sUNUbYQZsxF"

    // можно получить ошибку token expired.
    const val ERROR_TOKEN = "xfTV94kfsVFyxPfvTg55aDx_jxvrNUBg"

    // можно получить ошибку internal backend error.
    const val BACKEND_ERROR_TOKEN = "LbwCgZvvlFO2ydRK5BAfau2elUYnauNT"
}