package com.vvechirko.newrepository.data

object ErrorParser {
    fun parse(t: Throwable) = t.toString()
}