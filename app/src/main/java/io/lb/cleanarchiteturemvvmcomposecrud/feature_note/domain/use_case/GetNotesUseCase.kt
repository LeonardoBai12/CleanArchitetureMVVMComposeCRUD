package io.lb.cleanarchiteturemvvmcomposecrud.feature_note.domain.use_case

import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.data.repository.NoteRepository
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.domain.model.Note
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.domain.util.NoteOrder
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Desencding)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when(noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when(noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy {
                            it.title.lowercase()
                        }
                        is NoteOrder.Color -> notes.sortedBy {
                            it.color
                        }
                        is NoteOrder.Date -> notes.sortedBy {
                            it.timestamp
                        }
                    }
                }
                is OrderType.Desencding -> {
                    when(noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending {
                            it.title.lowercase()
                        }
                        is NoteOrder.Color -> notes.sortedByDescending {
                            it.color
                        }
                        is NoteOrder.Date -> notes.sortedByDescending {
                            it.timestamp
                        }
                    }
                }
            }
        }
    }
}