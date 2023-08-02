package com.jodonghyeon.neighborfriend.service;

import com.jodonghyeon.neighborfriend.domain.model.Address;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.repository.UserRepository;
import com.jodonghyeon.neighborfriend.geoLite2.GeoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.UnknownHostException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserInfoService {

    private final GeoService geoService;
    private final UserRepository userRepository;
    public Optional<User> findByIdAndEmail(Long id, String email) {
        return userRepository.findById(id).stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    public String addFirstAddress(Long id, String email) throws UnknownHostException {
        User u = userRepository.findById(id).stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().get();

        Address city = geoService.findCity();
        u.setFirstAddress(city);
        return "주소를 정상적으로 등록하였습니다.";


    }

    public String addSecondAddress(Long id, String email) throws UnknownHostException {
        User u = userRepository.findById(id).stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().get();

        Address city = geoService.findCity();
        u.setSecondAddress(city);

        return "주소를 정상적으로 등록하였습니다.";
    }
}
