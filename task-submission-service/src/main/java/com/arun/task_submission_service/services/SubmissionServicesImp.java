package com.arun.task_submission_service.services;

import com.arun.task_submission_service.model.Submission;
import com.arun.task_submission_service.model.TaskDto;
import com.arun.task_submission_service.repository.SubmissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionServicesImp  implements SubmissionServices{
    @Autowired
    private SubmissionRepo submissionRepo;
    @Autowired
    private TaskServices taskServices;
    @Autowired
    private UserServices  userServices;

    @Override
    public Submission submission(Long taskId, String githublink, Long userId,String jwt) throws Exception {
        TaskDto task=taskServices.getTaskById(taskId,jwt);
        if(task!=null){
            Submission submission=new Submission();
            submission.setTaskId(taskId);
            submission.setUserId(userId);
            submission.setGitHubLink(githublink);
            submission.setSubmissionTime(LocalDateTime.now());
            return submissionRepo.save(submission);
        }
        throw new Exception("task not found with id"+taskId);


    }

    @Override
    public Submission getTaskSubmissionById(Long SubmissionId) throws Exception {
        return submissionRepo.findById(SubmissionId).orElseThrow(()->new Exception("task submission not found with id"+SubmissionId));
    }

    @Override
    public List<Submission> getAllTaskSubmission() {

        return submissionRepo.findAll();
    }

    @Override
    public List<Submission> getTaskSubmissionByTaskId(Long taskId) {
        return submissionRepo.findByTaskId(taskId);
    }

    @Override
    public Submission acceptDeclineSubmission(Long id, String status) throws Exception {
        Submission submission=getTaskSubmissionById(id);
        submission.setStatus(status);
        if(status.equals("ACCEPT")){
            taskServices.completTask(submission.getTaskId());

        }


        return submissionRepo.save(submission);
    }
}
