package spring.app.service.impl;

import com.vladmihalcea.sql.SQLStatementCountValidator;
import com.vladmihalcea.sql.exception.SQLSelectCountMismatchException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.app.service.abstraction.SongService;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SongServiceImplTest {
    @Autowired
    private SongService songService;

    @Test
    public void getAllSongWithoutFetchModeJoin() {
        try {
            SQLStatementCountValidator.reset();
            songService.getAllSong();
            assertSelectCount(1);
        } catch (SQLSelectCountMismatchException e) {
            assertEquals(3, e.getRecorded());
        }
    }

    @Test
    public void getAllSongWithFetchModeJoin() {
        try {
            SQLStatementCountValidator.reset();
            songService.getAllSongFetchModeJoin();
            assertSelectCount(1);
        } catch (SQLSelectCountMismatchException e) {
            assertEquals(3, e.getRecorded());
        }
    }
}
