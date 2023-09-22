package com.ecommerce.ecommerce.model.article;

import com.ecommerce.ecommerce.model.chapter.Chapter;
import com.ecommerce.ecommerce.model.detail.Detail;
import com.ecommerce.ecommerce.model.file.FileData;
import com.ecommerce.ecommerce.model.subcategory.SubCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "articles")
public class Article {

    @SequenceGenerator(
            name = "article_sequence",
            sequenceName = "article_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "article_sequence")
    @Column(name = "id", unique = true ,nullable = false)
    private long id;


    @Column(name = "title" , nullable = false)
    private String title;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "quantity" , nullable = false)
    private long quantity;

    @Column(name = "layout_description", nullable = false)
    @Size(min = 2 , max = 800 , message = "The layout description must be between 2 and 800 characters long.")
    private String layoutDescription;

    @Column(name = "reference" , nullable = false)
    private String reference;

    @OneToMany(mappedBy = "article" , cascade = CascadeType.ALL)
    private List<Chapter> chapters = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Detail> details = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade =  CascadeType.ALL)
    private List<FileData> files = new ArrayList<>();


    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;

}
