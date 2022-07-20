package com.javarush.lectures.rest_example.service;

import com.javarush.lectures.rest_example.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс UserServiceImpl реализует интерфейс UserService
 * с CRUD методами.
 */
@Service
/**
 * Аннотация @Service говорит спрингу,
 * что данный класс является сервисом. Это специальный тип классов,
 * в котором реализуется некоторая бизнес логика приложения. Впоследствии,
 * благодаря этой аннотации Spring будет предоставлять нам экземпляр данного класса
 * в местах, где это, нужно с помощью Dependency Injection.
 */
public class UserServiceImpl implements UserService {

    // Хранилище клиентов
    private static final Map<Integer, User> USER_REPOSITORY_MAP = new HashMap<>();

    // Переменная для генерации ID клиента
    private static final AtomicInteger USER_ID_HOLDER = new AtomicInteger();

    @Override
    public void create(User user) {
        final int userId = USER_ID_HOLDER.incrementAndGet();
        user.setId(userId);
        USER_REPOSITORY_MAP.put(userId, user);
    }

    @Override
    public List<User> readAll() {
        return new ArrayList<>(USER_REPOSITORY_MAP.values());
    }

    @Override
    public User read(int employmentNumber) {
        return USER_REPOSITORY_MAP.get(employmentNumber);
    }

    @Override
    public boolean update(User user, int id) {
        if (USER_REPOSITORY_MAP.containsKey(id)) {
            user.setId(id);
            USER_REPOSITORY_MAP.put(id, user);
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        return USER_REPOSITORY_MAP.remove(id) != null;
    }
}
