package az.edu.ada.wm2.lab6.service;

import az.edu.ada.wm2.lab6.model.Category;
import az.edu.ada.wm2.lab6.model.Product;
import az.edu.ada.wm2.lab6.model.dto.CategoryRequestDto;
import az.edu.ada.wm2.lab6.model.dto.CategoryResponseDto;
import az.edu.ada.wm2.lab6.model.dto.ProductResponseDto;
import az.edu.ada.wm2.lab6.model.mapper.CategoryMapper;
import az.edu.ada.wm2.lab6.repository.CategoryRepository;
import az.edu.ada.wm2.lab6.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CategoryResponseDto create(CategoryRequestDto dto) {
        Category category = CategoryMapper.toEntity(dto);
        return CategoryMapper.toResponseDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponseDto> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toResponseDto)
                .toList();
    }

    @Override
    public CategoryResponseDto addProduct(UUID categoryId, UUID productId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        category.getProducts().add(product);
        product.getCategories().add(category);

        categoryRepository.save(category);
        productRepository.save(product);

        return CategoryMapper.toResponseDto(category);
    }

    @Override
    public List<ProductResponseDto> getProducts(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();

        return category.getProducts().stream()
                .map(p -> {
                    ProductResponseDto dto = new ProductResponseDto();
                    dto.setId(p.getId());
                    dto.setProductName(p.getProductName());
                    dto.setPrice(p.getPrice());
                    dto.setExpirationDate(p.getExpirationDate());
                    dto.setCategoryNames(
                            p.getCategories().stream().map(Category::getName).toList()
                    );
                    return dto;
                })
                .toList();
    }
}
