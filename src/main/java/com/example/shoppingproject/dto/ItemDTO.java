package com.example.shoppingproject.dto;

import com.example.shoppingproject.model.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO {

    private String id;

    private String title;

    private String teamName;

    private String manufacturer;

    private String userId;

    public ItemDTO(final ItemEntity itemEntity){
        this.id = itemEntity.getId();
        this.title = itemEntity.getTitle();
        this.teamName = itemEntity.getTeamName();
        this.manufacturer = itemEntity.getManufacturer();
        this.userId = itemEntity.getUserId();
    }

    public static ItemEntity toEntity(final ItemDTO dto){
        return ItemEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .teamName(dto.getTeamName())
                .manufacturer(dto.getManufacturer())
                .userId(dto.getUserId())
                .build();
    }


}
