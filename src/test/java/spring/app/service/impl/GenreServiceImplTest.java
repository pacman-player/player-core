package spring.app.service.impl;

import com.vladmihalcea.sql.SQLStatementCountValidator;
import com.vladmihalcea.sql.exception.SQLSelectCountMismatchException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring.app.controller.restController.GenreRestController;
import spring.app.model.Genre;
import spring.app.service.abstraction.GenreService;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GenreServiceImplTest {

    @Autowired
    private GenreService genreService;

    @Autowired
    private GenreRestController genreRestController;

    @Test
    public void contexLoads() throws Exception {
        assertThat(genreRestController).isNotNull();
    }

    @Test
    public void getAllGenreSqlCountTest() {
        Genre genre = new Genre("a1");
        genreService.addGenre(genre);
        try {
            SQLStatementCountValidator.reset();
            genreRestController.getAllGenre();
//            genreService.getAllGenre();
            assertSelectCount(1);
        } catch (SQLSelectCountMismatchException e) {
            assertEquals(3, e.getRecorded());
        }
    }

    @Test
    public void getAllGenreSqlCountJoinTest () {
        try {
            SQLStatementCountValidator.reset();
            genreService.getAllGenreFetchModeJoin();
            assertSelectCount(1);
        } catch (SQLSelectCountMismatchException e) {
            assertEquals(3, e.getRecorded());
        }
    }

    @Test
    public void deleteGenreSqlCountTest() {
        Genre genre = new Genre("a2");
        genreService.addGenre(genre);
        SQLStatementCountValidator.reset();
        genreService.deleteGenreById(1L);
        SQLStatementCountValidator.assertDeleteCount(1);
        assertEquals(3,2);
    }
}
