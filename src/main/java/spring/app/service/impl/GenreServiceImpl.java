package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.GenreDao;
import spring.app.dao.abstraction.dto.GenreDtoDao;
import spring.app.dto.GenreDto;
import spring.app.model.Author;
import spring.app.model.Genre;
import spring.app.model.Song;
import spring.app.model.SongCompilation;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.SongCompilationService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class GenreServiceImpl extends AbstractServiceImpl<Long, Genre, GenreDao> implements GenreService {

    private final GenreDtoDao genreDtoDao;
    private final SongCompilationService songCompilationService;

    @Autowired
    public GenreServiceImpl(GenreDao dao, GenreDtoDao genreDtoDao, SongCompilationService songCompilationService) {
        super(dao);
        this.genreDtoDao = genreDtoDao;
        this.songCompilationService = songCompilationService;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Genre notDefinedGenre = getByName("not defined");
        Genre genreForDelete = getById(id);
        // в сервисе только таким образом можно пробежать по всем авторам , удалить у них наш жанр и в случае если жанров у них больше нет добавить "не определенный"
        List<Author> authors = new ArrayList<>(genreForDelete.getAuthors());
        for (Author author : authors) {
            author.getAuthorGenres().remove(genreForDelete);
            if (author.getAuthorGenres().size() == 0) {
                author.getAuthorGenres().add(notDefinedGenre);
            }
        }

        List<Song> songs = dao.getSongsByGenre(genreForDelete);
        for (Song song : songs) {
            song.setGenre(notDefinedGenre);
        }
        dao.deleteReferenceFromOrgTypeByGenre(genreForDelete);
        dao.deleteReferenceFromCompanyByGenre(genreForDelete);

        List<SongCompilation> songCompilationList = new ArrayList<>(genreForDelete.getSongCompilation());
        for (SongCompilation songCompilation : songCompilationList) {
            songCompilation.setGenre(notDefinedGenre);
            genreForDelete.getSongCompilation().remove(songCompilation);
        }
        dao.flush();
        dao.deleteById(id);
    }

    @Override
    public Genre getByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public List<Genre> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo) {
        return dao.getByCreatedDateRange(dateFrom, dateTo);
    }

    @Override
    public List<GenreDto> getAllGenreDto() {
        return genreDtoDao.getAll();
    }

    @Override
    public List<Genre> getAllApprovedGenre() {
        return dao.getAllApproved();
    }

    @Override
    public boolean isExistByName(String name) {
        return genreDtoDao.isExistByName(name);
    }
}