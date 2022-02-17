package io.lb.cleanarchiteturemvvmcomposecrud.feature_note.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.data.data_source.NoteDatabase
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.data.repository.NoteRepository
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.data.repository.NoteRepositoryImpl
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.domain.use_case.AddNoteUseCase
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.domain.use_case.DeleteNoteUseCase
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.domain.use_case.GetNotesUseCase
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.domain.use_case.NoteUseCases
import javax.inject.Singleton

@Module
@InstallIn
object AppModule {
    @Provides
    @Singleton
    fun providesNotesDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDAO)
    }

    @Provides
    @Singleton
    fun providesNoteUseCases(noteRepository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(noteRepository),
            deleteNoteUseCase = DeleteNoteUseCase(noteRepository),
            addNoteUseCase = AddNoteUseCase(noteRepository),
        )
    }
}