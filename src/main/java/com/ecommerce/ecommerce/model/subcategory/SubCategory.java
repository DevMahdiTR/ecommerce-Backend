package com.ecommerce.ecommerce.model.subcategory;

import com.ecommerce.ecommerce.model.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "sub_categories")
public class SubCategory {

    @SequenceGenerator(
            name =  "sub_categories_sequence",
            sequenceName = "sub_categories_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "sub_categories_sequence")
    private long id;


    @Column(name = "title", nullable = false,unique = true)
    @Size(min = 2 , max = 30 ,message = "The category title should be between 2 and 30 characters long.")
    private String title;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "category_id")
    private Category category;
}
