package com.ecommerce.ecommerce.model.category;

import com.ecommerce.ecommerce.model.subcategory.SubCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "categories")
public class Category {

    @SequenceGenerator(
            name = "categories_sequence",
            sequenceName = "categories_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "categories_sequence")
    @Column(name  = "id" , nullable = false , unique = true)
    private long id;

    @Column(name = "title", nullable = false , unique = true)
    @Size(min = 2 , max = 30 , message = "The category title should be between 2 and 30 characters long.")
    private String title;


    @OneToMany(mappedBy = "category", cascade =  CascadeType.ALL , fetch = FetchType.EAGER)
    private List<SubCategory> subCategories;



}
