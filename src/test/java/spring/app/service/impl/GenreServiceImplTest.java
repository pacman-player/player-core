package spring.app.service.impl;

import com.vladmihalcea.sql.SQLStatementCountValidator;
import com.vladmihalcea.sql.exception.SQLSelectCountMismatchException;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.app.service.abstraction.GenreService;
import spring.app.util.CrudInterceptor;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenreServiceImplTest {

    @Autowired
    private GenreService genreService;

    @Test
    public void contexLoads() throws Exception {
        assertThat(genreService).isNotNull();
    }

    @Test
    public void getAllGenreJoinTest() throws Exception {
        CrudInterceptor.reset();
        genreService.getAllGenreFetchModeJoin();
        assertEquals(1,CrudInterceptor.getCount());
    }

    @Test
    @Fetch(FetchMode.SUBSELECT)
    public void getAllGenreSubselectTest() throws Exception {
        CrudInterceptor.reset();
        genreService.getAllGenre();
        assertEquals(1, CrudInterceptor.getCount());
    }

    @Test
    public void getAllGenreVladTest() throws Exception {
        try {
            SQLStatementCountValidator.reset();
            genreService.getAllGenre();
            assertSelectCount(1);
        } catch (SQLSelectCountMismatchException e) {
            assertEquals(1, e.getRecorded());
        }
    }

    @Test
    public void getGenreByIdJoinTest() throws Exception {
        CrudInterceptor.reset();
        genreService.getByIdFetchModeJoin(1L);
        assertEquals(1,CrudInterceptor.getCount());
    }

    @Test
    public void getGenreByIdTest() throws Exception {
        CrudInterceptor.reset();
        genreService.getById(1L);
        assertEquals(1, CrudInterceptor.getCount());
    }
}
