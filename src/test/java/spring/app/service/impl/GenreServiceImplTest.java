package spring.app.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.UserService;
import spring.app.util.CrudInterceptor;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenreServiceImplTest {

    @Autowired
    private GenreService genreService;

    @Autowired
    private UserService userService;

    @Test
    public void contexLoads() throws Exception {
        assertThat(genreService).isNotNull();
    }

    @Test
    public void getAllUsersFetchModeJoinTest() throws Exception {
        CrudInterceptor.reset();
        System.out.println("Сбросил счётчик, счётчик = " + CrudInterceptor.getCount());
        userService.getAllUsersFetchModeJoin();
        System.out.println("Счётчик = " + CrudInterceptor.getCount());
        assertEquals(CrudInterceptor.getCount(),3);
    }

    @Test
    public void getAllGenreFetchModeJoinTest() throws Exception {
        CrudInterceptor.reset();
        System.out.println("Сбросил счётчик, счётчик = " + CrudInterceptor.getCount());
        genreService.getAllGenreFetchModeJoin();
        System.out.println("Счётчик = " + CrudInterceptor.getCount());
        assertEquals(CrudInterceptor.getCount(),5);
    }

    @Test
    public void getAllGenreTest() throws Exception {
        CrudInterceptor.reset();
        System.out.println("Сбросил счётчик, счётчик = " + CrudInterceptor.getCount());
        genreService.getAllGenre();
        System.out.println("Счётчик = " + CrudInterceptor.getCount());
        assertEquals(CrudInterceptor.getCount(),5);
    }

    @Test
    public void getGenreByIdFetchModeJoinTest() throws Exception {
        CrudInterceptor.reset();
        System.out.println("Сбросил счётчик, счётчик = " + CrudInterceptor.getCount());
        genreService.getByIdFetchModeJoin(1L);
        System.out.println("Счётчик = " + CrudInterceptor.getCount());
        assertEquals(CrudInterceptor.getCount(),1);
    }

    @Test
    public void getGenreByIdTest() throws Exception {
        CrudInterceptor.reset();
        System.out.println("Сбросил счётчик, счётчик = " + CrudInterceptor.getCount());
        genreService.getById(1L);
        System.out.println("Счётчик = " + CrudInterceptor.getCount());
        assertEquals(CrudInterceptor.getCount(),1);
    }
}
