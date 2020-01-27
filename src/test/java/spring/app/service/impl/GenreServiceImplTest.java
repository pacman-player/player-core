package spring.app.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.app.service.abstraction.GenreService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenreServiceImplTest {

    @Autowired
    private GenreService genreService;

    @Test
    public void contexLoads() throws Exception {
        assertThat(genreService).isNotNull();
    }
}
