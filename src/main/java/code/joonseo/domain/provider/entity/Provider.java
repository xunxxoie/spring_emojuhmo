package code.joonseo.domain.provider.entity;

import code.joonseo.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "providers")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;

    @Builder
    private Provider(String company){
        this.company = company;
    }

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();
}
