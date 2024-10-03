package hhplus.architecture.domain.lecture;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;

    public List<LectureInfo> getAvailableLectures(LocalDateTime time) {
        return lectureRepository.findLecturesContainingDateTime(time).stream()
            .map(lecture -> new LectureInfo(lecture.id(), lecture.userId(), lecture.lectureName(), lecture.userName()))
            .toList();
    }
}
