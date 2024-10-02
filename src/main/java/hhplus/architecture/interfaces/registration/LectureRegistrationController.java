package hhplus.architecture.interfaces.registration;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<Void> registerLecture(
        @RequestBody LectureRegistrationRequest lectureRegistrationRequest
    ) {
        lectureRegistrationService.registerLecture(
            lectureRegistrationRequest.lectureId(), lectureRegistrationRequest.userId()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<LectureRegistrationResponse>> getRegisteredLecturesByUserId(@PathVariable Long id) {
        List<LectureRegistrationResponse> lectureRegistrationResponses =
            lectureRegistrationService.getRegisteredLecturesByUserId(id).stream()
                .map(lectureRegistration -> new LectureRegistrationResponse(
                    lectureRegistration.lectureId(),
                    lectureRegistration.userId(),
                    lectureRegistration.lectureName(),
                    lectureRegistration.userName(),
                    lectureRegistration.createdAt()
                )).toList();
        return ResponseEntity.ok(lectureRegistrationResponses);
    }
}
