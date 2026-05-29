package com.example.eMarketplace.Service;

import com.example.eMarketplace.DTO.ItemDTO;
import com.example.eMarketplace.Entity.Item;
import com.example.eMarketplace.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    @Value("${upload.path:src/main/resources/static/uploads/}")
    private String uploadPath;

    public Page<ItemDTO> getItems(int page){
        PageRequest pageable = PageRequest.of(page,6);
        return itemRepository.findAllByOrderBySubmissionTimeDesc(pageable).map(item -> new ItemDTO(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getDescription(),
                item.getSubmissionTime(),
                item.getPhotoUrl()
        ));



    }
    public ItemDTO getItemById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not Found"));

        return new ItemDTO(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getDescription(),
                item.getSubmissionTime(),
                item.getPhotoUrl()

        );

    }
    public void saveItem(String name, Long price, String description, MultipartFile photoUrl) throws IOException {
         Item item = new Item();
         item.setName(name);
         item.setPrice(price);
         item.setDescription(description);
         item.setSubmissionTime(LocalDateTime.now());

         if (photoUrl !=null && !photoUrl.isEmpty()){
             Path path = Paths.get(uploadPath);
             if(!Files.exists(path)){
                 Files.createDirectories(path);
             }
             String fileName = UUID.randomUUID().toString()+"_"+photoUrl.getOriginalFilename();
               Files.copy(photoUrl.getInputStream(),path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
               item.setPhotoUrl("/uploads/" + fileName);


         }else {
             item.setPhotoUrl("/uploads/default.jpg");
         }
        itemRepository.save(item);
    }
}
