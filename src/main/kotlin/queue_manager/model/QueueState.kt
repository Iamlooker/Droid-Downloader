package queue_manager.model

sealed interface QueueState {

    object Success : QueueState

    object Error : QueueState

    object Pending : QueueState

    data class Progress(
        val percent: Int,
        val total: Long
    ) : QueueState

}