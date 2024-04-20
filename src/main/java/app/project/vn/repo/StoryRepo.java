package app.project.vn.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import app.project.vn.model.Story;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface StoryRepo extends JpaRepository<Story, Long> {
    List<Story> findAllByStatus(boolean status);

    @Query("""
            SELECT s
            FROM Story s
            join fetch s.chapters
            WHERE s.storyId = :id
            """)
    Optional<StoryResponse> getDetailById(Long id);

    interface StoryResponse {
        Long getStoryId();

        String getTitle();

        String getAuthor();

        String getDescription();

        String getCoverImage();

        Set<ChapterResponse> getChapters();

        interface ChapterResponse {
            Long getChapterId();

            String getTitle();

            String getContent();
        }
    }
}
