package hhplus.architecture.infrastructure.registration;

import java.time.LocalDateTime;

import hhplus.architecture.domain.registration.LectureRegistration;
import hhplus.architecture.infrastructure.lecture.LectureEntity;
import hhplus.architecture.infrastructure.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    private long lectureId;

    private long userId;

    LocalDateTime createdAt;

    public LectureRegistrationEntity(long lectureId, long userId, LocalDateTime createdAt) {
        this.lectureId = lectureId;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public static LectureRegistrationEntity from(LectureRegistrationParams lectureRegistrationParams) {
        LectureRegistrationEntity lectureRegistrationEntity = new LectureRegistrationEntity();
        lectureRegistrationEntity.lectureId = lectureRegistrationParams.lectureId();
        lectureRegistrationEntity.userId = lectureRegistrationParams.userId();
        lectureRegistrationEntity.createdAt = lectureRegistrationParams.createdAt();
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
