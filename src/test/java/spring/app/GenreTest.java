package spring.app;

import com.vladmihalcea.sql.SQLStatementCountValidator;
import com.vladmihalcea.sql.exception.SQLSelectCountMismatchException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.app.controller.restController.GenreRestController;
import spring.app.service.abstraction.GenreService;
import spring.app.util.HibernateInterceptor;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenreTest {
    @Autowired
    private GenreRestController genreRestController;

    @Autowired
    private GenreService genreService;

    @Test
    public void test() throws Exception {
        assertThat(genreRestController).isNotNull();
    }

    @Test
    public void nPlusOneWithoutFetchModeJoinTest() {
        try {
            SQLStatementCountValidator.reset();
            HibernateInterceptor.startCounting();
            genreService.getAllGenre();
            HibernateInterceptor.stopCounting();
            HibernateInterceptor.getQueryCounter();
            assertSelectCount(1);
        } catch (SQLSelectCountMismatchException e) {
            assertEquals(3, e.getRecorded());
        }
    }

    @Test
    public void nPlusOneWithFetchModeJoinTest () {
        try {
            SQLStatementCountValidator.reset();
            genreService.getAllGenreFetchModeJoin();
            assertSelectCount(1);
        } catch (SQLSelectCountMismatchException e) {
            assertEquals(3, e.getRecorded());
        }

    }
}
