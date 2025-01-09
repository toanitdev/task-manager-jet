package com.toanitdev.taskmanager.core.helper

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getCurrentDateTimeString() : String{
    return LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}