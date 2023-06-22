package pt.ua.deti.store;

import com.opencsv.CSVReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pt.ua.deti.store.database.ProductEntity;
import pt.ua.deti.store.database.ProductRepository;

import java.io.FileReader;

@Component
public class Loader implements CommandLineRunner {
    private final ProductRepository productRepository;

    public Loader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.findAll().size() == 0) {
            FileReader reader = new FileReader("src/main/resources/data.csv");

            try (CSVReader parser = new CSVReader(reader)) {
                String[] line;
                System.out.println("Loading data...");
                int i = 0;

                while ((line = parser.readNext()) != null) {
                    if (i > 200) break;

                    ProductEntity product = new ProductEntity(
                            line[0],
                            line[1],
                            line[5],
                            Double.parseDouble(line[8])
                    );

                    productRepository.save(product);
                    i++;
                }
            }
        }
    }
}
