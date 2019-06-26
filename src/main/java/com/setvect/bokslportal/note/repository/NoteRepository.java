package com.setvect.bokslportal.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.bokslportal.note.vo.NoteVo;

/**
 * 노트 내용
 */
public interface NoteRepository extends JpaRepository<NoteVo, Integer>, NoteRepositoryCustom {
}
