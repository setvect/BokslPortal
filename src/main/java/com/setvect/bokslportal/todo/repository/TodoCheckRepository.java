package com.setvect.bokslportal.todo.repository;

import com.setvect.bokslportal.todo.vo.TodoCheckVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoCheckRepository extends JpaRepository<TodoCheckVo, Integer> {
}

