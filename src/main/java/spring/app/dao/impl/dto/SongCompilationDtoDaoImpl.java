package spring.app.dao.impl.dto;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.dto.SongCompilationDtoDao;
import spring.app.dto.SongDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SongCompilationDtoDaoImpl implements SongCompilationDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SongDto> getAllSongsWithCompId(long compilationID) {
        List<SongDto> list = entityManager.createQuery("SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, " +
                "s.genre.name) FROM Song s JOIN s.songCompilations sc WHERE sc.id = :id", SongDto.class)
                .setParameter("id", compilationID)
                .getResultList();
        return list;
    }

    //TODO: implement
//    @Override
//    public List<SongCompilationDto> getAll() {
//        List<SongCompilationDto> songCompilationDto = entityManager.createQuery(
//                "SELECT new spring.app.dto.SongCompilationDto(" +
//                        "t.id, " +
//                        "t.name, " +
//                        "t.genre, "+
//                        "t.cover "+
//                        ") FROM "+ SongCompilation.class.getName()+" t",
//                spring.app.dto.SongCompilationDto.class
//        )
//                .getResultList();
//
//        List<Long> ids = songCompilationDto.stream().mapToLong(SongCompilationDto::getId).boxed().collect(Collectors.toList());
//
////        List<SongDto> songs = entityManager.createQuery(
////                "SELECT new spring.app.dto.SongDto(a.id, a.name, a.authorName, a.genreName, a.createdAt, a.isApproved)" +
////                        " FROM Song a WHERE a.task.id IN (:ids)",
////                AnswerDto.class
////        )
////                .setParameter("ids", ids)
////                .getResultList();
//
//        return songCompilationDto;
//    }

}
