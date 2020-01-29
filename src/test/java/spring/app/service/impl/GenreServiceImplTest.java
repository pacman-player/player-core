package spring.app.service.impl;

import com.vladmihalcea.sql.SQLStatementCountValidator;
import com.vladmihalcea.sql.exception.SQLSelectCountMismatchException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import spring.app.service.abstraction.GenreService;
import spring.app.util.CrudInterceptor;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@ActiveProfiles(value = "application-test.yml")
//@TestPropertySource(locations="classpath:application-test.yml")
//@AutoConfigureMockMvc(addFilters = false)
public class GenreServiceImplTest {

    @Autowired
    private GenreService genreService;

    @Test
    public void contexLoads() throws Exception {
        assertThat(genreService).isNotNull();
    }

    @Test
    public void getAllGenreTest() throws Exception {
        CrudInterceptor.reset();
        genreService.getAllGenre();
        assertEquals(1,CrudInterceptor.getCount());
    }

    @Test
    public void getAllGenreDaoJoinTest() throws Exception {
        CrudInterceptor.reset();
        genreService.getAllGenreDaoJoin();
        assertEquals(1,CrudInterceptor.getCount());
    }

    @Test
    public void getAllGenreJoinTest() throws Exception {
        CrudInterceptor.reset();
        genreService.getAllGenreFetchModeJoin();
        assertEquals(1,CrudInterceptor.getCount());
    }

    @Test
    public void getAllGenreSubselectTest() throws Exception {
        CrudInterceptor.reset();
        genreService.getAllGenreFetchModeSubselect();
        assertEquals(2, CrudInterceptor.getCount());
    }

    @Test
    public void getAllGenreSubselectBatchTest() throws Exception {
        CrudInterceptor.reset();
        genreService.getAllGenreFetchModeSubselectBatch();
        assertEquals(3, CrudInterceptor.getCount());
    }

    @Test
    public void getAllGenreBatchTest() throws Exception {
        CrudInterceptor.reset();
        genreService.getAllGenreBatch();
        assertEquals(3, CrudInterceptor.getCount());
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
