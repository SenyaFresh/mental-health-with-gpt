package ru.edu.hse.common

/**
* Use this class to notify object holder (e.g. MutableStateFlow) about changes.
*/
class OnChange<T>(
    val value: T
)