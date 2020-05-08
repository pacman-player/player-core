package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongCompilationDao;
import spring.app.dao.abstraction.dto.SongCompilationDtoDao;
import spring.app.dto.SongCompilationDto;
import spring.app.dto.SongDto;
import spring.app.model.*;
import spring.app.service.abstraction.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SongCompilationServiceImpl extends AbstractServiceImpl<Long, SongCompilation, SongCompilationDao> implements SongCompilationService {
    private SongCompilationDtoDao songCompilationDtoDao;
    private UserService userService;
    private CompanyService companyService;
    private FileUploadService fileUploadService;
    private SendEmailAboutAddNewCompilationImpl sendEmail;
    private SongService songService;
    final private SongCompilationDtoDao songCompilationDto;

    @Autowired
    public SongCompilationServiceImpl(SongCompilationDao dao, SongCompilationDtoDao songCompilationDtoDao, UserService userService,
                                      CompanyService companyService, SendEmailAboutAddNewCompilationImpl sendEmail,
                                      @Lazy FileUploadService fileUploadService, @Lazy SongService songService, SongCompilationDtoDao songCompilationDto) {
        super(dao);
        this.songCompilationDtoDao = songCompilationDtoDao;
        this.userService = userService;
        this.companyService = companyService;
        this.sendEmail = sendEmail;
        this.fileUploadService = fileUploadService;
        this.songService = songService;
        this.songCompilationDto = songCompilationDto;
    }

    @Override
    @Transactional
    public void save(SongCompilation songCompilation) {
        dao.save(songCompilation);
//        sendEmail.send(songCompilation.getName());
    }

    @Override
    public List<SongCompilationDto> getAllSongCompilationDto() {
        return songCompilationDtoDao.getAllForAdmin();
    }

    @Override
    public List<SongCompilation> getListSongCompilationsByGenreId(Long id) {
        return dao.getListSongCompilationsByGenreId(id);
    }

    @Override
    public List<Song> getAvailableSongsForCompilationById(Long compilationId) {
        return dao.getAvailableContentForCompilation(dao.getById(compilationId));
    }

    @Override
    public List<SongDto> getSongCompilationContentById(Long compilationId) {
        return songCompilationDtoDao.getAllSongsWithCompId(compilationId);
    }

    @Override
    public void addSongToSongCompilation(Long compilationId, Long songId) {
        dao.addSongToSongCompilation(
                getSongCompilationById(compilationId),
                songService.getById(songId));
    }


    @Override
    public void removeSongFromSongCompilation(Long compilationId, Long songId) {
        dao.removeSongFromSongCompilation(
                getSongCompilationById(compilationId),
                songService.getById(songId));
    }

    @Override
    public void deleteValByGenreId(Long id) {
        dao.deleteValByGenreId(id);
    }

    @Override
    @Transactional
    public void addSongCompilationToMorningPlaylist(Long id) {
        SongCompilation newSongCompilation = dao.getById(id);
        //достаем юзера по id авторизованного юзера
        User authUser = userService.getById(userService.getIdAuthUser());
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
        userService.update(authUser);

    }

    @Override
    @Transactional
    public void addSongCompilationToMiddayPlaylist(Long id) {
        SongCompilation newSongCompilation = dao.getById(id);
        //достаем юзера по id авторизованного юзера
        User authUser = userService.getById(userService.getIdAuthUser());
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
        userService.update(authUser);
    }

    @Override
    @Transactional
    public void addSongCompilationToEveningPlaylist(Long id) {
        SongCompilation newSongCompilation = dao.getById(id);
        //достаем юзера по id авторизованного юзера
        User authUser = userService.getById(userService.getIdAuthUser());
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
        userService.update(authUser);
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
        return dao.getById(id);
    }


    @Override
    @Transactional
    public void deleteSongCompilationFromPlayList(Long id, String dayTime) {
        SongCompilation newSongCompilation = dao.getById(id);
        //достаем юзера по id авторизованного юзера
        User authUser = userService.getById(userService.getIdAuthUser());
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
        userService.update(authUser);
    }

    private void changeCompanyPlaylist(Set<PlayList> playList, SongCompilation newSongCompilation) {
        PlayList pl = new ArrayList<>(playList).get(0);
        pl.getSongCompilation().remove(newSongCompilation);
    }


    @Override
    public void deleteSongCompilation(SongCompilation songCompilation) throws IOException {
        if (songCompilation.getCover() != null) {
            fileUploadService.eraseCurrentFile(songCompilation.getCover());
        }

        dao.deleteById(songCompilation.getId());
    }


    @Override
    public List<SongCompilationDto> getAllDto() {
        return songCompilationDto.getAllDto();
    }


    @Override
    public List<SongCompilationDto> getListSongCompilationsByGenreIdDto(Long id) {
        return songCompilationDto.getListSongCompilationsByGenreIdDto(id);
    }

    @Override
    public SongCompilationDto getSongCompilationByIdDto(Long id) {
        return songCompilationDto.getSongCompilationByIdDto(id);
    }

    @Override
    public List<SongDto> getSongsDtoBySongCompilation(String compilationName) {
        return songCompilationDto.getSongsDtoBySongCompilation(compilationName);
    }


    @Override
    public List<SongCompilationDto> getAllCompilationsPlaylistByCompanyIdDto(Long id, String namePlayList) {
        return songCompilationDto.getAllCompilationsPlaylistByCompanyIdDto(id, namePlayList);
    }
    @Override
    public SongCompilation getSongCompilationByCompilationName(String compilationName) {
        return dao.getSongCompilationByCompilationName(compilationName);
    }



}
