package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.TagDto;
import spring.app.model.Tag;
import spring.app.service.abstraction.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/tag")
public class AdminTagRestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AdminTagRestController.class);

    private final TagService tagService;

    @Autowired
    public AdminTagRestController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/all_tags")
    public List<TagDto> getAllTags(@RequestParam("size") int pageSize, @RequestParam("page") int pageNo) {
        return tagService.getAllTagDto(pageSize, pageNo - 1);
    }

    @GetMapping(value = "/count_tags")
    public Long getCountTags(){
        return tagService.getRowsCountTagDto();
    }

    @GetMapping(value = "/is_free")
    public boolean isTagNameFree(@RequestParam("name") String name) {
        name = name.toLowerCase();
        return !tagService.isExistByName(name);
    }

    @PostMapping(value = "/add_tag")
    public void addTag(@RequestBody String name) {
        LOGGER.info("POST request '/add_tag'");
        name = name.toLowerCase();
        name = name.replaceAll("[^a-zа-я0-9]", "");

        if (!tagService.isExistByName(name)) {
            Tag tag = new Tag();
            tag.setName(name);
            tagService.save(tag);
            LOGGER.info("Added tag with name = {}", name);
        }
    }

    @PutMapping(value = "/update_tag")
    public void updateTag(@RequestBody TagDto tagDto) {
        LOGGER.info("PUT request '/update_tag': " + tagDto.toString());

        Tag tag = tagService.getById(tagDto.getId());
        String tagDtoName = tagDto.getName();
        tag.setName(tagDtoName.toLowerCase());

        tagService.update(tag);
    }

    @DeleteMapping(value = "/delete_tag")
    public void deleteTag(@RequestBody Long id) {
        LOGGER.info("DELETE request '/delete_tag': id = " + id);

        tagService.deleteById(id);
    }

}