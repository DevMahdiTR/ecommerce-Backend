package com.ecommerce.ecommerce.model.detail;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "details")
@Data
public class Detail {

    @SequenceGenerator(
            name = "detail_sequence",
            sequenceName = "detail_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "detail_sequence")
    @Column(name = "id", unique = true , nullable = false)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description" , nullable = false)
    @Size(min = 2 , max = 999 , message = "The description should be between 2 and 999 character per chapter.")
    private String description;


    //to do relation with article
}
