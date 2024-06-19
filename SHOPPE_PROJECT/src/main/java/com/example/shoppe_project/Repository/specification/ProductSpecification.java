package com.example.shoppe_project.Repository.specification;

import com.example.shoppe_project.modal.dto.SearchProductRequestDto;
import com.example.shoppe_project.modal.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> buildCondition(SearchProductRequestDto request){
        return Specification.where(buildConditionName(request))
                .and(buildConditionProductType(request))
                .and(buildConditionshipping_unit(request))
                .and(buildConditionSearchPrice(request));
    }

    public static Specification<Product> buildConditionName(SearchProductRequestDto request){
        if (request.getName() != null && !"".equals(request.getName())){
            // Tạo điều kiện tìm kiếm với name
            return (root, query, cri) -> {
                // root: Chọn cột, field, để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: Khai báo loại so sánh dữ liệu. ( lớn hơn, nhỏ hơn, equal, like,.... )
                return cri.like(root.get("name"), "%" + request.getName() + "%");
            };
        } else {
            return null;
        }
    }

    public static Specification<Product> buildConditionProductType(SearchProductRequestDto request){
        if (request.getType() != null){
            // Tạo điều kiện tìm kiếm với name
            return (root, query, cri) -> {

//               Tạo điều kiện để tìm typr. typr sẽ là 1 trong các giá trị truyền vào
                return root.get("type").in(request.getType());
            };
        } else {
            return null;
        }
    }

    public static Specification<Product> buildConditionshipping_unit(SearchProductRequestDto request){
        if (request.getShippingUnit() != null && request.getShippingUnit().size() > 0){
            // Tạo điều kiện tìm kiếm với name
            return (root, query, cri) -> {

//               Tạo điều kiện để tìm typr. typr sẽ là 1 trong các giá trị truyền vào
                return root.get("shippingUnit").in(request.getShippingUnit());
            };
        } else {
            return null;
        }
    }

    public static Specification<Product> buildConditionSearchPrice(SearchProductRequestDto request){
        if (request.getMinPrice() != 0 && request.getMaxPrice() != 0){
            return (root, query, criteriaBuilder) -> {
                return criteriaBuilder.between(root.get("price"), request.getMinPrice(), request.getMaxPrice());
            };
        }else {
            return null;
        }
    }
}
