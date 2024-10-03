package hhplus.architecture.infrastructure.registration;

import java.time.LocalDateTime;

import hhplus.architecture.domain.registration.LectureRegistration;
import hhplus.architecture.infrastructure.lecture.LectureEntity;
import hhplus.architecture.infrastructure.user.UserEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "lecture_registrations")
@NoArgsConstructor
public class LectureRegistrationEntity {
    @EmbeddedId
    private LectureRegistrationId id;

    @ManyToOne
    @MapsId("lectureId")
    @JoinColumn(name = "lecture_id")
    private LectureEntity lecture;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    LocalDateTime createdAt;

    public static LectureRegistrationEntity from(LectureEntity lecture, UserEntity user, LocalDateTime createdAt) {
        LectureRegistrationEntity lectureRegistrationEntity = new LectureRegistrationEntity();
        lectureRegistrationEntity.id = new LectureRegistrationId(lecture.getId(), user.getId());
        lectureRegistrationEntity.lecture = lecture;
        lectureRegistrationEntity.user = user;
        lectureRegistrationEntity.createdAt = createdAt;
        return lectureRegistrationEntity;
    }

    public static LectureRegistration to(
        LectureRegistrationEntity lectureRegistrationEntity,
        LectureEntity lectureEntity,
        UserEntity userEntity
    ) {
        return new LectureRegistration(
            LectureEntity.to(lectureEntity),
            UserEntity.to(userEntity),
            lectureRegistrationEntity.getCreatedAt()
        );
    }
}
