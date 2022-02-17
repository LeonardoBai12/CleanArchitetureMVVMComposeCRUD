package io.lb.cleanarchiteturemvvmcomposecrud.feature_note.domain.use_case

import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.domain.repository.NoteRepository
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.domain.model.Note

class GetNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}