package io.appwrite.starterkit.data.models

/**
 * Represents the various states of a process or operation.
 * This sealed class ensures that only predefined statuses are used.
 */
sealed class Status {
    /**
     * Represents the idle state.
     */
    data object Idle : Status()

    /**
     * Represents a loading state.
     */
    data object Loading : Status()

    /**
     * Represents a successful operation.
     */
    data object Success : Status()

    /**
     * Represents an error state.
     */
    data object Error : Status()
}
