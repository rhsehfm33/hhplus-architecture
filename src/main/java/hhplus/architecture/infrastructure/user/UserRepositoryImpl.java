package hhplus.architecture.infrastructure.user;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import hhplus.architecture.domain.user.User;
import hhplus.architecture.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User saveUser(UserParams userParams) {
        UserEntity userEntity = UserEntity.from(userParams);

        userEntity = userJpaRepository.save(userEntity);

        return UserEntity.to(userEntity);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        Optional<UserEntity> optionalUserEntity = userJpaRepository.findById(id);
        if (optionalUserEntity.isPresent()) {
            User user = UserEntity.to(optionalUserEntity.get());
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }
}
