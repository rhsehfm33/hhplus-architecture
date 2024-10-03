package hhplus.architecture.infrastructure.registration;

import java.time.LocalDateTime;

import hhplus.architecture.domain.registration.LectureRegistration;
import hhplus.architecture.infrastructure.lecture.LectureEntity;
import hhplus.architecture.infrastructure.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "lecture_registrations")
@NoArgsConstructor
public class LectureRegistrationEntity {
    @Id
    private long id;

    @ManyToOne
    private LectureEntity lecture;

    @ManyToOne
    private UserEntity user;

    LocalDateTime createdAt;

    public static LectureRegistrationEntity from(LectureEntity lecture, UserEntity user, LocalDateTime createdAt) {
        LectureRegistrationEntity lectureRegistrationEntity = new LectureRegistrationEntity();
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
            lectureRegistrationEntity.getId(),
            LectureEntity.to(lectureEntity),
            UserEntity.to(userEntity),
            lectureRegistrationEntity.getCreatedAt()
        );
    }
}
