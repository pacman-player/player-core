package spring.app.service.abstraction;

import spring.app.dto.SearchSongDTO;

import java.util.List;

public interface MusicSearchAndStorage {
    public List<SearchSongDTO> searchBySongName(String songName);
    public List<SearchSongDTO> searchByAuthorName(String authorName);
    public List<SearchSongDTO> searchBySongNameAndAuthorName(String songName, String authorName);

}

/*
Общий сервис. Для работы со стороними сервисами.
1. Метод searchBySongName(songName) return List<SearchSongDTO (link, authorName, SongName)>
2. Метод searchByAuthorName(authorName) return List<SearchSongDTO (link, authorName, SongName)>
3. Метод searchByAuthorNameAndSongName(songName, authorName) return SearchSongDTO (link, authorName, SongName)
4. получение и соранение песни getSong(SearchSongDTO)

Убрать из дто ссылку на песню. Всю информацию хранить в таблице
song_download_request_info; И пердавать идишник записи вместе с дтохой

Добавить в эту таблицу инфу о том - с какого сервиса мы можем скачать
 */