package app.project.vn.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chapter")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Chapter {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long chapterId;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String audio;

    @ManyToOne
    @JoinColumn(name = "story_id")
    private Story story;
}
