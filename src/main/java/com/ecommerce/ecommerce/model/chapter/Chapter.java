package com.ecommerce.ecommerce.model.chapter;

import com.ecommerce.ecommerce.model.article.Article;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "chapters")
public class Chapter {

    @SequenceGenerator(
            name = "chpter_sequence",
            sequenceName = "chpter_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "chpter_sequence")
    @Column(name = "id " ,unique = true , nullable = false)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description" , nullable = false)
    @Size(min = 2 , max = 999 , message = "The description should be between 2 and 999 character per chapter.")
    private String description;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "article_id")
    private Article article;

}
