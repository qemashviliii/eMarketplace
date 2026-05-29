package com.example.eMarketplace.Service;

import com.example.eMarketplace.DTO.ItemDTO;
import com.example.eMarketplace.Entity.Item;
import com.example.eMarketplace.Entity.User;
import com.example.eMarketplace.Repository.ItemRepository;
import com.example.eMarketplace.Repository.UserRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemService(
            ItemRepository itemRepository,
            UserRepository userRepository
    ) {

        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public Page<ItemDTO> getItems(
            int page,
            String sort
    ) {

        Sort sorting = switch (sort) {

            case "dateAsc" ->
                    Sort.by("submissionTime").ascending();

            case "priceAsc" ->
                    Sort.by("price").ascending();

            case "priceDesc" ->
                    Sort.by("price").descending();

            default ->
                    Sort.by("submissionTime").descending();
        };

        Pageable pageable =
                PageRequest.of(page, 6, sorting);

        return itemRepository
                .findAll(pageable)
                .map(this::mapToDTO);
    }

    public ItemDTO getItemById(Long id) {

        Item item = itemRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Item not found"));

        return mapToDTO(item);
    }

    public void saveItem(
            String name,
            Long price,
            String description,
            MultipartFile photo,
            UUID userId
    ) throws IOException {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Item item = new Item();

        item.setName(name);
        item.setPrice(price);
        item.setDescription(description);
        item.setSubmissionTime(LocalDateTime.now());

        item.setUser(user);

        if (photo != null && !photo.isEmpty()) {

            if (!photo.getContentType()
                    .startsWith("image/")) {

                throw new RuntimeException(
                        "Only image files allowed"
                );
            }

            String fileName =
                    System.currentTimeMillis()
                            + "_"
                            + photo.getOriginalFilename();

            Path uploadPath = Paths.get("uploads");

            Files.createDirectories(uploadPath);

            Files.copy(
                    photo.getInputStream(),
                    uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING
            );

            item.setPhotoUrl("/uploads/" + fileName);
        }

        itemRepository.save(item);
    }

    private ItemDTO mapToDTO(Item item) {

        return new ItemDTO(

                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getDescription(),
                item.getSubmissionTime(),
                item.getPhotoUrl(),
                item.getUser().getUsername()
        );
    }
}