package org.com.hcmurs.common.exception

sealed class AppException(message: String?) : Exception(message) {
    class NetworkException : AppException("No internet connection")
    class UnauthorizedException : AppException("Unauthorized")
    class TimeoutException : AppException("Request timed out")
    class UnexpectedException(msg: String?) : AppException(msg)

    class InvalidCredentialsException(message: String = "Wrong username or password") : AppException(message)
}
