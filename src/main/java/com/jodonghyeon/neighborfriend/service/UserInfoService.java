package com.jodonghyeon.neighborfriend.service;

import com.jodonghyeon.neighborfriend.domain.dto.UserDto;
import com.jodonghyeon.neighborfriend.domain.model.Address;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.repository.UserRepository;
import com.jodonghyeon.neighborfriend.exception.CustomException;
import com.jodonghyeon.neighborfriend.exception.ErrorCode;
import com.jodonghyeon.neighborfriend.geoLite2.GeoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.UnknownHostException;

@Service
@RequiredArgsConstructor
@Transactional
public class UserInfoService {

    private final GeoService geoService;
    private final UserRepository userRepository;

    public UserDto getDetailUserInfo(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );
        return UserDto.from(user);
    }

    public void addFirstAddress(String email) throws UnknownHostException {
        User u = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );

        Address city = geoService.findCity();
        u.setFirstAddress(city);
    }

    public void addSecondAddress(String email) throws UnknownHostException {
        User u = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );

        Address city = geoService.findCity();
        u.setSecondAddress(city);

    }
}
