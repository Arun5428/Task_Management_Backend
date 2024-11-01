package com.arun.taksServices.services;

import com.arun.taksServices.model.Task;
import com.arun.taksServices.model.TaskStatus;

import java.util.List;

public interface TaskServices {
    Task createTask(Task task,String  requesterRole)throws Exception;
    Task getTaskById(Long id) throws Exception;
    List<Task> getAllTasks(TaskStatus status);
    Task updateTask(Long id,Task updatedTask,Long UserID)throws Exception;
    void deleteTask(Long id) throws Exception;
    Task assingnedToUser(Long userId,Long taskId) throws Exception;
    List<Task>assignedUsersTask(Long userId,TaskStatus status);
    Task completeTask(Long taskId) throws Exception;

}
