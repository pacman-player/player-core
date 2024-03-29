package spring.app.dao.impl.dto;

import org.hibernate.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import spring.app.dao.abstraction.dto.SongCompilationDtoDao;
import spring.app.dto.SongCompilationDto;
import spring.app.dto.SongDto;
import spring.app.model.SongCompilation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class SongCompilationDtoDaoImpl implements SongCompilationDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SongDto> getSongsDtoBySongCompilation(String compilationName) {
        List<SongDto> list = entityManager.createQuery("SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, s.author.id, " +
                "g.name) FROM Song s JOIN s.songCompilations sc left JOIN s.author.genres g WHERE sc.name = :name", SongDto.class)
                .setParameter("name", compilationName)
                .getResultList();
        return list;
    }

    @Override
    public List<SongDto> getAllSongsWithCompId(long compilationID) {
        List<SongDto> list = entityManager.createQuery("SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, s.author.id, " +
                "g.name) FROM Song s JOIN s.songCompilations sc left JOIN s.author.genres g WHERE sc.id = :id", SongDto.class)
                .setParameter("id", compilationID)
                .getResultList();
        return list;
    }

    @Override
    public List<SongCompilationDto> getAllForAdmin() {
        List<SongCompilationDto> list = entityManager.createQuery("SELECT new spring.app.dto.SongCompilationDto(s.id, s.name, s.genre.name, s.cover)" +
                "FROM SongCompilation s" + "", SongCompilationDto.class).getResultList();
        return list;
    }

    @Override
    public List<SongCompilationDto> getAllCompilationsPlaylistByCompanyIdDto(Long id, String namePlayList) {
        List<SongCompilationDto> songCompilationList = entityManager.createQuery(
                "SELECT s.id, s.name, s.cover " +
                        ",g.name" +
                        ", son.name from Company c left join c." + namePlayList + " m left join m.songCompilation s join s.genre g left join s.song son where c.id=:id "

        ).setParameter("id", id)
                .unwrap(Query.class)
                .setResultTransformer(new SongCompilationDtoTransformer())
                .list();

        return songCompilationList;
    }


    @Override
    public SongCompilationDto getSongCompilationByIdDto(Long id) {
        List<SongCompilationDto> songCompilationDtoList = entityManager.createQuery(
                "SELECT s.id, s.name, s.cover " +
                        ",g.name" +
                        ", son.name  FROM SongCompilation s left join s.genre g left join s.song son where s.id=:id "
        ).setParameter("id", id)
                .unwrap(Query.class)
                .setResultTransformer(new SongCompilationDtoTransformer())
                .list();
        if (songCompilationDtoList.size() == 0) {
            return null;
        }
        return songCompilationDtoList.get(0);
    }

    @Override
    public List<SongCompilationDto> getListSongCompilationsByGenreIdDto(Long id) {
        List<SongCompilationDto> songCompilationDtoList = entityManager.createQuery(
                "SELECT s.id, s.name, s.cover " +
                        ",g.name" +
                        ", son.name  FROM SongCompilation s left join s.genre g left join s.song son where g.id=:id "
        ).setParameter("id", id)
                .unwrap(Query.class)
                .setResultTransformer(new SongCompilationDtoTransformer())
                .list();
        return songCompilationDtoList;
    }


    @Override
    public List<SongCompilationDto> getAllDto() {
        List<SongCompilationDto> songCompilationDtoList = entityManager.createQuery(
                "SELECT s.id, s.name, s.cover " +
                        ",g.name" +
                        ", son.name  FROM SongCompilation s left join s.genre g left join s.song son "
        ).unwrap(Query.class)
                .setResultTransformer(new SongCompilationDtoTransformer())
                .list();
        return songCompilationDtoList;
    }

    private static class SongCompilationDtoTransformer implements ResultTransformer {
        List<SongCompilationDto> root = new ArrayList<>();
        Map<Long, Set<String>> genreMap = new HashMap<>();
        Map<Long, Set<String>> songMap = new HashMap<>();

        @Override
        public Object transformTuple(Object[] tuple, String[] strings) {
            Long id = (Long) tuple[0];
            String name = (String) tuple[1];
            MultipartFile cover = (MultipartFile) tuple[2];
            String genreName = (String) tuple[3];
            String songName = (String) tuple[4];

            SongCompilationDto songCompilationDto = new SongCompilationDto(id, name, cover);

            if (!genreMap.containsKey(id)) {
                root.add(songCompilationDto);
                genreMap.put(id, new HashSet<>());
                songMap.put(id, new HashSet<>());
            }

            if (genreName != null) {
                genreMap.get(id).add(genreName);
            }
            if (songName != null) {
                songMap.get(id).add(songName);
            }
            return root;
        }

        @Override
        public List<SongCompilationDto> transformList(List list) {
            for (SongCompilationDto songCompilationDto : root) {
                songCompilationDto.setGenres(genreMap.get(songCompilationDto.getId()));
                songCompilationDto.setSongs(songMap.get(songCompilationDto.getId()));
            }
            return root;
        }
    }

    @Override
    public List<SongDto> getAvailableContentForCompilationDto(SongCompilation songCompilation) {
        List<SongDto> list = entityManager.createQuery("SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, s.author.id, " +
                "g.name) FROM Song s  left JOIN s.author.genres g WHERE g.id = :genreId AND s.isApproved = true AND s NOT IN " +
                "(SELECT s FROM Song s JOIN s.songCompilations sc WHERE sc.id = :compilationId )", SongDto.class)
                .setParameter("genreId", songCompilation.getGenre().getId())
                .setParameter("compilationId", songCompilation.getId())
                .getResultList();
        return list;
    }
}
