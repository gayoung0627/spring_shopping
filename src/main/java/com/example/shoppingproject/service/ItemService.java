package com.example.shoppingproject.service;

import com.example.shoppingproject.model.ItemEntity;
import com.example.shoppingproject.persistence.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<ItemEntity> create(final ItemEntity itemEntity){
        validate(itemEntity);
        itemRepository.save(itemEntity);
        log.info("Entity Id: {} is saved", itemEntity.getId());
        return itemRepository.findByUserId(itemEntity.getUserId());
    }

    public List<ItemEntity> retrieve(final String userId){
        return itemRepository.findByUserId(userId);
    }

    public List<ItemEntity> search(final String title){
        return itemRepository.findByTitle(title);
    }

    public List<ItemEntity> update(final ItemEntity itemEntity){
        validate(itemEntity);

        final Optional<ItemEntity> original = itemRepository.findById(itemEntity.getId());
        original.ifPresent(item ->{
            item.setTitle(itemEntity.getTitle());
            item.setTeamName(itemEntity.getTeamName());
            item.setManufacturer(itemEntity.getManufacturer());
            itemRepository.save(item);
        });
        return retrieve(itemEntity.getUserId());
    }

    public List<ItemEntity> delete(final ItemEntity itemEntity){
        validate(itemEntity);
        try{
            itemRepository.delete(itemEntity);
        }catch (Exception e){
            log.error("error deleting entity", itemEntity.getId(), e);
            throw new RuntimeException("error deleting entity"+ itemEntity.getId());
        }
        return retrieve(itemEntity.getUserId());
    }

    private void validate(final ItemEntity itemEntity){
        if(itemEntity == null){
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        } if(itemEntity.getUserId() == null){
            log.warn("Unknown user");
            throw new RuntimeException(("Unknown user"));
        }
    }
}
