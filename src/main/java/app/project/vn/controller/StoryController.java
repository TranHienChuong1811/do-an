package app.project.vn.controller;


import app.project.vn.model.Story;
import app.project.vn.repo.StoryRepo;
import app.project.vn.utils.CloudinaryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/stories")
public class StoryController {
    private final StoryRepo storyRepo;

    @GetMapping("all")
    public Object getAllStories() {
        return storyRepo.findAllByStatus(true);
    }

    @GetMapping("detail/{storyId}")
    public Object getStoryDetail(@PathVariable("storyId") Long storyId) {
        var rs = storyRepo.getDetailById(storyId).orElse(null);
        if (rs == null) {
            return Map.of("status", "NOT_FOUND");
        }
        return rs;
    }

    @PostMapping("add")
    public Object addStory(@RequestParam("title") String title,
                           @RequestParam("author") String author,
                           @RequestParam("description") String description,
                           @RequestParam(value = "file", required = false) MultipartFile img) {
        try {
            String imgStory = "";
            if (img != null) {
                imgStory = CloudinaryUtils.uploadImage(img.getBytes());
            }
            var story = Story.builder()
                    .title(title)
                    .author(author)
                    .description(description)
                    .coverImage(imgStory)
                    .build();
            storyRepo.save(story);
            return Map.of("status", "OK");
        } catch (IOException e) {
            return Map.of("status", "ERROR", "message", e.getMessage());
        }
    }

    @PatchMapping("update")
    public Object updateStory(@RequestParam("storyId") Long storyId,
                              @RequestParam("title") String title,
                              @RequestParam("author") String author,
                              @RequestParam("description") String description,
                              @RequestParam(value = "file", required = false) MultipartFile img) {
        try {
            var story = storyRepo.findById(storyId).orElse(null);
            if (story == null) {
                return Map.of("status", "NOT_FOUND");
            }
            if (img != null) {
                var urlImage = CloudinaryUtils.uploadImage(img.getBytes());
                story.setCoverImage(urlImage);
            }
            story.setTitle(title);
            story.setAuthor(author);
            story.setDescription(description);
            storyRepo.save(story);
            return Map.of("status", "OK");
        } catch (IOException e) {
            return Map.of("status", "ERROR");
        }
    }

    @DeleteMapping("delete/{storyId}")
    public Object deleteStory(@PathVariable("storyId") Long storyId) {
        var story = storyRepo.findById(storyId).orElse(null);
        if (story == null) {
            return Map.of("status", "NOT_FOUND");
        }
        story.setStatus(false);
        storyRepo.save(story);
        return Map.of("status", "OK");
    }
}
