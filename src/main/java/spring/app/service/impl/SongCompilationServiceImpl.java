package spring.app.service.impl;

import com.sun.security.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.PlayListDao;
import spring.app.dao.abstraction.SongCompilationDao;
import spring.app.dao.abstraction.UserDao;
import spring.app.model.Company;
import spring.app.model.PlayList;
import spring.app.model.SongCompilation;
import spring.app.model.User;
import spring.app.service.abstraction.SongCompilationService;

import java.util.*;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Transactional
@Service
public class SongCompilationServiceImpl implements SongCompilationService {

    private SongCompilationDao songCompilationDao;
    private PlayListDao playListDao;
    private UserDao userDao;

    @Autowired
    public SongCompilationServiceImpl(SongCompilationDao songCompilationDao, PlayListDao playListDao, UserDao userDao) {
        this.songCompilationDao = songCompilationDao;
        this.playListDao = playListDao;
        this.userDao = userDao;
    }

    @Override
    public void addSongСompilation(SongCompilation songCompilation) {
        songCompilationDao.save(songCompilation);
    }

    @Override
    public List<SongCompilation> getAllSongCompilations() {
        return songCompilationDao.getAll();
    }

    @Override
    public List<SongCompilation> getListSongCompilationsByGenreId(Long id) {
        return songCompilationDao.getListSongCompilationsByGenreId(id);
    }

    @Override
    public void addSongCompilationToMorningPlaylist(Long id) {
        SongCompilation newSongCompilation = songCompilationDao.getById(id);

        //достаем юзера
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authUser = (User) principal;
        Long idAuthUser = authUser.getId();
        User user = userDao.getById(idAuthUser);

        //достаем множество утренних плейлистов
        Company oldCompany = user.getCompany();
        Set<PlayList> setOldMorningPlayList = oldCompany.getMorningPlayList();
        int sizePlayList = setOldMorningPlayList.size();

        //достаем пока один единственный плейлист и его множество подборок
        List<PlayList> oldListPlayLists = new ArrayList<>(setOldMorningPlayList);
        PlayList oldPlayList = oldListPlayLists.get(0);
        String namePlayList = oldPlayList.getName();
        Set<SongCompilation> oldSetSongCompilation = oldPlayList.getSongCompilation();

        //добавляем новую подборку в старое множество подборок
        oldSetSongCompilation.add(newSongCompilation);

        //добавляем в старый плейлист обновленное старое множество подборок
        oldPlayList.setSongCompilation(oldSetSongCompilation);

        //добавляем в старое множество плейлистов обновленное множество плейлистов
        setOldMorningPlayList.add(oldPlayList);

        //добавляем компании обновленные утренние плейлисты
        oldCompany.setMorningPlayList(setOldMorningPlayList);

        //добавляем юзеру обновленную компанию
        user.setCompany(oldCompany);

        //обновляем юзера
        userDao.update(user);

//        songCompilationDao.addSongCompilationToMorningPlaylist(id);
    }

    @Override
    public List<SongCompilation> getAllCompilationsInMorningPlaylist() {
        //достаем юзера
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authUser = (User) principal;
        Long idAuthUser = authUser.getId();
        User user = userDao.getById(idAuthUser);

        //достаем множество утренних плейлистов
        Company oldCompany = user.getCompany();
        Set<PlayList> setOldMorningPlayList = oldCompany.getMorningPlayList();

        //достаем пока один единственный плейлист и его множество подборок
        List<PlayList> oldListPlayLists = new ArrayList<>(setOldMorningPlayList);
        PlayList oldPlayList = oldListPlayLists.get(0);
        Set<SongCompilation> oldSetSongCompilation = oldPlayList.getSongCompilation();

        List<SongCompilation> allCompilationsInMorningPlaylist = new ArrayList<>(oldSetSongCompilation);

        return allCompilationsInMorningPlaylist;
    }

    @Override
    public SongCompilation getSongCompilationById(Long id) {
        return songCompilationDao.getById(id);
    }
}
