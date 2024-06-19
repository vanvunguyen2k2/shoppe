package com.example.shoppe_project.Service;


import com.example.shoppe_project.Config.Exception.AppException;
import com.example.shoppe_project.Config.Exception.ErrorResponseEnum;
import com.example.shoppe_project.Repository.AccountRepository;
import com.example.shoppe_project.Repository.ProductRepository;
import com.example.shoppe_project.Repository.specification.ProductSpecification;
import com.example.shoppe_project.modal.dto.*;
import com.example.shoppe_project.modal.entity.Account;
import com.example.shoppe_project.modal.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ProductService implements IProductService{
    @Autowired
    private final ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product create(ProducCreatetRequestDto dto) {
        Product entity = new Product();
        BeanUtils.copyProperties(dto, entity);
        return productRepository.save(entity);
    }

    @Override
    public Product get_by_id(int id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()){
            return productOptional.get();
        }
        throw new AppException(ErrorResponseEnum.NOT_FOUND_PRODUCT);
    }

    @Override
    public Product update(ProductUpdateRequestDto dto) {
        Product entity = new Product();
        BeanUtils.copyProperties(dto, entity);
        return productRepository.save(entity);
    }

    public void delete(int id){
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> search(SearchProductRequestDto request) {
        PageRequest pageRequest = null;
        if ("DESC".equals(request.getSortType())){
            // Giá trị page mà thư viện mong muốn để vào trang đầu tiên: 0
            // Giá trị mình muốn để lấy trang đầu tiền: 1 - 1
            pageRequest = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by(request.getSortField()).descending());
        } else {
            pageRequest = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by(request.getSortField()).ascending());
        }
//        PageRequest pageRequest1 = PageRequest.of(request.getPage(), request.getSize());
        Specification<Product> condition = ProductSpecification.buildCondition(request);
        return productRepository.findAll(condition, pageRequest);

    }


}
