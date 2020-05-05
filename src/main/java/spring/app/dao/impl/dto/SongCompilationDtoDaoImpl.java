package spring.app.dao.impl.dto;

import org.hibernate.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import spring.app.dao.abstraction.dto.SongCompilationDtoDao;
import spring.app.dto.SongCompilationDto;
import spring.app.dto.SongDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class SongCompilationDtoDaoImpl implements SongCompilationDtoDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<SongCompilationDto> getAllCompilationsPlaylistByCompanyIdDto(Long id,  String namePlayList) {
        List<SongCompilationDto> songCompilationList = entityManager.createQuery(
                "SELECT s.id, s.name, s.cover " +
                        ",g.name" +
                        ", son.name from Company c left join c."+namePlayList+" m left join m.songCompilation s join s.genre g left join s.song son where c.id=:id "

        ).setParameter("id", id)
                .unwrap(Query.class)
                .setResultTransformer(new SongCompilationDtoTransformer())
                .list();

        return songCompilationList;
    }


    @Override
    public List<SongDto> getSongsDtoBySongCompilation(String compilationName) {
        List<SongDto> songDtos = entityManager.createQuery(
                "SELECT o.id, o.name, o.author.name, o.genre.name, o.searchTags,o.createdAt,o.isApproved" +
                        "  FROM SongCompilation s  left join s.song o where s.name=:name "
        ).setParameter("name", compilationName)
                .unwrap(Query.class)
                .setResultTransformer(new SongCompilationDtoTransformerForSong())
                .list();

        return songDtos;
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

    private class SongCompilationDtoTransformer implements ResultTransformer {
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
        public List transformList(List list) {
            for (SongCompilationDto songCompilationDto : root) {
                songCompilationDto.setGenres(genreMap.get(songCompilationDto.getId()));
                songCompilationDto.setSongs(songMap.get(songCompilationDto.getId()));
            }
            return root;
        }
    }

    private class SongCompilationDtoTransformerForSong implements ResultTransformer {
        List<SongDto> root = new ArrayList<>();
        Map<Long, SongDto> songDtos = new HashMap<>();

        @Override
        public Object transformTuple(Object[] tuple, String[] strings) {
            Long id = (Long) tuple[0];
            String name = (String) tuple[1];
            String authorName = (String) tuple[2];
            String genreName = (String) tuple[3];
            String searchTags = (String) tuple[4];
            Timestamp createdAt = (Timestamp) tuple[5];
            Boolean isApproved = (Boolean) tuple[6];

            SongDto songDto = new SongDto(id, name, authorName, genreName, searchTags, createdAt, isApproved);
            if (!songDtos.containsKey(id) && isApproved) {
                songDtos.put(id, songDto);
                root.add(songDto);
            }
            return root;
        }

        @Override
        public List transformList(List list) {
            return root;
        }
    }
}
//        this.id = song.getId();
//                this.name = song.getName();
//                this.authorName = song.getAuthor().getName();
//                if (song.getGenre() == null) {// если у автора нет жанра (жанр был удален, например),
//                this.genreName = "";      // то возвращаем пустую строк иначе ошибка на фронте
//                } else {
//                this.genreName = song.getGenre().getName();
//                }
//                this.searchTags = song.getSearchTags();
//                this.createdAt = song.getCreatedAt();
//                this.isApproved = song.getApproved();


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


