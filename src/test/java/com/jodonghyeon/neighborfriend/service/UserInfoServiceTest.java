package com.jodonghyeon.neighborfriend.service;

import com.jodonghyeon.neighborfriend.domain.dto.UserDto;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.repository.UserRepository;
import com.jodonghyeon.neighborfriend.exception.CustomException;
import com.jodonghyeon.neighborfriend.exception.ErrorCode;
import com.jodonghyeon.neighborfriend.geoLite2.GeoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;


// 서비스만 테스트 할경우 SpringBootTest어노테이션 필요없음 (붙이는 사람 시간많음)
@ExtendWith(MockitoExtension.class) // mockito에서 쓸수 있는 기능들 추가하는 어노테이션
class UserInfoServiceTest {

    @InjectMocks // 가짜로 만들어진 mock 들을 의존성 주입해줌
    private UserInfoService userInfoService;

    @Mock
    private UserRepository userRepository;

    @Mock // 가짜 객체
    private GeoService geoService;
    @Test
    @DisplayName("유저 정보 불러오기")
    void detail() {
        String email = "jawoo100@naver.com";

        User user = User.builder()
                .email("jawoo100@naver.com")
                .build();
        // given
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        // when
        UserDto detailUserInfo = userInfoService.getDetailUserInfo(email);

        // then
        assertEquals(detailUserInfo.getEmail(), email);
    }

    @Test
    @DisplayName("유저 정보 불러오기 실패 - 이메일 없음")
    void fail() {
        String email = "jawoo100@naver.com";

        //given
        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        //when
        CustomException customException = assertThrows(CustomException.class, () -> userInfoService.getDetailUserInfo(email));

        //then
        assertEquals(customException.getErrorCode(), ErrorCode.NOT_FOUND_USER);
    }
}