package queue_manager.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Channel<T>.filter(predicate: (T) -> Boolean): ReceiveChannel<T> = coroutineScope {
    produce {
        for (x in this@filter) {
            if (predicate(x)) send(x)
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T, E> Channel<T>.destinationMap(map: MutableMap<T, E>, defaultValue: (T) -> E): ReceiveChannel<T> =
    coroutineScope {
        produce {
            for (x in this@destinationMap) {
                map[x] = defaultValue(x)
                send(x)
            }
        }
    }