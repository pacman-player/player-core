package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongCompilationDao;
import spring.app.model.*;
import spring.app.service.abstraction.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class SongCompilationServiceImpl implements SongCompilationService {
    private SongCompilationDao songCompilationDao;
    private UserService userService;
    private CompanyService companyService;
    private FileUploadService fileUploadService;
    private SendEmailAboutAddNewCompilationImpl sendEmail;
    private SongService songService;

    @Autowired
    public SongCompilationServiceImpl(SongCompilationDao songCompilationDao, UserService userService,
                                      CompanyService companyService, SendEmailAboutAddNewCompilationImpl sendEmail,
                                      @Lazy FileUploadService fileUploadService, @Lazy SongService songService) {
        this.songCompilationDao = songCompilationDao;
        this.userService = userService;
        this.companyService = companyService;
        this.sendEmail = sendEmail;
        this.fileUploadService = fileUploadService;
        this.songService = songService;
    }

    @Override
    public void addSongCompilation(SongCompilation songCompilation) {
        songCompilationDao.save(songCompilation);
//        sendEmail.send(songCompilation.getName());
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
    public List<Song> getAvailableSongsForCompilationById(Long compilationId) {
        return songCompilationDao.getAvailableContentForCompilation(songCompilationDao.getById(compilationId));
    }

    @Override
    public List<Song> getSongCompilationContentById(Long compilationId) {
        return songCompilationDao.getSongCompilationContentById(compilationId);
    }

    @Override
    public void addSongToSongCompilation(Long compilationId, Long songId) {
        songCompilationDao.addSongToSongCompilation(
                getSongCompilationById(compilationId),
                songService.getSongById(songId));
    }

    @Override
    public void removeSongFromSongCompilation(Long compilationId, Long songId) {
        songCompilationDao.removeSongFromSongCompilation(
                getSongCompilationById(compilationId),
                songService.getSongById(songId));
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
    public List<SongCompilation> getAllCompilationsInMorningPlaylistByCompanyId(Long id) {
//        //достаем юзера по id авторизованного юзера
//        User authUser = userService.getUserById(userService.getIdAuthUser());
//        //достаем множество утренних плейлистов
//        Company oldCompany = authUser.getCompany();

        Company oldCompany = companyService.getById(id);
        Set<PlayList> setOldMorningPlayList = oldCompany.getMorningPlayList();
        //достаем пока один единственный плейлист и его множество подборок
        List<PlayList> oldListPlayLists = new ArrayList<>(setOldMorningPlayList);
        PlayList oldPlayList = oldListPlayLists.get(0);
        Set<SongCompilation> oldSetSongCompilation = oldPlayList.getSongCompilation();
        List<SongCompilation> allCompilationsInMorningPlaylist = new ArrayList<>(oldSetSongCompilation);
        return allCompilationsInMorningPlaylist;
    }

    @Override
    public List<SongCompilation> getAllCompilationsInMiddayPlaylistByCompanyId(Long id) {
        Company oldCompany = companyService.getById(id);
        Set<PlayList> setOldMiddayPlayList = oldCompany.getMiddayPlayList();
        //достаем пока один единственный плейлист и его множество подборок
        List<PlayList> oldListPlayLists = new ArrayList<>(setOldMiddayPlayList);
        PlayList oldPlayList = oldListPlayLists.get(0);
        Set<SongCompilation> oldSetSongCompilation = oldPlayList.getSongCompilation();
        List<SongCompilation> allCompilationsInMiddayPlaylist = new ArrayList<>(oldSetSongCompilation);
        return allCompilationsInMiddayPlaylist;
    }

    @Override
    public List<SongCompilation> getAllCompilationsInEveningPlaylistByCompanyId(Long id) {
        Company oldCompany = companyService.getById(id);
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

    @Override
    public void deleteSongCompilationFromPlayList(Long id, String dayTime) {
        SongCompilation newSongCompilation = songCompilationDao.getById(id);
        //достаем юзера по id авторизованного юзера
        User authUser = userService.getUserById(userService.getIdAuthUser());
        //достаем множество утренних плейлистов
        Company oldCompany = authUser.getCompany();
        switch (dayTime) {
            case "morning":
                changeCompanyPlaylist(oldCompany.getMorningPlayList(), newSongCompilation);
                break;
            case "midday":
                changeCompanyPlaylist(oldCompany.getMiddayPlayList(), newSongCompilation);
                break;
            case "evening":
                changeCompanyPlaylist(oldCompany.getEveningPlayList(), newSongCompilation);
                break;
        }
        userService.updateUser(authUser);
    }

    private void changeCompanyPlaylist(Set<PlayList> playList, SongCompilation newSongCompilation) {
        PlayList pl = new ArrayList<>(playList).get(0);
        pl.getSongCompilation().remove(newSongCompilation);
    }

    @Override
    public void updateCompilation(SongCompilation songCompilation) {
        songCompilationDao.update(songCompilation);
    }

    @Override
    public void deleteSongCompilation(SongCompilation songCompilation) throws IOException {
        if (songCompilation.getCover() != null) {
            fileUploadService.eraseCurrentFile(songCompilation.getCover());
        }

        songCompilationDao.deleteById(songCompilation.getId());
    }
}
