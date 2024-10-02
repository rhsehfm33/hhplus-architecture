package hhplus.architecture.interfaces.registration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hhplus.architecture.domain.registration.LectureRegistrationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lecture-registrations")
public class LectureRegistrationController {
    private final LectureRegistrationService lectureRegistrationService;

    @PostMapping
    public ResponseEntity<Boolean> registerLecture(
        @RequestBody LectureRegistrationRequest lectureRegistrationRequest
    ) {
        lectureRegistrationService.registerLecture(
            lectureRegistrationRequest.lectureId(), lectureRegistrationRequest.userId()
        );
        return ResponseEntity.ok(true);
    }
}
