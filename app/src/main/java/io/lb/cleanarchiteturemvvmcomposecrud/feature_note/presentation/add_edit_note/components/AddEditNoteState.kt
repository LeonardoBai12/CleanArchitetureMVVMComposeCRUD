package io.lb.cleanarchiteturemvvmcomposecrud.feature_note.presentation.add_edit_note.components

import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.domain.model.Note

data class AddEditNoteState (
    val note: Note? = null,
)