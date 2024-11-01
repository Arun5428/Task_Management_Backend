package com.arun.taksServices.services;

import com.arun.taksServices.Repo.TaskRepository;
import com.arun.taksServices.model.Task;
import com.arun.taksServices.model.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServicesImp implements TaskServices {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task, String requesterRole) throws Exception {
        if(!requesterRole.equals(("ROLE_ADMIN"))){
            throw new Exception("only admin can create task");
        }
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) throws Exception {

        return taskRepository.findById(id).orElseThrow(()->new Exception("task not found witn id"+id));
    }

    @Override
    public List<Task> getAllTasks(TaskStatus status) {
        List<Task> alltask=taskRepository.findAll();
        List<Task>filterTask=alltask.stream().filter(task->status==null || task.getStatus().name()
                .equalsIgnoreCase(status.toString())
        ).collect(Collectors.toList());


        return filterTask;
    }

    @Override
    public Task updateTask(Long id, Task updatedTask, Long UserID) throws Exception {

        Task exstingTask=getTaskById(id);
        if(updatedTask.getTitle()!=null){
            exstingTask.setTitle(updatedTask.getTitle());
        }
        if(updatedTask.getImage()!=null){
            exstingTask.setImage(updatedTask.getImage());
        }
        if(updatedTask.getDescription()!=null){
            exstingTask.setDescription(updatedTask.getDescription());
        }
        if(updatedTask.getStatus()!=null){
            exstingTask.setStatus(updatedTask.getStatus());
        }
        if(updatedTask.getDeadline()!=null){
            exstingTask.setDeadline(updatedTask.getDeadline());
        }
        return taskRepository.save(exstingTask);
    }

    @Override
    public void deleteTask(Long id) throws Exception {
        getTaskById(id);

        taskRepository.deleteById(id);

    }

    @Override
    public Task assingnedToUser(Long userId, Long taskId) throws Exception {
        Task task=getTaskById(taskId);
        task.setAssignedUserID(userId);
        task.setStatus(TaskStatus.ASSIGNED);


        return taskRepository.save(task);
    }

    @Override
    public List<Task> assignedUsersTask(Long userId, TaskStatus status) {
        List<Task> allTask=taskRepository.findByAssignedUserID(userId);
        List<Task>filterTask=allTask.stream().filter(task->status==null || task.getStatus().name()
                .equalsIgnoreCase(status.toString())
        ).collect(Collectors.toList());


        return filterTask;
    }

    @Override
    public Task completeTask(Long taskId) throws Exception {

        Task task=getTaskById(taskId);
        task.setStatus(TaskStatus.DONE);


        return taskRepository.save(task);
    }
}
