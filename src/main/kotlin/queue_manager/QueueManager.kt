package queue_manager

import kotlinx.coroutines.channels.ReceiveChannel
import queue_manager.model.QueueState

interface QueueManager<T> {

    suspend fun addItem(item: T)

    suspend fun getProgress(): ReceiveChannel<QueueItemProgress<T>>

    fun getCurrentList(): Map<T, QueueItemProgress<T>>

}

data class QueueItemProgress<T>(
    val item: T,
    val state: QueueState
)