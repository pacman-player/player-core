package spring.app.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.app.service.abstraction.UserService;
import spring.app.util.CrudInterceptor;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void contexLoads() throws Exception {
        assertThat(userService).isNotNull();
    }

    @Test
    public void getAllUserTest() throws Exception {
        CrudInterceptor.reset();
        userService.getAllUsers();
        assertEquals(11, CrudInterceptor.getCount());
    }

    @Test
    public void getAllUserDaoJoinTest() throws Exception {
        CrudInterceptor.reset();
        userService.getAllUsersDaoJoin();
        assertEquals(1, CrudInterceptor.getCount());
    }

    @Test
    public void getAllUserJoinTest() throws Exception {
        CrudInterceptor.reset();
        userService.getAllUsersFetchModeJoin();
        assertEquals(1, CrudInterceptor.getCount());
    }

    @Test
    public void getAllUserSubselectTest() throws Exception {
        CrudInterceptor.reset();
        userService.getAllUsersFetchModeSubselect();
        assertEquals(2, CrudInterceptor.getCount());
    }

    @Test
    public void getAllUserSubselectBatchTest() throws Exception {
        CrudInterceptor.reset();
        userService.getAllUsersFetchModeSubselectBatch();
        assertEquals(3, CrudInterceptor.getCount());
    }

    /*
    Этот тест пройден, хотя в консоле 2 запроса Hibernate при 1 от CrudInterceptor
    В model заменил EAGER на LAZY у поля Roles
     */
    @Test
    public void getUserByIdTest() throws Exception {
        CrudInterceptor.reset();
        userService.getUserById(1L);
        assertEquals(1, CrudInterceptor.getCount());
    }
}