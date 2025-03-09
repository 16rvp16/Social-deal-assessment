package com.assessment.socialdeal.data

enum class ResultState {
    Loading,
    Success,
    Failure,
}

sealed class DataResult<T> (val resultState: ResultState, val result: T? = null) {

    class Success<T>(result :T): DataResult<T>(ResultState.Success, result)
    class Failure<T>(): DataResult<T>(ResultState.Failure, null)
}