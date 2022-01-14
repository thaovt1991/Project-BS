package com.cg.model.dto;

import com.cg.model.CategoryGroup;
import com.cg.model.Product;
import com.cg.model.ProductImage;
import com.cg.utils.AppUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {

    private Long id;

    private Long categoryGroupId;

    @Pattern(regexp = "^[\\pL .,0-9()_:-]{2,50}$", message = "Tên sản phẩm phải chứa từ 2-50 ký tự và không có ký tự đặc biệt")
    @NotBlank(message = "Tên sản phẩm không được trống")
    private String name;

    @NotBlank(message = "Tên tác giả không được trống")
    private String author;


    @NotBlank(message = "Tên công ty không được trống")
    private String publishingCompany;

    @NotNull(message = "Ngay san xuat khong duoc de trong")
    private String publicationDate;

    @NotNull(message = "Số trang không được trống")
    @Min(value = 1, message = "Số trang không được nhỏ hơn 1")
    private int page;


    private BigDecimal price ;

    private BigDecimal percentageDiscount ;

    private BigDecimal discountAmount ;

    private BigDecimal lastPrice ;

    private String description;

    private String fileName;

    private String fileFolder;

    private String fileUrl;

    private String cloudId;

    private String fileProductId;

    @NotNull(message = "Hay upload anh cua san pham")
    private MultipartFile file;

    private String fileType;



    public Product toProduct(CategoryGroup categoryGroup)  {

        Product product =  new Product();
        product.setId(id);
        product.setCategoryGroup(categoryGroup);
        product.setName(name);
        product.setAuthor(author);
        product.setPublishingCompany(publishingCompany);

        product.setPublicationDate(AppUtils.convertoString(publicationDate));

        product.setPage(page);
        product.setPrice(price);
        product.setPercentageDiscount(percentageDiscount);
        product.setDiscountAmount(discountAmount);
        product.setLastPrice(lastPrice);
        product.setDescription(description);
        product.setCreatedBy(2L);
        product.setUpdatedBy(2L);
        return product  ;
    }

    public ProductImage toProductImage() {
        ProductImage productImage = new ProductImage();
        productImage.setId(fileProductId);
        productImage.setFileName(fileName);
        productImage.setFileFolder(fileFolder);
        productImage.setFileUrl(fileUrl);
        productImage.setCloudId(cloudId);
        productImage.setFileType(fileType);
        return productImage ;
    }
}
