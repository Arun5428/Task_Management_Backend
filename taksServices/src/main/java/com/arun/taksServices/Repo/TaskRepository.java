package com.arun.taksServices.Repo;

import com.arun.taksServices.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    public List<Task>findByAssignedUserID(Long userId);


}
