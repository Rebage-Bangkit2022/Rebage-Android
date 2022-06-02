package trashissue.rebage.domain.model

sealed class Result<out R> {
    data class NoData(val loading: Boolean = false) : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val throwable: Throwable) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is NoData -> "NoData"
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[throwable=$throwable]"
        }
    }

    companion object {
        val Loading = NoData(loading = true)
        val Empty = NoData()
    }
}

inline fun <T> Result<T>.success(block: (T) -> Unit) {
    if (this is Result.Success) block(data)
}

inline fun Result<*>.error(block: (Throwable) -> Unit) {
    if (this is Result.Error) block(throwable)
}

inline fun Result<*>.empty(block: (Boolean) -> Unit) {
    if (this is Result.NoData) block(loading)
}

val Result<*>.isLoading: Boolean
    get() {
        empty { return it }
        return false
    }
