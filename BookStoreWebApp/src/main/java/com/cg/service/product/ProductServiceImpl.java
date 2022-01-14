package com.cg.service.product;


import com.cg.exception.DataInputException;
import com.cg.model.CategoryGroup;
import com.cg.model.Product;
import com.cg.model.ProductImage;
import com.cg.model.dto.IProductDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.model.enums.FileType;
import com.cg.repository.CategoryGroupRepository;
import com.cg.repository.ProductImageRepository;
import com.cg.repository.ProductRepository;
import com.cg.service.categorygroup.CategoryGroupService;
import com.cg.service.upload.UploadService;
import com.cg.utils.AppUtils;
import com.cg.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryGroupRepository categoryGroupRepository ;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private UploadUtils uploadUtils;


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product getById(Long id) {
        return null;
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Iterable<IProductDTO> findAllIProductDTO() {
        return productRepository.findAllIProductDTO();
    }



    @Override
    public Product create(ProductDTO productDTO) {


        String fileType = productDTO.getFile().getContentType();

        assert fileType != null;

        fileType = fileType.substring(0, 5);

        productDTO.setFileType(fileType);

        CategoryGroup categoryGroup = categoryGroupRepository.findById(productDTO.getCategoryGroupId()).get() ;

        Product product = productDTO.toProduct(categoryGroup)  ;

        productRepository.save(product);

        String slug = AppUtils.removeNonAlphanumeric(product.getName()+"-srbs-"+product.getId()) ;

        product.setSlug(slug);
        productRepository.save(product) ;

        ProductImage productImage = productImageRepository.save(productDTO.toProductImage());

        if (fileType.equals(FileType.IMAGE.getValue())) {
            uploadAndSaveProductImage(productDTO, product, productImage);
        }

//        String avtar = productImage.getFileUrl() ;
//        product.setAvatar(avtar);
//        productRepository.save(product) ;

        return product;
    }



    @Override
    public IProductDTO findIProductDTOById(Long id) {
        return productRepository.findIProductDTOById(id);
    }

    private void uploadAndSaveProductImage(ProductDTO productDTO, Product product, ProductImage productImage) {
        try {
            Map uploadResult = uploadService.uploadImage(productDTO.getFile(), uploadUtils.buildImageUploadParams(productImage));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            productImage.setFileName(productImage.getId() + "." + fileFormat);
            productImage.setFileUrl(fileUrl);
            productImage.setFileFolder(UploadUtils.IMAGE_UPLOAD_FOLDER);
            productImage.setCloudId(productImage.getFileFolder() + "/" + productImage.getId());//cai ni co the doi
            productImage.setProduct(product);


            productImageRepository.save(productImage);
//            String avtar = "https://res.cloudinary.com/c0721k1/image/upload"+  productImage.getFileFolder() + "/" + productImage.getFileName();
            String avtar = productImage.getFileUrl() ;
            product.setAvatar(avtar);

            productRepository.save(product) ;

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    @Override
    public Boolean existsBySlugEquals(String slug) {
        return null;
    }

    @Override
    public Boolean existsBySlugEqualsAndIdIsNot(String slug, long id) {
        return null;
    }

    @Override
    public void delete(Product product) throws IOException {

        Optional<ProductImage> productImage = productImageRepository.findByProduct(product);

        if (productImage.isPresent()) {
            String publicId = productImage.get().getCloudId();

            if (productImage.get().getFileType().equals(FileType.IMAGE.getValue())) {
                uploadService.destroyImage(publicId, uploadUtils.buildImageDestroyParams(product, publicId));
            }

            productImageRepository.delete(productImage.get());
        }

        productRepository.delete(product);

    }
}

