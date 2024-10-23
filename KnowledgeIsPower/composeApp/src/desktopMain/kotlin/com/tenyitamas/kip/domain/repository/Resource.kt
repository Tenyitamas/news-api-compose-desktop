package com.tenyitamas.kip.domain.repository

sealed class Result<T>(
    val data: T?,
    val message: String? = null
) {
    class Success<T>(data: T?): Result<T>(data)
    class Error<T>(message: String, data: T? = null): Result<T>(data, message)
}