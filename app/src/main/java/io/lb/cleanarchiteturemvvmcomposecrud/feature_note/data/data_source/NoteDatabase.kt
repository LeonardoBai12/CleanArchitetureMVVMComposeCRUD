package io.lb.cleanarchiteturemvvmcomposecrud.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDAO: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}