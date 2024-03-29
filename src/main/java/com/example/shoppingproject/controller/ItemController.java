package com.example.shoppingproject.controller;


import com.example.shoppingproject.dto.ItemDTO;
import com.example.shoppingproject.dto.ResponseDTO;
import com.example.shoppingproject.model.ItemEntity;
import com.example.shoppingproject.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    //제품 정보 추가
    @PostMapping
    public ResponseEntity<?> createItem(@AuthenticationPrincipal String userId, @RequestBody ItemDTO dto){
        try{
            ItemEntity itemEntity = ItemDTO.toEntity(dto);
            itemEntity.setId(null);
            itemEntity.setUserId(userId);
            List<ItemEntity> itemEntities = itemService.create(itemEntity);
            List<ItemDTO> dtos = itemEntities.stream().map(ItemDTO::new).collect(Collectors.toList());
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error =e.getMessage();
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

     //제품 정보 검색
     @GetMapping
     public ResponseEntity<?> retrieveItemList(@AuthenticationPrincipal String userId, ItemDTO dto) {
         try {
             ItemEntity itemEntity = ItemDTO.toEntity(dto);
             itemEntity.setUserId(userId);
             List<ItemEntity> itemEntities = itemService.search(itemEntity.getTitle());
             List<ItemDTO> dtos = itemEntities.stream().map(ItemDTO::new).collect(Collectors.toList());
             ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().data(dtos).build();
             return ResponseEntity.ok().body(response);
         } catch (Exception e) {
             String error = e.getMessage();
             ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().error(error).build();
             return ResponseEntity.badRequest().body(response);
         }
     }


    //제품 정보 수정
    @PutMapping
    public ResponseEntity<?> updateItem(@AuthenticationPrincipal String userId,@RequestBody ItemDTO dto){
        ItemEntity itemEntity = ItemDTO.toEntity(dto);
        itemEntity.setUserId(userId);
        List<ItemEntity> entities = itemService.update(itemEntity);
        List<ItemDTO> dtos = entities.stream().map(ItemDTO::new).collect(Collectors.toList());
        ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    //제품 정보 삭제
    @DeleteMapping
    public ResponseEntity<?> deleteItem(@AuthenticationPrincipal String userId, @RequestBody ItemDTO dto){
        try{
            ItemEntity itemEntity = ItemDTO.toEntity(dto);
            itemEntity.setUserId(userId);
            List<ItemEntity> entities = itemService.delete(itemEntity);
            List<ItemDTO> dtos = entities.stream().map(ItemDTO::new).collect(Collectors.toList());
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> retrieveItemList(@AuthenticationPrincipal String userId) {
        try {
            List<ItemEntity> itemEntities = itemService.getAllItems(userId);
            List<ItemDTO> dtos = itemEntities.stream().map(ItemDTO::new).collect(Collectors.toList());
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

}
