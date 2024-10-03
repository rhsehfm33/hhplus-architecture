package hhplus.architecture.interfaces.lecture;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hhplus.architecture.domain.lecture.LectureService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LectureController {
    private final LectureService lectureService;

    @GetMapping("/available-lectures")
    ResponseEntity<List<LectureResponse>> getAvailableLectures(@RequestBody LectureRequest lectureRequest) {
        List<LectureResponse> lectureResponses = lectureService.getAvailableLectures(
            lectureRequest.lectureDate().atTime(0, 0))
            .stream().map(lectureInfo -> new LectureResponse(
                lectureInfo.lectureId(), lectureInfo.userId(), lectureInfo.lectureName(), lectureInfo.userName()
            )).toList();
        return ResponseEntity.ok(lectureResponses);
    }
}
