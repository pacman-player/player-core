package spring.app.dao.impl;

import com.vladmihalcea.sql.SQLStatementCountValidator;
import com.vladmihalcea.sql.exception.SQLSelectCountMismatchException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.app.dao.abstraction.GenreDao;
import spring.app.util.CrudInterceptor;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenreDaoImplTest {
    @Autowired
    private GenreDao genreDao;

    @Test
    public void getAll() {
        CrudInterceptor.reset();
        genreDao.getAll();
        assertEquals(1, CrudInterceptor.getCount());
    }

    @Test
    public void getAllDaoJoin() {
        CrudInterceptor.reset();
        genreDao.getAllDaoJoin();
        assertEquals(1, CrudInterceptor.getCount());
    }
/*
    Тест с использованием библиотеки Влад Михалыча. Не считает запросы!!!
    оставил для примера.
 */
    @Test
    public void getAllGenreVladTest() throws Exception {
        try {
            SQLStatementCountValidator.reset();
            genreDao.getAll();
            assertSelectCount(1);
        } catch (SQLSelectCountMismatchException e) {
            assertEquals(1, e.getRecorded());
        }
    }
}