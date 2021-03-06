package com.udobs.onetwotrip_testtask.utils

data class Resource<out T>(val status: Status, val data: T?, val error: Throwable?) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, error = null)

        fun <T> error(error: Throwable): Resource<T> =
            Resource(status = Status.ERROR, data = null, error = error)

        fun <T> loading(): Resource<T> =
            Resource(status = Status.LOADING, data = null, error = null)
    }
}
