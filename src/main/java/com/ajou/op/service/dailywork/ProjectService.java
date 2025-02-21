package com.ajou.op.service.dailywork;

import com.ajou.op.domain.dailywork.Project;
import com.ajou.op.domain.user.User;
import com.ajou.op.exception.ErrorCode;
import com.ajou.op.exception.OpApplicationException;
import com.ajou.op.repositoty.dailywork.ProjectRepository;
import com.ajou.op.request.dailywork.ProjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ajou.op.exception.ErrorCode.INVALID_PERMISSION;


@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public void saveProjects(List<ProjectRequest> requests, User user) {
        List<Project> projects = requests.stream().map((req) -> Project.builder()
                        .user(user)
                        .goals(req.getGoals())
                        .startedAt(req.getStartedAt())
                        .endedAt(req.getEndedAt())
                        .build())
                .toList();

        projectRepository.saveAll(projects);
    }


    public void updateProject(Long id, ProjectRequest request, User user) {
        Project entity = projectRepository.findById(id)
                .orElseThrow(() -> new OpApplicationException(INVALID_PERMISSION));

        if(!entity.getUser().equals(user)) {
            throw new OpApplicationException(INVALID_PERMISSION);
        }

        entity.changeGoals(request.getGoals());
    }


    public  void deleteProject(Long id, User user) {
        Project entity = projectRepository.findById(id)
                .orElseThrow(() -> new OpApplicationException(INVALID_PERMISSION));

        if(!entity.getUser().equals(user)) {
            throw new OpApplicationException(INVALID_PERMISSION);
        }
        projectRepository.delete(entity);
    }
}
