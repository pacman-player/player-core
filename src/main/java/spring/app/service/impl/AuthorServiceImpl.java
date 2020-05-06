package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.AuthorDao;
import spring.app.dao.abstraction.SongDao;
import spring.app.dao.abstraction.dto.AuthorDtoDao;
import spring.app.dto.AuthorDto;
import spring.app.model.Author;
import spring.app.model.NotificationTemplate;
import spring.app.service.abstraction.AuthorService;
import spring.app.service.abstraction.NotificationService;
import spring.app.service.abstraction.NotificationTemplateService;
import spring.app.service.abstraction.SongFileService;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

@Service
public class AuthorServiceImpl extends AbstractServiceImpl<Long, Author, AuthorDao> implements AuthorService {
    private final AuthorDtoDao authorDtoDao;
    private SongDao songDao;
    private NotificationService notificationService;
    private NotificationTemplateService notificationTemplateService;
    private final SongFileService songFileService;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao, AuthorDtoDao authorDtoDao, SongDao songDao, NotificationService notificationService, NotificationTemplateService notificationTemplateService, SongFileService songFileService) {
        super(authorDao);
        this.authorDtoDao = authorDtoDao;
        this.songDao = songDao;
        this.notificationService = notificationService;
        this.notificationTemplateService = notificationTemplateService;
        this.songFileService = songFileService;
    }


    @Override
    @Transactional
    public void save(Author author) {
        dao.save(author);
        NotificationTemplate notificationTemplate = notificationTemplateService.getByName("default");

        try {
            notificationService.save(author);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Когда удаляется Автор, удаляются все его песни
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        Author author = dao.getById(id);
        // удаляем песни с данным автором
        songDao.bulkRemoveSongsByAuthorId(id);
        // теперь удаляем автора
        dao.deleteById(id);
        // удаляем папку с автором и всеми песнями физически
        songFileService.deleteSongFile(author);
    }

    @Override
    public Author getByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public List<AuthorDto> findAuthorsByNameContaining (String name){
        return authorDtoDao.findByNameContaining(name);
    }

    @Override
    public List<Author> getByCreatedDateRange (Timestamp dateFrom, Timestamp dateTo){
        return dao.getByCreatedDateRange(dateFrom, dateTo);
    }

    @Override
    public List<AuthorDto> getAllAuthors () {
        return authorDtoDao.getAllAuthors();
    }

    @Override
    public List<AuthorDto> getAllApprovedAuthors () {
        return authorDtoDao.getAllApproved();

    }

    @Override
    public List<Author> getApprovedAuthorsPage ( int pageNumber, int pageSize){
        return dao.getApprovedPage(pageNumber, pageSize);
    }

    @Override
    public int getLastApprovedAuthorsPageNumber ( int pageSize){
        return dao.getLastApprovedPageNumber(pageSize);
    }

    @Override
    public boolean isExist (String name){
        return dao.isExist(name);
    }

}