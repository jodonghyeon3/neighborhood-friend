package com.jodonghyeon.neighborfriend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jodonghyeon.neighborfriend.domain.form.SignInForm;
import com.jodonghyeon.neighborfriend.domain.form.SignUpForm;
import com.jodonghyeon.neighborfriend.domain.type.Gender;
import com.jodonghyeon.neighborfriend.exception.CustomException;
import com.jodonghyeon.neighborfriend.exception.ErrorCode;
import com.jodonghyeon.neighborfriend.service.SignService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignController.class)
@MockBean(JpaMetamodelMappingContext.class)
class SignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignService signService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 성공")
    @WithMockUser
    void singUp() throws Exception {

        SignUpForm form = SignUpForm.builder()
                .name("조동일")
                .email("aa")
                .phone("010-3604-154")
                .age(26L)
                .gender(Gender.M)
                .password("1234")
                .birth(LocalDate.now())
                .build();

        mockMvc.perform(post("/user/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(form))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패 - userEmail 중복")
    @WithMockUser
    void signUp_Fail() throws Exception {
        SignUpForm form = SignUpForm.builder()
                .name("조동현")
                .email("jawoo100@naver.com")
                .phone("010-3604-1541")
                .age(26L)
                .gender(Gender.M)
                .password("1234")
                .birth(LocalDate.now())
                .build();

        doThrow(new CustomException(ErrorCode.ALREADY_REGISTER_USER)).when(signService).signUp(any());
        // 외부객체를 변환해주는 족에서 폼에 맞는 객체를 새로 생성해서 우리가 입력한 form이랑 새로 만든 form 이랑 객체가 달라서 에러가 발생하지않음
        mockMvc.perform(post("/user/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(form))
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그인 성공")
    @WithMockUser
    void login_success() throws Exception {
        SignInForm form = SignInForm.builder()
                .email("aaa")
                .password("1234")
                .build();

        when(signService.signIn(any())).thenReturn("token");
        mockMvc.perform(post("/user/signin")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(form))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 실패")
    @WithMockUser
    void login_fail() throws Exception {
        SignInForm form = SignInForm.builder()
                .email("aaa")
                .password("1234")
                .build();

        when(signService.signIn(any())).thenThrow(new CustomException(ErrorCode.NOT_FOUND_USER));

        mockMvc.perform(post("/user/signin")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(form))
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그인 실패 - 패스워드 틀림")
    @WithMockUser
    void login_fail2() throws Exception {
        SignInForm form = SignInForm.builder()
                .email("aaa")
                .password("1234")
                .build();

        when(signService.signIn(any())).thenThrow(new CustomException(ErrorCode.INVALID_PASSWORD));

        mockMvc.perform(post("/user/signin")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(form))
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }
}