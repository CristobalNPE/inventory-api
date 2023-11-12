package dev.cnpe.inventoryappapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "categories")
public class Category {

  @Id
  @GeneratedValue(
          strategy = GenerationType.SEQUENCE,
          generator = "category_id_sequence")
  private Long id;

  private String name;

  private String description;

  private String url;

  @ManyToMany(mappedBy = "categories")
  private Set<Item> categoryItems = new HashSet<>();


}
