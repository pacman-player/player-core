package spring.app.dto.mapping;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dto.AuthorDto;
import spring.app.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class AuthorDtoMapping {
    @PersistenceContext
    private EntityManager entityManager;

//        public List<AuthorDto> getAll(){
//    List<AuthorDto> authorDtos =entityManager.createQuery(
//                "SELECT new  spring.app.dto.AuthorDto(" +
//                        "a.id, " +
//                        "a.name,"+
//                        "a.genres,"+
//                        "a.isApproved"+") FROM " + Author.class.getName() + " a",
//                AuthorDto.class
//        )
//                .getResultList();
//        return authorDtos;
//    }

    public List<AuthorDto> getAll(){
        List<AuthorDto> authorDtos = entityManager.createNativeQuery()

        return authorDtos;
    }

}
