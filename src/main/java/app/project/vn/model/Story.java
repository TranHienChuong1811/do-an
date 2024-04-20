package app.project.vn.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "story")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Story {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long storyId;
    private String title;
    private String author;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String coverImage;

    @Builder.Default
    private boolean status = true;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
    @Builder.Default
    @JsonBackReference
    @OrderBy("chapterId ASC")
    private Set<Chapter> chapters = new HashSet<>();
}