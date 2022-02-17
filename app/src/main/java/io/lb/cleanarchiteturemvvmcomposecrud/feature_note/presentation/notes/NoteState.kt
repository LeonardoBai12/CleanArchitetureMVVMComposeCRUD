package io.lb.cleanarchiteturemvvmcomposecrud.feature_note.presentation.notes

import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.domain.model.Note
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.domain.util.NoteOrder
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.domain.util.OrderType

data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Desencding),
    val isOrderSectionVisible: Boolean = false
)