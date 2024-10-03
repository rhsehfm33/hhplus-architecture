package hhplus.architecture.domain.lecture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LectureServiceTest {
    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureService lectureService;

    // 더미 데이터
    private List<Lecture> lectures;

    // 특강 기간에 맞게 조회할 때만 lectures 반환
    @BeforeEach
    void setUp() {
        LocalDateTime time = LocalDateTime.now();

        lectures = new ArrayList<>();
        lectures.add(new Lecture(1L, 1L, "l1", "u1", time.minusMonths(30), time.plusMinutes(30)));
        when(lectureRepository.findLecturesContainingDateTime(any())).thenReturn(new ArrayList<>());
        lenient().when(lectureRepository.findLecturesContainingDateTime(argThat((LocalDateTime givenTime) ->
            !givenTime.isBefore(lectures.get(0).startTime()) &&
            !givenTime.isAfter(lectures.get(0).endTime())
        ))).thenReturn(lectures);
    }

    @Test
    public void getAvailableLectures_BeforeTime_EmptyList() {
        List<LectureInfo> lectureInfos = lectureService.getAvailableLectures(lectures.get(0).startTime().minusSeconds(1));
        assertTrue(lectureInfos.isEmpty());
    }

    @Test
    public void getAvailableLectures_AfterTime_EmptyList() {
        List<LectureInfo> lectureInfos = lectureService.getAvailableLectures(lectures.get(0).endTime().plusSeconds(1));
        assertTrue(lectureInfos.isEmpty());
    }

    @Test
    public void getAvailableLectures_StartTime_OneSizeList() {
        List<LectureInfo> lectureInfos = lectureService.getAvailableLectures(lectures.get(0).startTime());
        assertEquals(1, lectureInfos.size());
    }

    @Test
    public void getAvailableLectures_EndTime_OneSizeList() {
        List<LectureInfo> lectureInfos = lectureService.getAvailableLectures(lectures.get(0).endTime());
        assertEquals(1, lectureInfos.size());
    }
}
