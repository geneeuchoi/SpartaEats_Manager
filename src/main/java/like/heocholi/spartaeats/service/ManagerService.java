package like.heocholi.spartaeats.service;

import java.util.Optional;

import like.heocholi.spartaeats.constants.ErrorType;
import like.heocholi.spartaeats.exception.ManagerException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import like.heocholi.spartaeats.dto.SignupRequestDto;
import like.heocholi.spartaeats.entity.Manager;
import like.heocholi.spartaeats.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String signup(SignupRequestDto requestDto) {
        String userId = requestDto.getUserId();
        String password = requestDto.getPassword();

        Optional<Manager> checkUsername = managerRepository.findByUserId(userId);
        if (checkUsername.isPresent()) {
            throw new ManagerException(ErrorType.DUPLICATE_ACCOUNT_ID);
        }

        String encodedPassword = passwordEncoder.encode(password);

        Manager manager = new Manager(requestDto, encodedPassword);

        managerRepository.save(manager);

        return "회원가입 성공";
    }
}
