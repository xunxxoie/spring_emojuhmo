package code;

import code.joonseo.JoonseoApplication;
import code.joonseo.domain.product.entity.Product;
import code.joonseo.domain.product.repository.ProductRepository;
import code.joonseo.domain.provider.entity.Provider;
import code.joonseo.domain.provider.repository.ProviderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@SpringBootTest(classes = JoonseoApplication.class)
@Transactional
class JoonseoApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private  ProviderRepository providerRepository;

    @Test
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

        Provider savedProvider = providerRepository.findById(provider.getId())
                        .orElse(null);

        List<Product> products = productRepository.findAll();

        for(Product product : products){
            log.info("product name : {}", product.getName());
        }


        log.info("size of list = {}", savedProvider.getProducts().size());

    }

}
