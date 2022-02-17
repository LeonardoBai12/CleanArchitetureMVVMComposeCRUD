package io.lb.cleanarchiteturemvvmcomposecrud.feature_note.domain.use_case

import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.data.repository.NoteRepository
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.domain.model.Note

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {
     suspend operator fun invoke(note: Note) {
         repository.deleteNote(note)
     }
}