package com.mindiqo.backend.controller;

import com.mindiqo.backend.entity.Note;
import com.mindiqo.backend.entity.Result;
import com.mindiqo.backend.entity.Tag;
import com.mindiqo.backend.service.NoteService;
import com.mindiqo.backend.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/notes")
public class NoteController {
    @Autowired
    private TagService tagService;
    @Autowired
    private NoteService noteService;

    /**
     * Fetches all tags.
     *
     * @return All available tags wrapped in a Result object.
     */
    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    public Result getAllTags(){
        log.info("Get all tags");
        return Result.success(tagService.getAll());
    }
    /**
     * Fetches all notes for a given user.
     *
     * @param userId The ID of the user.
     * @return All notes for the user wrapped in a Result object.
     */
    @RequestMapping( method = RequestMethod.GET)
    public Result getAllNotes(@RequestAttribute("userId") Integer userId){
        log.info("user {} Get all notes", userId);
        return Result.success(noteService.getAll(userId));
    }
    /**
     * Renames the title of a note.
     *
     * @param note The new note details with the title.
     * @param id The ID of the note to be renamed.
     * @param userId The ID of the user.
     * @return A Result object indicating success or failure.
     */
    @RequestMapping(value = "/{id}/rename", method = RequestMethod.POST)
    public Result renameNote(@RequestBody Note note, @PathVariable Integer id, @RequestAttribute("userId") Integer userId){
        note.setId(id);
        note.setCreatorId(userId);
        log.info("Rename note" + note);
        noteService.updateTitle(note);
        return Result.success();
    }
    /**
     * Edits the abstract of a note.
     *
     * @param note The new note details with the abstract.
     * @param id The ID of the note.
     * @param userId The ID of the user.
     * @return A Result object indicating success or failure.
     */
    @RequestMapping(value = "/{id}/edit_abs", method = RequestMethod.POST)
    public  Result editAbs(@RequestBody Note note, @PathVariable Integer id, @RequestAttribute("userId") Integer userId){
        note.setId(id);
        note.setCreatorId(userId);
        log.info("Edit abstract" + note);
        noteService.updateAbs(note);
        return Result.success();
    }
    /**
     * Updates the image associated with a note.
     *
     * @param note The new note details with the image URL or base64 encoded string.
     * @param id The ID of the note.
     * @param userId The ID of the user.
     * @return A Result object indicating success or failure.
     */
    @RequestMapping(value = "/{id}/change_img", method = RequestMethod.POST)
    public Result changeImg(@RequestBody Note note, @PathVariable Integer id, @RequestAttribute("userId") Integer userId){
        note.setId(id);
        note.setCreatorId(userId);
        log.info("Change image" + note);
        noteService.updateImg(note);
        return Result.success();
    }
    /**
     * Deletes a note.
     *
     * @param id The ID of the note to be deleted.
     * @param userId The ID of the user.
     * @return A Result object indicating success or failure.
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    public Result delete(@PathVariable Integer id, @RequestAttribute("userId") Integer userId){
        log.info("Delete note with id: {}", id);
        noteService.deleteById(id, userId);
        return Result.success();
    }
    /**
     * Adds a new note.
     *
     * @param note The details of the note to be added.
     * @param userId The ID of the user.
     * @return A Result object containing the added note.
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Note note, @RequestAttribute("userId") Integer userId){
        log.info("Add note: {}", note);
        note.setCreatorId(userId);
        return Result.success(noteService.add(note));
    }
    /**
     * Fetches a note by its ID.
     *
     * @param id The ID of the note.
     * @param userId The ID of the user.
     * @return A Result object containing the fetched note.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result getNoteById(@PathVariable Integer id, @RequestAttribute("userId") Integer userId){
        log.info("Get note with id: {}", id);
        return Result.success(noteService.getById(id, userId));
    }
    /**
     * Adds a tag to a note.
     *
     * @param id The ID of the note.
     * @param tag The tag to be added.
     * @param userId The ID of the user.
     * @return A Result object indicating success or failure.
     */
    @RequestMapping(value = "/{id}/add_tag", method = RequestMethod.POST)
    public Result addTagToNoteById(@PathVariable Integer id, @RequestBody Tag tag, @RequestAttribute("userId") Integer userId){
        log.info("Add tag {} to note with id: {}", tag, id);
        noteService.addTagToNoteById(id, tag); // todo: user id didn't use

        return Result.success();

    }
    /**
     * Removes a tag from a note.
     *
     * @param noteId The ID of the note.
     * @param tag The tag to be removed.
     * @param userId The ID of the user.
     * @return A Result object indicating success or failure.
     */
    @RequestMapping(value = "/{noteId}/remove_tag", method = RequestMethod.POST)
    public Result removeTagFromNoteById(@PathVariable Integer noteId, @RequestBody Tag tag, @RequestAttribute("userId") Integer userId){
        log.info("Remove tag {} from note with id: {}", tag, noteId);
        noteService.removeTagFromNoteById(noteId, tag);

        return Result.success();

    }
    /**
     * Adds a new tag.
     *
     * @param tag The details of the tag to be added.
     * @return A Result object indicating success or failure.
     */
    @RequestMapping(value = "/tags/add", method = RequestMethod.POST)
    public Result addTag(@RequestBody Tag tag){
        log.info("Add tag: {}", tag);
        tagService.add(tag);
        return Result.success();
    }
    /**
     * Saves a note's content.
     *
     * @param id The ID of the note.
     * @param note The details of the note with content in HTML format.
     * @param userId The ID of the user.
     * @return A Result object indicating success or failure.
     */
    @RequestMapping(value="{id}/save", method = RequestMethod.POST)
    public Result saveNote(@PathVariable Integer id, @RequestBody Note note, @RequestAttribute("userId") Integer userId){
        note.setId(id);
        note.setCreatorId(userId);
        log.info("Save note: {}", note);
        noteService.saveNote(note);
        return Result.success();
    }
    /**
     * Marks a note as public.
     *
     * @param id The ID of the note.
     * @param note The details of the note.
     * @param userId The ID of the user.
     * @return A Result object indicating success or failure.
     */
@RequestMapping(value = "/{id}/public", method = RequestMethod.POST)
    public Result publicNote(@PathVariable Integer id, @RequestBody Note note, @RequestAttribute("userId") Integer userId){
        note.setId(id);
        note.setCreatorId(userId);
        log.info("Post note: {}", note);
        noteService.post(note);
        return Result.success();
    }
}
