package app.repository;

import app.payload.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends ElasticsearchRepository<Product, String> {

}
