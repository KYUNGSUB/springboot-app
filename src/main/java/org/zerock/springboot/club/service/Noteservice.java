package org.zerock.springboot.club.service;

import org.zerock.springboot.club.dto.NoteDTO;
import org.zerock.springboot.club.entity.Note;
import org.zerock.springboot.member.entity.Member;

import java.util.List;

public interface Noteservice {
    Long register(NoteDTO noteDTO);
    NoteDTO get(Long num);
    void modify(NoteDTO noteDTO);
    void remove(Long num);
    List<NoteDTO> getAllWithWriter(Long writerMid);

    default Note dtoToEntity(NoteDTO noteDTO){
        Note note = Note.builder()
                .num(noteDTO.getNum())
                .title(noteDTO.getTitle())
                .content(noteDTO.getContent())
                .writer(Member.builder().mid(noteDTO.getWriterMid()).build())
                .build();
        return note;
    }

    default NoteDTO entityToDTO(Note note){
        NoteDTO noteDTO = NoteDTO.builder()
                .num(note.getNum())
                .title(note.getTitle())
                .content(note.getContent())
                .writerMid(note.getWriter().getMid())
                .regDate(note.getRegDate())
                .modDate(note.getModDate())
                .build();
        return noteDTO;
    }
}