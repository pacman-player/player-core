package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.AuthorDao;
import spring.app.dao.abstraction.SongDao;
import spring.app.model.Author;
import spring.app.model.NotificationTemplate;
import spring.app.service.abstraction.AuthorService;
import spring.app.service.abstraction.NotificationService;
import spring.app.service.abstraction.NotificationTemplateService;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class AuthorServiceImpl extends AbstractServiceImpl<Author, AuthorDao> implements AuthorService {

    private SongDao songDao;
    private NotificationService notificationService;
    private NotificationTemplateService notificationTemplateService;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao, SongDao songDao, NotificationService notificationService, NotificationTemplateService notificationTemplateService) {
        super(authorDao);
        this.songDao = songDao;
        this.notificationService = notificationService;
        this.notificationTemplateService = notificationTemplateService;
    }

    @Override
    public void save(Author author) {
        dao.save(author);
        NotificationTemplate notificationTemplate = notificationTemplateService.getByName("default");

        try {
            notificationService.addNotification(author);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Когда удаляется Автор, удаляются все его песни
     */
    @Override
    public void deleteById(Long id) {
        // удаляем песни с данным автором
        songDao.bulkRemoveSongsByAuthorId(id);
        // теперь удаляем автора
        dao.deleteById(id);
    }

    @Override
    public Author getByName(String name) {
        return dao.getByName(name);
    }


    @Override
    public List<Author> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo) {
        return dao.getByCreatedDateRange(dateFrom, dateTo);
    }

    @Override
    public List<Author> getAllApprovedAuthors() {
        return dao.getAllApproved();
    }

    @Override
    public List<Author> getApprovedAuthorsPage(int pageNumber, int pageSize) {
        return dao.getApprovedPage(pageNumber, pageSize);
    }

    @Override
    public int getLastApprovedAuthorsPageNumber(int pageSize) {
        return dao.getLastApprovedPageNumber(pageSize);
    }

    @Override
    public boolean isExist(String name) {
        return dao.isExist(name);
    }

}