package hhplus.architecture.infrastructure.user;

import hhplus.architecture.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class UserEntity {
    @Id
    private Long id;

    private String email;

    private String password;

    private String name;

    public static UserEntity from(UserParams userParams) {
        UserEntity user = new UserEntity();
        user.email = userParams.email();
        user.password = userParams.password();
        user.name = userParams.name();
        return user;
    }

    public static User to(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getEmail(), userEntity.getPassword(), userEntity.getName());
    }
}
