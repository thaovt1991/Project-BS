package com.cg.model.dto;

import com.cg.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CategoryDTO {

    private Long id;

    @NotBlank(message = "Tên danh mục không được trống")
    @Pattern(regexp = "^[\\pL .,0-9()_:-]{2,50}$", message = "Tên danh mục phải chứa từ 2-50 ký tự và không có ký tự đặc biệt")
    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String slug;

    @NotNull(message = "Hay upload anh cua san pham")
    private MultipartFile imageFile;

    public Category toCategory(){
        Category category= new Category() ;
        category.setName(name) ;
        category.setSlug(slug);
        return category;
    }

}
