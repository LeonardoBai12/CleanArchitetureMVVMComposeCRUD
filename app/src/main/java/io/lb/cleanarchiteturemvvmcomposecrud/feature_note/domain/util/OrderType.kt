package io.lb.cleanarchiteturemvvmcomposecrud.feature_note.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}