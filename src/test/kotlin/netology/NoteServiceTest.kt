package netology

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.netology.Comment
import ru.netology.Note
import ru.netology.NoteNotFoundException
import ru.netology.NoteService


class NoteServiceTest {

    // Объявление переменнрой для создания экземпляра класса с объявлением непосредственно в тесте
    // lateinit ключевое слово для объявления переменной позже
    private lateinit var noteService: NoteService

    @Before
    fun clearBeforeTest() {
        noteService = NoteService
        NoteService.clear()
    }

    @Test
    fun add() {
        val note = Note(
            id = 1,
            title = "Тестовая заметка",
            text = "Это текст тестовой заметки"
        )
        val addedNote = noteService.add(note)
        assertEquals(note, addedNote)
    }

    @Test
    fun update() {
        val note = Note(
            id = 1,
            title = "Тестовая заметка",
            text = "Это текст тестовой заметки"
        )
        noteService.add(note)

        val updatedNote = note.copy(text = "Обновлениетестовой заметки")
        assertTrue(noteService.update(updatedNote))

        val retrievedNote = noteService.getNoteById(1)
        assertEquals(updatedNote, retrievedNote)
    }

    @Test(expected = NoteNotFoundException::class)
    fun createCommentOnNonExistentNote() {
        val comment = Comment(
            id = 1,
            from_id = 1,
            date = 123456,
            text = "Тестовый комментарий"
        )
        noteService.createComment(1, comment)
    }

    @Test
    fun deleteNote() {
        val note = Note(
            id = 1,
            title = "Тестовая заметка",
            text = "Это текст тестовой заметки"
        )
        noteService.add(note)

        assertTrue(noteService.delete(1))

        assertNull(noteService.getNoteById(1))
    }

    @Test
    fun restoreNote() {
        val note = Note(
            id = 1,
            title = "Тестовая заметка",
            text = "Это текст тестовой заметки"
        )
        noteService.add(note)
        noteService.delete(1)

        assertTrue(noteService.restore(1))

        val restoredNote = noteService.getNoteById(1)
        assertNotNull(restoredNote)
        assertFalse(restoredNote!!.deleted)
    }
}