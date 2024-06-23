package my.unishop.user.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.unishop.user.domain.member.dto.MemberRequestDto;
import my.unishop.user.domain.member.dto.MemberResponseDto;
import my.unishop.user.domain.member.dto.VerifyMailRequest;
import my.unishop.user.domain.member.entity.UserRole;
import my.unishop.user.domain.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MemberApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MemberService memberService;

    @MockBean
    private JavaMailSender javaMailSender;

    @InjectMocks
    private MemberApiController memberController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원가입 테스트")
    void signup() {
        MemberRequestDto memberRequestDto =
                new MemberRequestDto("test", "1234", "010-1234-5678", "abc@naver.com", "Seoul", "1234", "1234", UserRole.USER);

        doNothing().when(memberService).signup(any(MemberRequestDto.class));

        ResponseEntity<?> response = memberController.signup(memberRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    void updateMember() {
        MemberRequestDto memberRequestDto =
                new MemberRequestDto("test", "1234", "010-1234-5678", "abc@naver.com", "Seoul", "1234", "1234", UserRole.USER);

        Principal principal = () -> "test";

        when(memberService.updateMember(any(String.class), any(MemberRequestDto.class)))
                .thenReturn(new MemberResponseDto("abc@naver.com", "test", "Busan"));

        ResponseEntity<?> response = memberController.updateMember(principal, memberRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}