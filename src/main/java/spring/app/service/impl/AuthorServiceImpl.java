package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.AuthorDao;
import spring.app.model.Author;
import spring.app.model.NotificationTemplate;
import spring.app.service.abstraction.AuthorService;
import spring.app.service.abstraction.NotificationService;
import spring.app.service.abstraction.NotificationTemplateService;

import java.util.List;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;
    private NotificationService notificationService;
    private NotificationTemplateService notificationTemplateService;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao, NotificationService notificationService, NotificationTemplateService notificationTemplateService) {
        this.authorDao = authorDao;
        this.notificationService = notificationService;
        this.notificationTemplateService = notificationTemplateService;
    }

    @Override
    public List<Author> getAllAuthor() {
        return authorDao.getAll();
    }

    @Override
    public void addAuthor(Author author) {
        authorDao.save(author);
        NotificationTemplate notificationTemplate = notificationTemplateService.getById(1L);
        String link = " <a href=\"performers\">ссылке</a>";
        if (notificationTemplate == null) {
            notificationTemplate = new NotificationTemplate();
            notificationTemplate.setTemplate("Был добавлен новый автор {author}, нужно проверить жанры по {link}");
            notificationTemplateService.create(notificationTemplate);
        }

        String notification = notificationTemplateService.getById(1L).getTemplate();

        notification = notification.replace("{author}", author.getName());
        notification = notification.replace("{link}", link);

        try {
            notificationService.addNotification(notification);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Author getByName(String name) {
        return authorDao.getByName(name);
    }

    @Override
    public List<Author> findAuthorsByNameContaining(String name) {
        return authorDao.findByNameContaining(name);
    }

    @Override
    public Author getById(long authorsId) {
        return authorDao.getById(authorsId);
    }

    @Override
    public void updateAuthor(Author author) {
        authorDao.update(author);
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorDao.deleteById(id);
    }

    @Override
    public boolean isExist(String name) {
        return authorDao.isExist(name);
    }

}