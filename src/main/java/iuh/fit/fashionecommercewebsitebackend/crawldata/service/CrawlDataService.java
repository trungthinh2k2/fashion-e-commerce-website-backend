package iuh.fit.fashionecommercewebsitebackend.crawldata.service;

import iuh.fit.fashionecommercewebsitebackend.crawldata.model.Product;
import iuh.fit.fashionecommercewebsitebackend.crawldata.model.ProductDetail;
import iuh.fit.fashionecommercewebsitebackend.crawldata.res.TikiRes;
import iuh.fit.fashionecommercewebsitebackend.models.*;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Status;
import iuh.fit.fashionecommercewebsitebackend.repositories.ProductDetailRepository;
import iuh.fit.fashionecommercewebsitebackend.repositories.ProductImageRepository;
import iuh.fit.fashionecommercewebsitebackend.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CrawlDataService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductImageRepository productImageRepository;


    public TikiRes previewData(String url) {
        return restTemplate.getForObject(url, TikiRes.class);
    }

    public TikiRes getProducts(String url, int[] brands, int category, int provider, int[] size, int[] color) {
        TikiRes tikiRes = restTemplate.getForObject(url, TikiRes.class);
        Random random = new Random();
        assert tikiRes != null;
        List<Product> products = tikiRes.getData();
        LocalDateTime startDate = LocalDateTime.of(2024, 8, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 11, 30, 23, 59);
        for (Product product : products) {
            ProductDetail productDetail = getProductDetail(product.getId() + "");
            iuh.fit.fashionecommercewebsitebackend.models.Product myProduct =
                    new iuh.fit.fashionecommercewebsitebackend.models.Product();
            myProduct.setId(product.getId() + "" );
            myProduct.setProductName(product.getName());
            myProduct.setProductStatus(Status.ACTIVE);
            myProduct.setThumbnail(product.getThumbnailUrl());
            myProduct.setPrice((double) product.getOriginalPrice());
            myProduct.setDescription(productDetail.getShortDescription());
            myProduct.setProductNameConvert(toLowerCaseAndRemoveAccents(product.getName()));
            myProduct.setInputPrice(product.getPrice() / 2);
            int selectedBrand = brands[random.nextInt(brands.length)];
            myProduct.setBrand(new Brand(selectedBrand));
            myProduct.setCategory(new Category(category));
            myProduct.setProvider(new Provider(provider));
            LocalDateTime randomImportDate = randomDateTime(startDate, endDate);
            myProduct.setImportDate(randomImportDate);
            productRepository.save(myProduct);

            List<iuh.fit.fashionecommercewebsitebackend.models.ProductDetail> productDetails = new ArrayList<>();
            int totalQuantity = 0;
            for (int i = 0; i < productDetail.getConfigurableOptions().size(); i++) {
                int selectedSize = size[random.nextInt(size.length)];
                int selectedColor = color[random.nextInt(color.length)];
                iuh.fit.fashionecommercewebsitebackend.models.ProductDetail myProductDetail =
                        new iuh.fit.fashionecommercewebsitebackend.models.ProductDetail();
                String id = "PD_" + myProduct.getId() + "_"+ UUID.randomUUID().toString().substring(0, 5);
                myProductDetail.setId(id);
                myProductDetail.setProduct(myProduct);
                myProductDetail.setSize(new Size(selectedSize));
                myProductDetail.setColor(new Color(selectedColor));
                int quantity = 5; // Giá trị số lượng giả định
                myProductDetail.setQuantity(quantity);
                myProductDetail.setWeight(300F);
                myProductDetail.setImportDate(randomImportDate);
                productDetails.add(myProductDetail);
                totalQuantity += quantity;
            }
            myProduct.setTotalQuantity(totalQuantity);
            productRepository.save(myProduct);
            productDetailRepository.saveAll(productDetails);

            List<ProductImage> productImages = new ArrayList<>();
            for (int i = 0; i < productDetail.getImages().size(); i++) {
                ProductImage productImage = new ProductImage();
                productImage.setProduct(myProduct);
                productImage.setPath(productDetail.getImages().get(i).getBaseUrl());
                productImages.add(productImage);
            }

            productImageRepository.saveAll(productImages);
        }
        return tikiRes;
    }

    public ProductDetail getProductDetail(String id) {
        String apiUrl = "https://tiki.vn/api/v2/products/" + id + "?platform=web&version=3";
        return restTemplate.getForObject(apiUrl, ProductDetail.class);
    }


    private String toLowerCaseAndRemoveAccents(String input) {
        if (input == null) {
            return null;
        }

        // Bước 1: Chuyển về chữ thường
        String result = input.toLowerCase();

        // Bước 2: Chuẩn hóa và loại bỏ dấu
        result = Normalizer.normalize(result, Normalizer.Form.NFD);
        result = result.replaceAll("\\p{M}", ""); // Loại bỏ dấu

        // Bước 3: Loại bỏ ký tự đặc biệt (nếu cần)
        result = result.replaceAll("[^a-z0-9\\s-]", ""); // Chỉ giữ chữ cái, số, khoảng trắng và dấu gạch ngang
        result = result.replaceAll("\\s+", " ").trim(); // Loại bỏ khoảng trắng thừa

        return result;
    }

    public LocalDateTime randomDateTime(LocalDateTime startDate, LocalDateTime endDate) {
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        Random random = new Random();
        long randomDays = random.nextInt((int) days + 1); // Thêm 1 để bao gồm cả ngày cuối
        return startDate.plusDays(randomDays);
    }

}
