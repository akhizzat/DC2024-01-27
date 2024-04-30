package org.example.publisher.impl.editor.service;

import org.example.publisher.api.exception.DuplicateEntityException;
import org.example.publisher.api.exception.EntityNotFoundException;
import org.example.publisher.impl.editor.Editor;
import org.example.publisher.impl.editor.EditorRepository;
import org.example.publisher.impl.editor.dto.EditorRequestTo;
import org.example.publisher.impl.editor.dto.EditorResponseTo;
import org.example.publisher.impl.editor.mapper.Impl.EditorMapperImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EditorService {

    private final EditorRepository editorRepository;

    private final EditorMapperImpl editorMapper;
    private final String ENTITY_NAME = "editor";

    public List<EditorResponseTo> getEditors(){
        List<Editor> editors = editorRepository.findAll();
        return editorMapper.editorToResponseTo(editors);
    }

    public EditorResponseTo getEditorById(BigInteger id) throws EntityNotFoundException{
        Optional<Editor> editor = editorRepository.findById(id);
        if (editor.isEmpty()){
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        return editorMapper.editorToResponseTo(editor.get());
    }

    public EditorResponseTo createEditor(EditorRequestTo editor) throws DuplicateEntityException {
        try {
            Editor savedEditor = editorRepository.save(editorMapper.dtoToEntity(editor));
            return editorMapper.editorToResponseTo(savedEditor);

        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException(ENTITY_NAME, "login");
        }
    }

    public EditorResponseTo updateEditor(EditorRequestTo editor) throws EntityNotFoundException {
        if (editorRepository.findById(editor.getId()).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, editor.getId());
        }
        Editor savedEditor = editorRepository.save(editorMapper.dtoToEntity(editor));
        return editorMapper.editorToResponseTo(savedEditor);
    }


    public void deleteEditor(BigInteger id) throws EntityNotFoundException {
        if (editorRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        editorRepository.deleteById(id);
    }
}
