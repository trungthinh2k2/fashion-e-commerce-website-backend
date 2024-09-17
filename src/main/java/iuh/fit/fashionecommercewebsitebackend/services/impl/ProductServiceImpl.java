package iuh.fit.fashionecommercewebsitebackend.services.impl;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.ProductDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.products.ProductMapper;
import iuh.fit.fashionecommercewebsitebackend.models.Product;
import iuh.fit.fashionecommercewebsitebackend.models.ProductImage;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Status;
import iuh.fit.fashionecommercewebsitebackend.repositories.ProductImageRepository;
import iuh.fit.fashionecommercewebsitebackend.repositories.ProductRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.ProductService;
import iuh.fit.fashionecommercewebsitebackend.utils.S3Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, String> implements ProductService{

    private ProductRepository productRepository;
    private ProductMapper productMapper;
    private S3Upload s3Upload;
    private ProductImageRepository productImageRepository;

    public ProductServiceImpl(JpaRepository<Product, String> repository) {
        super(repository, Product.class);
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Autowired
    public void setS3Upload(S3Upload s3Upload) {
        this.s3Upload = s3Upload;
    }

    @Autowired
    public void setProductImageRepository(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    @Override
    public Product save(ProductDto productDto) throws DataExistsException {
        if (productRepository.existsByProductName(productDto.getProductName())) {
            throw new DataNotFoundException("Product already exists");
        }

        String id = "Pro" + LocalDate.now().format(DateTimeFormatter.ofPattern("-ddMMyyyy-")) + UUID.randomUUID().toString().substring(0, 8);

        Product product = productMapper.ProductDtoToProduct(productDto);
        product.setId(id);

        product = super.save(product);

        processProductImages(product, productDto);

        return super.save(product);
    }

    @Override
    public void deactivateProduct(String id) throws DataExistsException {
        Product product = super.findById(id).orElseThrow(() -> new DataNotFoundException("Product not found"));
        product.setProductStatus(Status.INACTIVE);
        super.save(product);
    }

    @Override
    public List<Product> findAllByStatus(Status status) {
        return productRepository.findAllByProductStatus(Status.ACTIVE);
    }

    private void processProductImages(Product product, ProductDto productDto) throws DataExistsException {
        List<MultipartFile> images = productDto.getImages();
        if (images == null || images.isEmpty()) {
            return;
        }

        Integer thumbnailIndex = productDto.getThumbnail();

        for (int i = 0; i < images.size(); i++) {
            MultipartFile file = images.get(i);
            if (!isImageFile(file)) {
                throw new RuntimeException("File is not an image");
            }
            String imagePath = uploadImageToS3(file);
            saveProductImage(product, imagePath);
            if (thumbnailIndex != null && thumbnailIndex == i) {
                product.setThumbnail(imagePath);
            }
        }
    }

    private boolean isImageFile(MultipartFile file) {
        return Objects.requireNonNull(file.getContentType()).startsWith("image/");
    }

    private String uploadImageToS3(MultipartFile file) {
        try {
            return s3Upload.uploadFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void saveProductImage(Product product, String imagePath) {
        ProductImage productImage = new ProductImage();
        productImage.setProduct(product);
        productImage.setPath(imagePath);
        productImageRepository.save(productImage);
    }

}
