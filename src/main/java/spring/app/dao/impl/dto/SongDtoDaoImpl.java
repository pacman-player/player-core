package spring.app.dao.impl.dto;

import org.hibernate.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.dto.SongDtoDao;
import spring.app.dto.SongDto;
import spring.app.dto.SongDtoTop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SongDtoDaoImpl implements SongDtoDao {

    @PersistenceContext
    private EntityManager entityManager;
    final private int numberOfSegments = 10;
    private long[] segment;
    private long idSongs;

    @Override
    public List<SongDto> getAll() {
        List<SongDto> songDtos = entityManager.createQuery(
                "SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, s.genre.name) FROM Song s",
                SongDto.class
        )
                .getResultList();

        return songDtos;
    }

    @Override
    public List<SongDtoTop> getTopSongsByNumberOfList(Timestamp startTime, Timestamp endTime) {
        List<SongDtoTop> songDtos = entityManager.createQuery(
                "SELECT s.id , s.name , s.author.name " +
                        "FROM OrderSong o LEFT JOIN o.song s where o.timestamp > :startTime  and o.timestamp < :endTime  "
        )
                .unwrap(Query.class)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime)
                .setResultTransformer(new SongDtoTopListTransformer())
                .list();

        return songDtos;
    }

    private class SongDtoTopListTransformer implements ResultTransformer {
        private List<SongDtoTop> roots = new ArrayList<>();
        private Map<Long, Long> idMap = new HashMap<>();

        @Override
        public Object transformTuple(Object[] tuple, String[] strings) {
            long songId = (long) tuple[0];
            String name = (String) tuple[1];
            String authorName = (String) tuple[2];

            SongDtoTop songDtoTop = new SongDtoTop(songId, name, authorName);

            if (!idMap.containsKey(songId)) {
                roots.add(songDtoTop);
                idMap.put(songId, 0L);
            }

            idMap.put(songId, (idMap.get(songId) + 1L));
            return songDtoTop;
        }

        @Override
        public List transformList(List list) {
            for (SongDtoTop songDtoTop : roots) {
                Long amount = idMap.get(songDtoTop.getId());
                songDtoTop.setAmount(amount);
            }

            return roots;
        }
    }

    @Override
    public SongDtoTop getSongDtoTopWithPoint(Timestamp startTime, Timestamp endTime, Long idSong) {
        segment = getSegments(startTime, endTime);
        idSongs = idSong;
        List<SongDtoTop> songDtos = entityManager.createQuery(
                "SELECT s.id , s.name , o.timestamp " +
                        "FROM OrderSong  o  LEFT JOIN o.song s where  o.timestamp BETWEEN :startTime AND :endTime "
        )
                .unwrap(Query.class)
                //      .setParameter("idSong", idSong)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime)

                .setResultTransformer(new SongDtoForGraficTransformer())
                .list();

        return songDtos.get(0);
    }

    private class SongDtoForGraficTransformer implements ResultTransformer {
        private List<SongDtoTop> roots = new ArrayList<>();
        private int[][] point = new int[2][numberOfSegments];

        @Override
        public Object transformTuple(Object[] tuple, String[] strings) {
            long songId = (long) tuple[0];
            String name = (String) tuple[1];
            Timestamp timestamp = (Timestamp) tuple[2];
            Long curTime = timestamp.getTime();
            SongDtoTop songDtoTop = new SongDtoTop(songId, name);

            if (songId == idSongs) {


                if (roots.size() == 0) {
                    roots.add(songDtoTop);
                    // 0-ось y кол-во заказанных песен в данном отрезке
                    // 1-ось x длина отрезка
                    songDtoTop.setPoint(new int[2][numberOfSegments]);
                }
                for (int i = 0; i < numberOfSegments - 1; i++) {
                    if (curTime < segment[i + 1]) {
                        point[0][i]++;
                        break;
                    }
                }
            }
            return songDtoTop;
        }

        @Override
        public List transformList(List list) {
            roots.get(0).setPoint(point);
            return roots;
        }


    }

    public long[] getSegments(Timestamp startTime, Timestamp endTime) {
        long[] ans = new long[numberOfSegments];
        long start = startTime.getTime();
        long end = endTime.getTime();
        // кол-во отрезков на 1 меньше количества точек
        long part = (end - start) / (numberOfSegments - 1);
        ans[0] = start;
        for (int i = 1; i < numberOfSegments; i++) {
            ans[i] = ans[i - 1] + part;
        }

        ans[numberOfSegments - 1] = end + 100;
        return ans;
    }
}
