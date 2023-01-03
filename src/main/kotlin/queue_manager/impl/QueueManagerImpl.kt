package queue_manager.impl

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import queue_manager.QueueItemProgress
import queue_manager.QueueManager
import queue_manager.model.QueueState
import queue_manager.utils.destinationMap

// Invoke the variable to start downloading
abstract class QueueManagerImpl<T : Any> : QueueManager<T> {

    private val queue = Channel<T>()
    private val progressChannel = Channel<QueueItemProgress<T>>()
    private val currentStateMap = mutableMapOf<T, QueueItemProgress<T>>()

    suspend operator fun invoke() {
        queue.destinationMap(currentStateMap) { QueueItemProgress(it, QueueState.Pending) }
            .consumeEach { process(it) }
    }

    override suspend fun addItem(item: T) = queue.send(item)

    override fun getCurrentList(): Map<T, QueueItemProgress<T>> = currentStateMap

    override suspend fun getProgress(): ReceiveChannel<QueueItemProgress<T>> = progressChannel

    // Also update the state in map and progressChannel
    abstract suspend fun process(item: T)

}