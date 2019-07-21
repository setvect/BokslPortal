package com.setvect.bokslportal.todo.repository;

import com.setvect.bokslportal.todo.vo.TodoVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TodoVo, Integer>, TodoRepositoryCustom {
}

