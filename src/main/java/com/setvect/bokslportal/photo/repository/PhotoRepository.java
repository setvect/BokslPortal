package com.setvect.bokslportal.photo.repository;

import com.setvect.bokslportal.photo.vo.PhotoVo;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * 사진 Repository
 */
public interface PhotoRepository extends JpaRepository<PhotoVo, String>, PhotoRepositoryCustom {
}
