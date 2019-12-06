package spring.app.service.abstraction;

import spring.app.model.Genre;

public interface GenreService {
    Genre getByName(String name);
}
