package io.lb.cleanarchiteturemvvmcomposecrud.feature_note.presentation.add_edit_note.components

data class NoteTextFielState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true,
)