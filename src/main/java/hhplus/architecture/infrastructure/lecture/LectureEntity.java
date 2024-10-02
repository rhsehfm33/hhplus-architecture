package hhplus.architecture.infrastructure.lecture;

import java.time.LocalDateTime;

import hhplus.architecture.domain.lecture.Lecture;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "lectures")
@NoArgsConstructor
public class LectureEntity {
    @Id
    private long id;

    private long userId;

    private String name;

    LocalDateTime startDateTime;

    LocalDateTime endDateTime;

    public static LectureEntity from(LectureParams lectureParams) {
        LectureEntity lecture = new LectureEntity();
        lecture.id = lectureParams.lectureId();
        lecture.name = lectureParams.name();
        lecture.userId = lectureParams.teacherId();
        lecture.startDateTime = lectureParams.startDateTime();
        lecture.endDateTime = lectureParams.endDateTime();
        return lecture;
    }

    public static Lecture to(LectureEntity lectureEntity) {
        return new Lecture(
            lectureEntity.getId(), lectureEntity.getUserId(), lectureEntity.getName(),
            lectureEntity.getStartDateTime(), lectureEntity.getEndDateTime()
        );
    }
}
