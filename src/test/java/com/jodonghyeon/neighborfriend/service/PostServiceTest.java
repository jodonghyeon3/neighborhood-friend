package com.jodonghyeon.neighborfriend.service;

import com.jodonghyeon.neighborfriend.domain.dto.PostDto;
import com.jodonghyeon.neighborfriend.domain.form.PostForm;
import com.jodonghyeon.neighborfriend.domain.model.Address;
import com.jodonghyeon.neighborfriend.domain.model.Post;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.repository.PostRepository;
import com.jodonghyeon.neighborfriend.domain.repository.UserRepository;
import com.jodonghyeon.neighborfriend.domain.type.AddressType;
import com.jodonghyeon.neighborfriend.domain.type.Gender;
import com.jodonghyeon.neighborfriend.domain.type.PostStatus;
import com.jodonghyeon.neighborfriend.exception.CustomException;
import com.jodonghyeon.neighborfriend.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

// private 메서드 리플렉션이용하면 테스트가
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Spy
    @InjectMocks
    private PostService postService;

    @Test
    @DisplayName("게시글 등록")
    void addPostTest() {
// given
        String email = "jawoo1003@gmail.com";
        AddressType addressType = AddressType.FIRSTADDRESS;

        Address address = new Address("상도동", 101.11, 11.11);
        User user = User.builder()
                .email(email)
                .age(17L)
                .birth(LocalDate.now())
                .gender(Gender.M)
                .phone("010-3604-1541")
                .firstAddress(address)
                .build();
        PostForm post = PostForm.builder()
                .title("제목")
                .detail("내용")
                .place("홍대")
                .time(LocalDateTime.now())
                .build();

        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));
        // when
        postService.addAddressPost(post, email, addressType);
        // then

        // Verify
        verify(postService, times(1))
                .addAddressPost(post, email, addressType);
    }

    @Test
    @DisplayName("게시글 등록 - 유저 주소 등록안함")
    void addPostFailNotFoundAddress() {
        //given
        String email = "jawoo1003@gmail.com";
        AddressType addressType = AddressType.FIRSTADDRESS;
        Address firstAddress = new Address();
        User user = User.builder()
                .email(email)
                .age(17L)
                .birth(LocalDate.now())
                .gender(Gender.M)
                .phone("010-3604-1541")
                .build();
        PostForm post = PostForm.builder()
                .title("제목")
                .detail("내용")
                .place("홍대")
                .time(LocalDateTime.now())
                .build();

        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));
        //when
        CustomException customException = assertThrows(CustomException.class,
                () -> postService.addAddressPost(post, email, addressType));

        //then
        assertEquals(customException.getErrorCode(), ErrorCode.NOT_FOUND_ADDRESS);
        verify(postService, times(1))
                .addAddressPost(post, email, addressType);
    }

    @Test
    @DisplayName("게시글 불러오기")
    void getListPostTest() {
        //given 주어진 것들 셋팅
        Address address = Address.builder()
                .address("상도")
                .lat(11.12)
                .lon(11.13)
                .build();

        User user = User.builder()
                .email("jawoo1003@gmail.com")
                .age(17L)
                .birth(LocalDate.now())
                .gender(Gender.M)
                .phone("010-3604-1541")
                .firstAddress(address)
                .build();

        List<Post> postList = List.of(
                Post.builder()
                        .address(address)
                        .status(PostStatus.RECRUITING)
                        .build(),
                Post.builder()
                        .address(address)
                        .status(PostStatus.RECRUITMENT_COMPLETE)
                        .build()
        );
        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));
        when(postRepository.findByAddress(any()))
                .thenReturn(postList);

        //when 실제 로직 수행
        List<PostDto> listAddressPost = postService.getListAddressPost(user.getEmail(), AddressType.FIRSTADDRESS);

        //then 검증
        assertThat(listAddressPost.size()).isEqualTo(1);
        assertThat(listAddressPost.get(0).getStatus()).isEqualTo(PostStatus.RECRUITING);
    }

    @Test
    @DisplayName("게시글 불러오기 - 주소 없음")
    void getListPostAddressFailNotFoundAddress() {
        //given 주어진 것들 셋팅
        Address address = Address.builder()
                .address("상도")
                .lat(11.12)
                .lon(11.13)
                .build();

        User user = User.builder()
                .email("jawoo1003@gmail.com")
                .age(17L)
                .birth(LocalDate.now())
                .gender(Gender.M)
                .phone("010-3604-1541")
                .firstAddress(address)
                .build();

        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));

        //when 실제 로직 수행
        CustomException customException = assertThrows(CustomException.class,
                () -> postService.getListAddressPost(user.getEmail(), AddressType.FIRSTADDRESS));
        assertEquals(customException.getErrorCode(), ErrorCode.NOT_FOUND_ADDRESS);

        //then
        verify(postService, times(1))
                .getListAddressPost(user.getEmail(), AddressType.FIRSTADDRESS);

    }

    @Test
    @DisplayName("게시글 상태 수정")
    void modifyPostStatusSuccess() {
        //given
        String email = "jawoo1003@gmail.com";

        Address address = new Address("상도동", 101.11, 11.11);

        User user = User.builder()
                .id(1L)
                .email(email)
                .age(17L)
                .birth(LocalDate.now())
                .gender(Gender.M)
                .phone("010-3604-1541")
                .firstAddress(address)
                .build();
        Post post = Post.builder()
                .id(1L)
                .user(user)
                .status(PostStatus.RECRUITING)
                .build();

        when(userRepository.findByEmail(post.getUser().getEmail())).thenReturn(Optional.of(post.getUser()));
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        //when

        postService.modifyPostStatus(post.getId(), user.getEmail());

        verify(postService, times(1))
                .modifyPostStatus(post.getId(), email);
    }

    @Test
    @DisplayName("게시글 상태 수정 - 권한 없음")
    void modifyPostStatusNotPermittedConnect() {
        //given
        String email = "jawoo1003@gmail.com";

        Address address = new Address("상도동", 101.11, 11.11);

        User user = User.builder()
                .id(1L)
                .email(email)
                .age(17L)
                .birth(LocalDate.now())
                .gender(Gender.M)
                .phone("010-3604-1541")
                .firstAddress(address)
                .build();
        User user2 = User.builder()
                .id(1L)
                .email("jaw1")
                .age(17L)
                .birth(LocalDate.now())
                .gender(Gender.M)
                .phone("010-3604-1541")
                .firstAddress(address)
                .build();
        Post post = Post.builder()
                .id(1L)
                .user(user2)
                .status(PostStatus.RECRUITING)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        //when
        CustomException customException = assertThrows(CustomException.class,
                () -> postService.modifyPostStatus(post.getId(), user.getEmail()));
        assertEquals(customException.getErrorCode(), ErrorCode.NOT_PERMITTED_CONNECT);

        verify(postRepository, times(1)).findById(post.getId());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    @DisplayName("게시글 수정")
    void modifyPostSuccess() {
        //given
        String email = "jawoo1003@gmail.com";

        Address address = new Address("상도동", 101.11, 11.11);

        User user = User.builder()
                .id(1L)
                .email(email)
                .age(17L)
                .birth(LocalDate.now())
                .gender(Gender.M)
                .phone("010-3604-1541")
                .firstAddress(address)
                .build();
        User user2 = User.builder()
                .id(1L)
                .email("jaw1")
                .age(17L)
                .birth(LocalDate.now())
                .gender(Gender.M)
                .phone("010-3604-1541")
                .firstAddress(address)
                .build();
        Post post = Post.builder()
                .id(1L)
                .user(user2)
                .status(PostStatus.RECRUITING)
                .build();

        PostForm form = PostForm.builder()
                .title("제목바꿈")
                .detail("내용바꿈")
                .build();
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        //when
        postService.modifyPost(user.getEmail(), post.getId(), form);

        //then
        verify(postRepository, times(1)).findById(post.getId());
    }

    @Test
    @DisplayName("게시글 상태 수정 - 권한 없음")
    void modifyPostNotPermittedConnect() {
        //given
        String email = "jawoo1003@gmail.com";

        Address address = new Address("상도동", 101.11, 11.11);

        User user = User.builder()
                .id(1L)
                .email(email)
                .age(17L)
                .birth(LocalDate.now())
                .gender(Gender.M)
                .phone("010-3604-1541")
                .firstAddress(address)
                .build();
        User user2 = User.builder()
                .id(1L)
                .email("jaw1")
                .age(17L)
                .birth(LocalDate.now())
                .gender(Gender.M)
                .phone("010-3604-1541")
                .firstAddress(address)
                .build();
        Post post = Post.builder()
                .id(1L)
                .user(user2)
                .status(PostStatus.RECRUITING)
                .build();
        PostForm form = PostForm.builder()
                .title("제목바꿈")
                .detail("내용바꿈")
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        //when
        CustomException customException = assertThrows(CustomException.class,
                () -> postService.modifyPost(user.getEmail(), post.getId(), form));
        assertEquals(customException.getErrorCode(), ErrorCode.NOT_PERMITTED_CONNECT);

        verify(postRepository, times(1)).findById(post.getId());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    @DisplayName("게시글 삭제")
    void removePostSuccess() {
        //given
        String email = "jawoo1003@gmail.com";
        Address address = new Address("상도동", 101.11, 11.11);
        User user = User.builder()
                .id(1L)
                .email(email)
                .age(17L)
                .birth(LocalDate.now())
                .gender(Gender.M)
                .phone("010-3604-1541")
                .firstAddress(address)
                .build();
        Post post = Post.builder()
                .id(1L)
                .user(user)
                .status(PostStatus.RECRUITING)
                .build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        //when
        postService.removePost(post.getUser().getEmail(), post.getId());

        //then
        verify(userRepository, times(1)).findByEmail(post.getUser().getEmail());
        verify(postRepository, times(1)).findById(post.getId());
        verify(postService, times(1)).removePost(post.getUser().getEmail(), post.getId());
    }

    @Test
    @DisplayName("게시글 삭제 - 권한 없음")
    void removePostNotPermitted() {
        //given
        String email = "jawoo1003@gmail.com";
        Address address = new Address("상도동", 101.11, 11.11);
        User user = User.builder()
                .id(1L)
                .email(email)
                .age(17L)
                .birth(LocalDate.now())
                .gender(Gender.M)
                .phone("010-3604-1541")
                .firstAddress(address)
                .build();
        User user2 = User.builder()
                .id(1L)
                .email("jaw1003@gmail.com")
                .age(17L)
                .birth(LocalDate.now())
                .gender(Gender.M)
                .phone("010-3604-1541")
                .firstAddress(address)
                .build();
        Post post = Post.builder()
                .id(1L)
                .user(user2)
                .status(PostStatus.RECRUITING)
                .build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        //when
        CustomException customException = assertThrows(CustomException.class,
                () -> postService.removePost(user.getEmail(), post.getId()));
        assertEquals(customException.getErrorCode(), ErrorCode.NOT_PERMITTED_CONNECT);

        //then
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(postRepository, times(1)).findById(post.getId());
        verify(postService, times(1)).removePost(user.getEmail(), post.getId());
    }
}