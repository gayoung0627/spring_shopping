package com.example.shoppingproject.persistence;

import com.example.shoppingproject.model.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, String> {
    List<ItemEntity> findByUserId(String userId);
    List<ItemEntity> findByTitle(String title);
}
