package org.kyle.arthas.demo.web;

import org.kyle.arthas.demo.service.UserServiceImpl;
import org.kyle.arthas.demo.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserServiceImpl userService;

    @GetMapping("/user/{id}")
    public User findUserById(@PathVariable Integer id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Find user by id: {}", id);
        }
        if (id == null) {
            throw new IllegalArgumentException("Id must not be  null");
        }
        if (id < 1) {
            // return new User(id, "name:" + id);
           throw new IllegalArgumentException("id < 1");
        }
        return new User(id, "name:" + id);
    }

    @GetMapping("/user/{id}/slow")
    public User findUserById2(@PathVariable Integer id) {
        try {
            userService.get(id);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        return new User(id, "name:" + id);
    }

}