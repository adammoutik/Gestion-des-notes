package Handlers.interfaces;

import Handlers.Model.Note;

import java.util.List;

public interface INote {
    public void insertNote(Note note);
    public void deleteNote(int id);
    public void updateNote(Note note);
    public Note findNote(int id);
    public List<Note> findAllNotes();
}
