package dev.cnpe.inventoryappapi.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "items")
public class Item {

  @Id
  @GeneratedValue(
          strategy = GenerationType.SEQUENCE,
          generator = "item_id_sequence")
  private Long id;

  private String name;

  private String description;

  private Integer stock;

  @ManyToMany
  @JoinTable(
          name = "item_categories",
          joinColumns = @JoinColumn(name = "item_id"),
          inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  private Set<Category> categories = new HashSet<>();

  private BigDecimal price;

  private String url;

}
