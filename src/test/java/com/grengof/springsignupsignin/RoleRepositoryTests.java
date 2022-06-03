package com.grengof.springsignupsignin;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
    
    @Autowired
    RoleRepository roleRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void testCreateRoles() {

        // Create some roles
        Role roleUser = new Role("User");
        Role roleAdmin = new Role("Admin");
        
        // Save roles
        roleRepo.save(roleUser);
        roleRepo.save(roleAdmin);

        // Assertions
        List<Role> roles = roleRepo.findAll();
        assertThat(roles.size()).isEqualTo(2);

    }

    @Test
    public void testAddRoleToNewUser() {
        
        // Find role
        Role roleAdmin = roleRepo.findByName("Admin");

        // Create new User
        User user = new User();

        user.setEmail("testAddRoleToNewUser@gmail.com");
        user.setUsername("test");
        user.setPassword("123456789");

        // Set role
        user.addRole(roleAdmin);

        // Save role
        User savedUser = userRepo.save(user);

        // Assertions
        assertThat(savedUser.getRoles().size()).isEqualTo(1);

    }

    @Test
    public void testAddRoleToExistingUser() {

        // Find role
        Role roleAdmin = roleRepo.findByName("Admin");

        // Find existing user by pk
        User user = userRepo.findById(1L).get();

        // Get old size of Set<Role>
        Integer oldSize = user.getRoles().size();

        // Add new role to user 
        user.addRole(roleAdmin);

        // Save user
        User savedUser = userRepo.save(user);

        // Get new size of Set<Role>
        Integer newSize = savedUser.getRoles().size();

        // Assertions
        assertThat(newSize).isEqualTo(oldSize + 1);

    }

}
