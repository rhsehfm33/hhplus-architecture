package hhplus.architecture.infrastructure.registration;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LectureRegistrationId implements Serializable {
    @Column(name = "lecture_id")
    private Long lectureId;

    @Column(name = "user_id")
    private Long userId;
}
