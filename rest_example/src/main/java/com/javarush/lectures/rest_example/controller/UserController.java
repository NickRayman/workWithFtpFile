package com.javarush.lectures.rest_example.controller;

import com.javarush.lectures.rest_example.model.User;
import com.javarush.lectures.rest_example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @RestController — говорит спрингу, что данный класс
 * является REST контроллером. Т.е. в данном классе
 * будет реализована логика обработки клиентских запросов
 */
@RestController
/**
 * Класс UserController будет реализовывать логику
 * обработки клиентских запросов на эндпоинты (URI).
 */
public class UserController {

    private final UserService userService;

    /**
     * @param userService
     * @Autowired — говорит спрингу,
     * что в этом месте необходимо внедрить
     * зависимость. В конструктор мы передаем интерфейс UserService.
     * Реализацию данного сервиса мы пометили аннотацией @Service ранее,
     * и теперь спринг сможет передать экземпляр этой реализации в конструктор контроллера.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param user
     * @return
     * @PostMapping(value = "/user") — здесь мы обозначаем,
     * что данный метод обрабатывает POST запросы на адрес /user.
     * Метод возвращает ResponseEntity<?>. ResponseEntity — специальный класс для возврата ответов.
     * С помощью него мы сможем в дальнейшем вернуть клиенту HTTP статус код.
     * Метод принимает параметр @RequestBody User user,
     * значение этого параметра подставляется из тела запроса. Об этом говорит
     * аннотация  @RequestBody.
     */
    @PostMapping(value = "/user")
    public ResponseEntity<?> create(@RequestBody User user) {
        userService.create(user);
        /**
         * После чего возвращаем статус 201 Created, создав
         * новый объект ResponseEntity и передав в него нужное значение
         * енума HttpStatus.
         */
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Внутри метода, с помощью нашего сервиса мы получаем список всех клиентов.
     *
     * @return
     * @GetMapping(value = "/user") — все аналогично аннотации
     * @PostMapping, только теперь мы обрабатываем GET запросы.
     * На этот раз мы возвращаем ResponseEntity<List<Client>>, только в этот раз,
     * помимо HTTP статуса, мы вернем еще и тело ответа, которым будет список клиентов.
     */
    @GetMapping(value = "/user")
    public ResponseEntity<List<User>> read() {
        final List<User> users = userService.readAll();

        /**
         * В REST контроллерах спринга все POJO объекты,
         * а также коллекции POJO объектов, которые возвращаются
         * в качестве тел ответов, автоматически сериализуются в JSON,
         * если явно не указано иное. Нас это вполне устраивает.
         */
        if (users != null && !users.isEmpty())
            return new ResponseEntity<>(users, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Из нового, у нас тут появилась переменная пути.
     * Переменная, которая определена в URI. value = "/user/{id}".
     * Мы указали ее в фигурных скобках. А в параметрах метода принимаем её в качестве int переменной,
     * с помощью аннотации @PathVariable(name = "id").
     * Данный метод будет принимать запросы на uri вида /user/{id},
     * где вместо {id} может быть любое численное значение. Данное значение,
     * впоследствии, передается переменной int id — параметру метода.
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> read(@PathVariable(name = "id") int id) {
        final User user = userService.read(id);

        /**
         * В теле мы получаем объект User с помощью
         * нашего сервиса и принятого id. И далее, по аналогии
         * со списком, возвращаем либо статус 200 OK и сам объект
         * User, либо просто статус 404 Not Found, если клиента с
         * таким id не оказалось в системе.
         */
        if (user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Единственное, о чем стоит сказать: метод update обрабатывает
     * PUT запросы (аннотация @PutMapping), а метод delete
     * обрабатывает DELETE запросы (аннотация DeleteMapping).
     * @param id
     * @param user
     * @return
     */
    @PutMapping(value = "/user/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody User user) {
        final boolean updated = userService.update(user, id);

        if (updated)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = userService.delete(id);

        if (deleted)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


}
