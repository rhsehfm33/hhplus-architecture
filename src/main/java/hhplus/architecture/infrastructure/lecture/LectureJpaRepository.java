package hhplus.architecture.infrastructure.lecture;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureJpaRepository extends JpaRepository<LectureEntity, Long> {
}
