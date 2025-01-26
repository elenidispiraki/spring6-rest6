package gr.aueb.cf.schoolapp.rest;

import gr.aueb.cf.schoolapp.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TeacherRestController {
    private final TeacherService teacherService;
}
