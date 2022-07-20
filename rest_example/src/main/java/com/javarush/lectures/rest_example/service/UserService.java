package com.javarush.lectures.rest_example.service;

import com.javarush.lectures.rest_example.model.User;

import java.util.List;

/**
 * Интерфейс UserService является сервисом, в котором будут реализованы
 * CRUD операции.
 */
public interface UserService {
    /**
     * Создает нового клиента
     *
     * @param user - клиент для создания
     */
    void create(User user);

    /**
     * Возвращает список всех имеющихся user'ов
     *
     * @return список клиентов
     */
    List<User> readAll();

    /**
     * Возвращает user'a по его ID
     *
     * @param id - ID клиента
     * @return - объект клиента с заданным ID
     */
    User read(int id);

    /**
     * Обновляет user'а с заданным ID,
     * в соответствии с переданным клиентом
     *
     * @param user             - user в соответсвии с которым нужно обновить данные
     * @param employmentNumber - id user'a которого нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    boolean update(User user, int employmentNumber);

    /**
     * Удаляет user'а с заданным ID
     *
     * @param id - id клиента, которого нужно удалить
     * @return - true если user был удален, иначе false
     */
    boolean delete(int id);
}
