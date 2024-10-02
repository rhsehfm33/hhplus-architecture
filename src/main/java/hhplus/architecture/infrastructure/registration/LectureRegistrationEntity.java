package hhplus.architecture.infrastructure.registration;

import java.time.LocalDateTime;

import hhplus.architecture.domain.registration.LectureRegistration;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "lectures")
@NoArgsConstructor
public class LectureRegistrationEntity {
    @Id
    private long id;

    private long lectureId;

    private long userId;

    LocalDateTime createdAt;

    public static LectureRegistrationEntity from(LectureRegistrationParams lectureRegistrationParams) {
        LectureRegistrationEntity lectureRegistrationEntity = new LectureRegistrationEntity();
        lectureRegistrationEntity.lectureId = lectureRegistrationParams.lectureId();
        lectureRegistrationEntity.userId = lectureRegistrationParams.userId();
        lectureRegistrationEntity.createdAt = lectureRegistrationParams.createdAt();
        return lectureRegistrationEntity;
    }

    public static LectureRegistration to(LectureRegistrationEntity lectureRegistrationEntity) {
        return new LectureRegistration(
            lectureRegistrationEntity.getId(),
            lectureRegistrationEntity.getLectureId(),
            lectureRegistrationEntity.getUserId(),
            lectureRegistrationEntity.getCreatedAt()
        );
    }
}
