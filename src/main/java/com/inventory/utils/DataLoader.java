package com.inventory.utils;

import com.inventory.models.Product;
import com.inventory.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class DataLoader {

    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void init() throws IOException {

        //loadDataFromCsv(new File("/Users/arishhaque/Desktop/books.csv"));
    }

    private void loadDataFromCsv(File file) throws IOException {

        List<Product> productList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String header = br.readLine(), row;
        while((row = br.readLine()) != null) {

            try {
                String[] data = row.split(",");
                if(data.length > 0){

                    List<String> bookList = Arrays.asList(data[0].split(";"));
                    Product product = new Product();
                    product.setProductId(bookList.get(0));
                    product.setName(bookList.get(1));
                    product.setActive(true);
                    productList.add(product);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println(productList.toString());
        productRepository.saveAll(productList);
        log.info("Product Data Inserted into Database");
    }
}
