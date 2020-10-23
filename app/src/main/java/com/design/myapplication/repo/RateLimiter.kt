package com.design.myapplication.repo

import android.os.Build
import android.os.SystemClock
import android.util.ArrayMap
import androidx.annotation.RequiresApi
import java.util.concurrent.TimeUnit

class RateLimiter<KEY>(timeout: Int, timeUnit: TimeUnit) {
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private val timestamps = ArrayMap<KEY, Long>()
    private val timeout: Long = timeUnit.toMillis(timeout.toLong())

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @Synchronized
    fun shouldFetch(key: KEY): Boolean {
        val lastFetched = timestamps[key]
        val now = now()
        if (lastFetched == null) {
            timestamps.put(key, now)
            return true
        }
        if (now - lastFetched > timeout) {
            timestamps.put(key, now)
            return true
        }
        return false
    }

    private fun now(): Long {
        return SystemClock.uptimeMillis()
    }

    @Synchronized
    fun reset(key: KEY) {
        timestamps.remove(key)
    }
}
