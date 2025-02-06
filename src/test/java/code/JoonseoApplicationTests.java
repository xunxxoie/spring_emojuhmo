package code;

import code.joonseo.JoonseoApplication;
import code.joonseo.domain.product.entity.Product;
import code.joonseo.domain.product.repository.ProductRepository;
import code.joonseo.domain.provider.entity.Provider;
import code.joonseo.domain.provider.repository.ProviderRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(classes = JoonseoApplication.class)
@Transactional
class JoonseoApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName(value = "일대다 연관관계에서의 영속성 테스트")
    void contextLoads() {

        Provider provider = Provider.builder()
                .company("Samsung")
                .build();

        Product product1 = Product.builder()
                .name("삼성에어컨")
                .price(10000)
                .provider(provider)
                .build();

        Product product2 = Product.builder()
                .name("삼탠바이미")
                .price(100000)
                .provider(provider)
                .build();

        provider.addProduct(product1);
        provider.addProduct(product2);

        providerRepository.save(provider);

        // 데이터베이스 트랜잭션을 통해 로드
        Provider savedProvider = providerRepository.findById(provider.getId())
                        .orElse(null);

        List<Product> products = productRepository.findAll();

        for(Product product : products){
            log.info("product name : {}", product.getName());
        }

        log.info("size of list = {}", savedProvider.getProducts().size());
    }

    @Test
    @Transactional
    @DisplayName(value = "영속성 컨택스트 1차 캐시 테스트")
    void firstLevelCacheTest(){
        Provider provider = Provider.builder()
                .company("LG")
                .build();

        Provider savedProvider = providerRepository.save(provider);

        Long generatedId = savedProvider.getId();

        Provider provider1 = em.find(Provider.class, generatedId);

        Provider provider2 = em.find(Provider.class, generatedId);

        assertThat(provider1 == provider2).isTrue();
    }

}
