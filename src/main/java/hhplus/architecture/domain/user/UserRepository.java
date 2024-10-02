package hhplus.architecture.domain.user;

import java.util.Optional;

import hhplus.architecture.infrastructure.user.UserParams;

public interface UserRepository {
    User saveUser(UserParams user);
    Optional<User> findUserById(Long id);
}