package com.setvect.bokslportal.note.repository;

import com.setvect.bokslportal.note.vo.NoteVo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 노트 내용
 */
public interface NoteRepository extends JpaRepository<NoteVo, Integer>, NoteRepositoryCustom {
}
