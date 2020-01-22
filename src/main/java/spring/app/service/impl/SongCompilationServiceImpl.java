package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongCompilationDao;
import spring.app.model.Company;
import spring.app.model.PlayList;
import spring.app.model.SongCompilation;
import spring.app.model.User;
import spring.app.service.abstraction.SongCompilationService;
import spring.app.service.abstraction.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class SongCompilationServiceImpl implements SongCompilationService {

    private SongCompilationDao songCompilationDao;
    private UserService userService;

    @Autowired
    public SongCompilationServiceImpl(SongCompilationDao songCompilationDao, UserService userService) {
        this.songCompilationDao = songCompilationDao;
        this.userService = userService;
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
    public void deleteValByGenreId(Long id) {
        songCompilationDao.deleteValByGenreId(id);
    }

    @Override
    public void addSongCompilationToMorningPlaylist(Long id) {
        SongCompilation newSongCompilation = songCompilationDao.getById(id);
        //достаем юзера по id авторизованного юзера
        User authUser = userService.getUserById(userService.getIdAuthUser());
        //достаем множество утренних плейлистов
        Company oldCompany = authUser.getCompany();
        Set<PlayList> setOldMorningPlayList = oldCompany.getMorningPlayList();
        //достаем пока один единственный плейлист и его множество подборок
        List<PlayList> oldListPlayLists = new ArrayList<>(setOldMorningPlayList);
        PlayList oldPlayList = oldListPlayLists.get(0);
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
        authUser.setCompany(oldCompany);
        //обновляем юзера
        userService.updateUser(authUser);

    }

    @Override
    public void addSongCompilationToMiddayPlaylist(Long id) {
        SongCompilation newSongCompilation = songCompilationDao.getById(id);
        //достаем юзера по id авторизованного юзера
        User authUser = userService.getUserById(userService.getIdAuthUser());
        //достаем множество дневных плейлистов
        Company oldCompany = authUser.getCompany();
        Set<PlayList> setOldMiddayPlayList = oldCompany.getMiddayPlayList();
        //достаем пока один единственный плейлист и его множество подборок
        List<PlayList> oldListPlayLists = new ArrayList<>(setOldMiddayPlayList);
        PlayList oldPlayList = oldListPlayLists.get(0);
        Set<SongCompilation> oldSetSongCompilation = oldPlayList.getSongCompilation();
        //добавляем новую подборку в старое множество подборок
        oldSetSongCompilation.add(newSongCompilation);
        //добавляем в старый плейлист обновленное старое множество подборок
        oldPlayList.setSongCompilation(oldSetSongCompilation);
        //добавляем в старое множество плейлистов обновленное множество плейлистов
        setOldMiddayPlayList.add(oldPlayList);
        //добавляем компании обновленные дневные плейлисты
        oldCompany.setMiddayPlayList(setOldMiddayPlayList);
        //добавляем юзеру обновленную компанию
        authUser.setCompany(oldCompany);
        //обновляем юзера
        userService.updateUser(authUser);
    }

    @Override
    public void addSongCompilationToEveningPlaylist(Long id) {
        SongCompilation newSongCompilation = songCompilationDao.getById(id);
        //достаем юзера по id авторизованного юзера
        User authUser = userService.getUserById(userService.getIdAuthUser());
        //достаем множество вечерних плейлистов
        Company oldCompany = authUser.getCompany();
        Set<PlayList> setOldEveningPlayList = oldCompany.getEveningPlayList();
        //достаем пока один единственный плейлист и его множество подборок
        List<PlayList> oldListPlayLists = new ArrayList<>(setOldEveningPlayList);
        PlayList oldPlayList = oldListPlayLists.get(0);
        Set<SongCompilation> oldSetSongCompilation = oldPlayList.getSongCompilation();
        //добавляем новую подборку в старое множество подборок
        oldSetSongCompilation.add(newSongCompilation);
        //добавляем в старый плейлист обновленное старое множество подборок
        oldPlayList.setSongCompilation(oldSetSongCompilation);
        //добавляем в старое множество плейлистов обновленное множество плейлистов
        setOldEveningPlayList.add(oldPlayList);
        //добавляем компании обновленные вечерние плейлисты
        oldCompany.setEveningPlayList(setOldEveningPlayList);
        //добавляем юзеру обновленную компанию
        authUser.setCompany(oldCompany);
        //обновляем юзера
        userService.updateUser(authUser);
    }

    @Override
    public List<SongCompilation> getAllCompilationsInMorningPlaylist() {
        //достаем юзера по id авторизованного юзера
        User authUser = userService.getUserById(userService.getIdAuthUser());
        //достаем множество утренних плейлистов
        Company oldCompany = authUser.getCompany();
        Set<PlayList> setOldMorningPlayList = oldCompany.getMorningPlayList();
        //достаем пока один единственный плейлист и его множество подборок
        List<PlayList> oldListPlayLists = new ArrayList<>(setOldMorningPlayList);
        PlayList oldPlayList = oldListPlayLists.get(0);
        Set<SongCompilation> oldSetSongCompilation = oldPlayList.getSongCompilation();
        List<SongCompilation> allCompilationsInMorningPlaylist = new ArrayList<>(oldSetSongCompilation);
        return allCompilationsInMorningPlaylist;
    }

    @Override
    public List<SongCompilation> getAllCompilationsInMiddayPlaylist() {
        //достаем юзера
        User authUser = userService.getUserById(userService.getIdAuthUser());
        //достаем множество дневных плейлистов
        Company oldCompany = authUser.getCompany();
        Set<PlayList> setOldMiddayPlayList = oldCompany.getMiddayPlayList();
        //достаем пока один единственный плейлист и его множество подборок
        List<PlayList> oldListPlayLists = new ArrayList<>(setOldMiddayPlayList);
        PlayList oldPlayList = oldListPlayLists.get(0);
        Set<SongCompilation> oldSetSongCompilation = oldPlayList.getSongCompilation();
        List<SongCompilation> allCompilationsInMiddayPlaylist = new ArrayList<>(oldSetSongCompilation);
        return allCompilationsInMiddayPlaylist;
    }

    @Override
    public List<SongCompilation> getAllCompilationsInEveningPlaylist() {
        //достаем юзера
        User authUser = userService.getUserById(userService.getIdAuthUser());
        //достаем множество вечерних плейлистов юзера
        Company oldCompany = authUser.getCompany();
        Set<PlayList> setOldEveningPlayList = oldCompany.getEveningPlayList();
        //достаем пока один единственный плейлист и его множество подборок
        List<PlayList> oldListPlayLists = new ArrayList<>(setOldEveningPlayList);
        PlayList oldPlayList = oldListPlayLists.get(0);
        Set<SongCompilation> oldSetSongCompilation = oldPlayList.getSongCompilation();
        List<SongCompilation> allCompilationsInEveningPlaylist = new ArrayList<>(oldSetSongCompilation);
        return allCompilationsInEveningPlaylist;
    }

    @Override
    public SongCompilation getSongCompilationById(Long id) {
        return songCompilationDao.getById(id);
    }

    @Override
    public SongCompilation getSongCompilationByCompilationName(String compilationName) {
        return songCompilationDao.getSongCompilationByCompilationName(compilationName);
    }
}
