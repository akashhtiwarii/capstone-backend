package com.capstone.restaurants_service.dtoTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.capstone.restaurants_service.dto.GetOwnerRestaurantsInDTO;
import org.junit.jupiter.api.Test;

class GetOwnerRestaurantsInDTOTest {

    @Test
    void testGetterAndSetter() {
        GetOwnerRestaurantsInDTO getOwnerRestaurantsInDTO = new GetOwnerRestaurantsInDTO();
        long ownerId = 123L;

        getOwnerRestaurantsInDTO.setOwnerId(ownerId);

        assertThat(getOwnerRestaurantsInDTO.getOwnerId()).isEqualTo(ownerId);
    }

    @Test
    void testEqualsAndHashCode() {
        GetOwnerRestaurantsInDTO getOwnerRestaurantsInDTO = new GetOwnerRestaurantsInDTO(123L);
        GetOwnerRestaurantsInDTO getOwnerRestaurantsInDTO1 = new GetOwnerRestaurantsInDTO(123L);
        GetOwnerRestaurantsInDTO getOwnerRestaurantsInDTO2 = new GetOwnerRestaurantsInDTO(456L);

        assertThat(getOwnerRestaurantsInDTO).isEqualTo(getOwnerRestaurantsInDTO1);
        assertThat(getOwnerRestaurantsInDTO).isNotEqualTo(getOwnerRestaurantsInDTO2);
        assertThat(getOwnerRestaurantsInDTO.hashCode()).isEqualTo(getOwnerRestaurantsInDTO1.hashCode());
        assertThat(getOwnerRestaurantsInDTO.hashCode()).isNotEqualTo(getOwnerRestaurantsInDTO2.hashCode());
    }
}
