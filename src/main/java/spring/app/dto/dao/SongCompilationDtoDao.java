package spring.app.dto.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dto.SongCompilationDto;
import spring.app.model.SongCompilation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SongCompilationDtoDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<SongCompilationDto> getAll() {
        List<SongCompilationDto> songCompilationDto = entityManager.createQuery(
                "SELECT new spring.app.dto.SongCompilationDto(" +
                        "t.id, " +
                        "t.name, " +
                        "t.genre, "+
                        "t.cover "+
                        ") FROM "+ SongCompilation.class.getName()+" t",
                spring.app.dto.SongCompilationDto.class
        )
                .getResultList();

        List<Long> ids = songCompilationDto.stream().mapToLong(SongCompilationDto::getId).boxed().collect(Collectors.toList());

//        List<SongDto> songs = entityManager.createQuery(
//                "SELECT new spring.app.dto.SongDto(a.id, a.name, a.authorName, a.genreName, a.createdAt, a.isApproved)" +
//                        " FROM Song a WHERE a.task.id IN (:ids)",
//                AnswerDto.class
//        )
//                .setParameter("ids", ids)
//                .getResultList();

        return songCompilationDto;
    }

}
