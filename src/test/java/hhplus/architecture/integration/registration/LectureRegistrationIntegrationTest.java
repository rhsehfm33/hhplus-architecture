package hhplus.architecture.integration.registration;

import static hhplus.architecture.domain.registration.LectureRegistrationService.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import hhplus.architecture.domain.lecture.LectureRepository;
import hhplus.architecture.domain.registration.LectureRegistrationRepository;
import hhplus.architecture.domain.registration.LectureRegistrationService;
import hhplus.architecture.domain.user.UserRepository;
import hhplus.architecture.infrastructure.lecture.LectureParams;
import hhplus.architecture.infrastructure.user.UserParams;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // test 메서드마다 db reset
public class LectureRegistrationIntegrationTest {
    @Autowired
    private LectureRegistrationService lectureRegistrationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private LectureRegistrationRepository lectureRegistrationRepository;

    @Test
    void registerLecture_31Times_30LectureRegistrations() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();

        int numThreads = MAX_REGISTRATION + 10;
        // DB 세팅
        for (int i = 1; i <= numThreads; i++) {
            userRepository.save(new UserParams(i, "user" + i + "@gmail.com", "password", "name" + i));
        }
        lectureRepository.save(new LectureParams(1, 1, "l1", now.minusDays(1), now.plusDays(1)));

        // 최대 + 1번 특강 신청
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        for (long i = 1; i <= numThreads; i++) {
            long finalI = i;
            executorService.submit(() -> {
                try {
                    lectureRegistrationService.registerLecture(1L, finalI);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        assertTrue(executorService.awaitTermination(1, TimeUnit.MINUTES));

        // 총 MAX_REGISTRATION 건의 특강 신청 목록이 조회 되어야 함
        assertEquals(MAX_REGISTRATION, lectureRegistrationRepository.countByLectureId(1L));
    }

    @Test
    void registerLecture_SameUser5Times_1SuccessRegistration() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();

        // DB 세팅
        userRepository.save(new UserParams(1, "user" + 1 + "@gmail.com", "password", "name" + 1));
        lectureRepository.save(new LectureParams(1, 1, "l1", now.minusDays(1), now.plusDays(1)));

        // 같은 유저 같은 특강 5번 신청
        int numThreads = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        // 메서드 성공, 실패 카운트 기록
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        // 특강 5번 신청
        for (int i = 0; i < numThreads; i++) {
            executorService.submit(() -> {
                try {
                    lectureRegistrationService.registerLecture(1L, 1L);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    e.printStackTrace();
                    failureCount.incrementAndGet();
                }
            });
        }
        executorService.shutdown();
        assertTrue(executorService.awaitTermination(1, TimeUnit.MINUTES));

        // 1건만 특강 신청 성공해야 함
        assertTrue(lectureRegistrationRepository.findByLectureIdAndUserId(1, 1).isPresent());
        assertEquals(1, successCount.get());
        assertEquals(4, failureCount.get());
    }

}
