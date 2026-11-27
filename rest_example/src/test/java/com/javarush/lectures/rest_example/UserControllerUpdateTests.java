package com.javarush.lectures.rest_example;

import com.javarush.lectures.rest_example.controller.UserController;
import com.javarush.lectures.rest_example.model.User;
import com.javarush.lectures.rest_example.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class UserControllerUpdateTests {

    @Test
    void reportsSuccessWhenReplacingAnExistingUser() {
        UserServiceImpl service = new UserServiceImpl();
        UserController controller = new UserController(service);
        User original = new User();
        service.create(original);
        int id = original.getId();

        try {
            User replacement = new User();
            assertEquals(HttpStatus.OK, controller.update(id, replacement).getStatusCode());
            assertSame(replacement, service.read(id));
            assertEquals(id, replacement.getId().intValue());
        } finally {
            service.delete(id);
        }

        assertEquals(HttpStatus.NOT_MODIFIED, controller.update(id, new User()).getStatusCode());
    }
}
