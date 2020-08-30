package ru.grv.testtask.data.repository

import ru.grv.testtask.Constants.INTERNAL_BACKEND_ERROR_TYPE
import ru.grv.testtask.Constants.TOKEN_EXPIRED_TYPE

open class BaseRepository() {
    open fun definitionError(reason: String?) {
        when(reason) {
            TOKEN_EXPIRED_TYPE -> throw TokenExpiredException()
            INTERNAL_BACKEND_ERROR_TYPE -> throw InternalBackendException()
            else -> throw RuntimeException()
        }
    }
}