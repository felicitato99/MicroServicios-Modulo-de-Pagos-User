package com.accenture.modulosPago;

import com.accenture.modulosPago.entities.User;
import com.accenture.modulosPago.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void existUsers(){
        List<User> users = userRepository.findAll();
        assertThat(users,is(not(empty())));
    }
}
