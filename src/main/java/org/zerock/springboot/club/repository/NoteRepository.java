package org.zerock.springboot.club.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.springboot.club.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
	@EntityGraph(attributePaths = "writer", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select n from Note n where n.num = :num")
    Optional<Note> getWithWriter(@Param("num") Long num);

    @EntityGraph(attributePaths = {"writer"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select n from Note n where n.writer.mid = :mid")
    List<Note> getList(@Param("mid") Long mid);
}