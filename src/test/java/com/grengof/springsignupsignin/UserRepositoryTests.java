package com.grengof.springsignupsignin;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
 
    @Autowired
    private TestEntityManager entityManager;
     
    @Autowired
    private UserRepository userRepo;

    @Test
    public void testCreateUser() {
        
        User user = new User();
        user.setEmail("testCreateUser@gmail.com");
        user.setPassword("123456");
        user.setUsername("test");
        user.setCreatedAt(LocalDate.now());
         
        User savedUser = userRepo.save(user);
         
        User existUser = entityManager.find(User.class, savedUser.getId());
         
        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
        assertThat(user.getEmail()).isNotEmpty();

        assertThat(user.getUsername()).isEqualTo(existUser.getUsername());
        assertThat(user.getUsername()).isNotEmpty();

        assertThat(user.getPassword()).isEqualTo(existUser.getPassword());
        assertThat(user.getPassword()).isNotEmpty();
        
        assertThat(user.getCreatedAt()).isEqualTo(existUser.getCreatedAt());
        assertThat(user.getCreatedAt()).isToday();
         
    }
}