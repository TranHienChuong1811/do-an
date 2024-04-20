package app.project.vn.controller;

import app.project.vn.model.Chapter;
import app.project.vn.model.Story;
import app.project.vn.repo.ChapterRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/chapter")
public class ChapterController {
    private final ChapterRepo chapterRepo;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @GetMapping("all")
    public Object getAllChapters() {
        return chapterRepo.findAll();
    }

    @GetMapping("detail/{chapterId}")
    public Object getChapterDetail(@PathVariable Long chapterId) {
        var chapter = chapterRepo.findById(chapterId).orElse(null);
        if (chapter == null) {
            return Map.of("status", "NOT_FOUND");
        }
        return chapter;
    }

    @PostMapping("add")
    public Object addChapter(@RequestBody ChapterPayload payload) {
        var chapter = Chapter
                .builder()
                .title(payload.getTitle())
                .content(payload.getContent())
                .story(Story.builder().storyId(payload.getStoryId()).build())
                .build();
        chapterRepo.save(chapter);
        return Map.of("status", "OK");
    }

    @PatchMapping("update")
    public Object updateChapter(@RequestBody ChapterPayload payload) {
        var chapter = chapterRepo.findById(payload.getChapterId()).orElse(null);
        if (chapter == null) {
            return Map.of("status", "NOT_FOUND");
        }
        chapter.setTitle(payload.getTitle());
        chapter.setContent(payload.getContent());
        chapter.setStory(Story.builder().storyId(payload.getStoryId()).build());
        chapterRepo.save(chapter);
        return Map.of("status", "OK");
    }

    @PatchMapping("update-audio")
    public Object updateAudio(@RequestBody ChapterPayload payload) {
        var chapter = chapterRepo.findById(payload.getChapterId()).orElse(null);
        if (chapter == null) {
            return Map.of("status", "NOT_FOUND");
        }
        chapter.setAudio(payload.getAudio());
        chapterRepo.save(chapter);
        return Map.of("status", "OK");
    }

    @DeleteMapping("delete/{chapterId}")
    public Object deleteChapter(@PathVariable Long chapterId) {
        var chapter = chapterRepo.findById(chapterId).orElse(null);
        if (chapter == null) {
            return Map.of("status", "NOT_FOUND");
        }
        chapterRepo.delete(chapter);
        return Map.of("status", "OK");
    }

    @PostMapping("convert-to-audio")
    public Object convertToAudio(@RequestBody ChapterPayload payload) {
        var chapter = chapterRepo.findById(payload.getChapterId()).orElse(null);
        if (chapter == null) {
            return Map.of("status", "NOT_FOUND");
        }
        // convert to audio
        var url = "https://api.fpt.ai/hmi/tts/v5";
        var apiKey = "28ZacWUxU26GLEoNvz3UKVrF14I3YKXn";
        var voice = "banmai";
        var speed = ""; // Điền giá trị của speed nếu cần
        var headers = new HttpHeaders();
        headers.set("api-key", apiKey);
        headers.set("voice", voice);
        headers.set("speed", speed);
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        var requestEntity = new HttpEntity<>(payload.getContent(), headers);
        var response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            var bodyResponse = response.getBody();
            try {
                var jsonNode = objectMapper.readTree(bodyResponse);
                var audioUrl = jsonNode.get("async").asText();
                chapter.setAudio(audioUrl);
                chapterRepo.save(chapter);
                return Map.of("status", "OK", "audioUrl", audioUrl);
            } catch (Exception e) {
                return Map.of("status", "ERROR", "message", e.getMessage());
            }
        }

        return Map.of("status", "ERROR");
    }

    @Data
    public static class ChapterPayload {
        private Long chapterId;
        private String title;
        private String content;
        private Long storyId;
        private String audio;
    }
}
