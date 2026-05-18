package br.com.fiap.example.basicproductssystem.domainmodel;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bps_product")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private @Setter @Getter Long id;

    @Column(name = "name", nullable = false, length = 100)
    private @Setter @Getter String name;

    @Column(name = "price", nullable = false, precision = 14, scale = 2)
    private @Setter @Getter BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private @Setter @Getter Category category;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

}
