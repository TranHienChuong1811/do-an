package app.project.vn.repo;

import app.project.vn.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepo extends JpaRepository<Chapter, Long> {
}

