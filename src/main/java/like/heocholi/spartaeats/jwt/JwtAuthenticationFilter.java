package like.heocholi.spartaeats.jwt;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import like.heocholi.spartaeats.constants.ErrorType;
import like.heocholi.spartaeats.constants.UserStatus;
import like.heocholi.spartaeats.dto.ErrorMessage;
import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.exception.ManagerException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import like.heocholi.spartaeats.constants.UserRole;
import like.heocholi.spartaeats.dto.LoginRequestDto;
import like.heocholi.spartaeats.entity.Manager;
import like.heocholi.spartaeats.repository.ManagerRepository;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final ManagerRepository managerRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, ManagerRepository managerRepository) {
        this.jwtUtil = jwtUtil;
        this.managerRepository = managerRepository;
        setFilterProcessesUrl("/managers/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUserId(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    // 로그인 성공시 처리
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        Manager manager = ((UserDetailsImpl) authResult.getPrincipal()).getManager();
        if(manager.getUserStatus().equals(UserStatus.DEACTIVATE)){
            throw new ManagerException(ErrorType.NOT_FOUND_USER);
        }

        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRole role = ((UserDetailsImpl) authResult.getPrincipal()).getManager().getRole();

        String accessToken = jwtUtil.createAccessToken(username, role);
        String refreshToken = jwtUtil.createRefreshToken(username, role);

        manager.saveRefreshToken(refreshToken.substring(7));
        managerRepository.save(manager);

        response.addHeader(JwtUtil.AUTH_ACCESS_HEADER, accessToken);
        response.addHeader(JwtUtil.AUTH_REFRESH_HEADER, refreshToken);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(ResponseMessage.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("로그인 성공")
                .data(username)
                .build())
        );
        response.getWriter().flush();
    }

    // 로그인 실패시 처리
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        ErrorType errorType = ErrorType.NOT_FOUND_AUTHENTICATION_INFO;
        response.setStatus(errorType.getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(ErrorMessage.<String>builder()
                .statusCode(errorType.getHttpStatus().value())
                .message(errorType.getMessage())
                .build())
        );
        response.getWriter().flush();
    }
}
