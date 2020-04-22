package spring.app.dto.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dto.AuthorDto;
import spring.app.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository
public class AuthorDtoDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<AuthorDto> getAll() {
        List<AuthorDto> authorDtos = entityManager.createQuery(
                "SELECT new spring.app.dto.AuthorDto(" +
                        "t.id, " +
                        "t.name, " +
                        "t.isApproved, "+
                        "t.genres "+
                        ") FROM "+ Author.class.getName()+" t",
                AuthorDto.class
        )
                .getResultList();

    return authorDtos;
    }

//    public List<AuthorDto> getAll() {
//        List<AuthorDto> authorDtos = entityManager.createNativeQuery(
//                "SELECT t.id AS authorId t.name t.isApproved a.id AS genreId, a.genre"+
//                        " FROM author t JOIN genre a on t.id = a.author_id)
//                .unwrap(SQLQuery.class)
//                .setResultTransformer(new AuthorDto())
//
//        return authorDtos;
//    }

    public boolean isExistByName(String name) {
        Query query = entityManager.createQuery(
                "SELECT new spring.app.dto.AuthorDto(" +
                        "a.name" +
                        ") FROM " + Author.class.getName() + " a WHERE a.name = :name",
                AuthorDto.class
        );
        query.setParameter("name", name);
        if ((query).getResultList().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
