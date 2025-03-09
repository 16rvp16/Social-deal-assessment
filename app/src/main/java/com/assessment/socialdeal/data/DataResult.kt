package com.assessment.socialdeal.data

enum class ResultState {
    Success,
    Failure,
}

/**
 * Class to wrap function result in for repositories, so that both success and failure states can
 * be reported without making the calling classes responsible for exception handling
 */
sealed class DataResult<T> (val resultState: ResultState, val result: T? = null) {

    class Success<T>(result :T): DataResult<T>(ResultState.Success, result)
    class Failure<T>(): DataResult<T>(ResultState.Failure, null)
}