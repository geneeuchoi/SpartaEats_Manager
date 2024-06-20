package like.heocholi.spartaeats.service;

import java.util.Optional;

import like.heocholi.spartaeats.constants.ErrorType;
import like.heocholi.spartaeats.constants.UserStatus;
import like.heocholi.spartaeats.dto.SignupResponseDto;
import like.heocholi.spartaeats.dto.WithdrawRequestDto;
import like.heocholi.spartaeats.entity.Customer;
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
    public SignupResponseDto signup(SignupRequestDto requestDto) {
        String userId = requestDto.getUserId();
        String password = requestDto.getPassword();

        Optional<Manager> checkUsername = managerRepository.findByUserId(userId);
        if (checkUsername.isPresent()) {
            throw new ManagerException(ErrorType.DUPLICATE_ACCOUNT_ID);
        }

        String encodedPassword = passwordEncoder.encode(password);

        Manager manager = new Manager(requestDto, encodedPassword);

        managerRepository.save(manager);

        return new SignupResponseDto(manager);
    }

    @Transactional
    public String logout(String userId) {
        // 유저 확인
        Manager manager = this.findByUserId(userId);

        manager.removeRefreshToken();

        return manager.getUserId();
    }

    @Transactional
    public String withdrawManager(WithdrawRequestDto requestDto, String userId) {
        // 유저 확인
        Manager manager = this.findByUserId(userId);
        // 이미 탈퇴한 회원인지 확인
        if(manager.getUserStatus().equals(UserStatus.DEACTIVATE)){
            throw new ManagerException(ErrorType.DEACTIVATE_USER);
        }
        // 비밀번호 확인
        if(!passwordEncoder.matches(requestDto.getPassword(), manager.getPassword())){
            throw new ManagerException(ErrorType.INVALID_PASSWORD);
        }

        manager.withdrawManager();

        return manager.getUserId();
    }

    private Manager findByUserId(String userId){
        return managerRepository.findByUserId(userId).orElseThrow(()-> new ManagerException(ErrorType.NOT_FOUND_USER));
    }
}
