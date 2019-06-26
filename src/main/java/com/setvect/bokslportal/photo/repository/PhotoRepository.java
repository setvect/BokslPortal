package com.setvect.bokslportal.photo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.bokslportal.photo.vo.PhotoVo;


/**
 * 사진 Repository
 */
public interface PhotoRepository extends JpaRepository<PhotoVo, String>, PhotoRepositoryCustom {
}
