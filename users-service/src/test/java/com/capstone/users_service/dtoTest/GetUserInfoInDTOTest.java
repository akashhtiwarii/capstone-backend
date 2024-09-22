package com.capstone.users_service.dtoTest;

import com.capstone.users_service.dto.GetUserInfoInDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GetUserInfoInDTOTest {

    @Test
    public void testGetterAndSetter() {
        GetUserInfoInDTO getUserInfoInDTO = new GetUserInfoInDTO();

        assertEquals(0, getUserInfoInDTO.getUserId());
        long userId = 101;
        getUserInfoInDTO.setUserId(userId);
        assertEquals(userId, getUserInfoInDTO.getUserId());

        assertEquals(0, getUserInfoInDTO.getLoggedInUserId());
        long loggedInUserId = 202;
        getUserInfoInDTO.setLoggedInUserId(loggedInUserId);
        assertEquals(loggedInUserId, getUserInfoInDTO.getLoggedInUserId());
    }

    @Test
    public void testEqualsAndHashcode() {
        long userId = 101;
        long loggedInUserId = 202;

        GetUserInfoInDTO getUserInfoInDTO1 = buildGetUserInfoInDTO(userId, loggedInUserId);

        assertEquals(getUserInfoInDTO1, getUserInfoInDTO1);
        assertEquals(getUserInfoInDTO1.hashCode(), getUserInfoInDTO1.hashCode());

        assertNotEquals(getUserInfoInDTO1, new Object());

        GetUserInfoInDTO getUserInfoInDTO2 = buildGetUserInfoInDTO(userId, loggedInUserId);
        assertEquals(getUserInfoInDTO1, getUserInfoInDTO2);
        assertEquals(getUserInfoInDTO1.hashCode(), getUserInfoInDTO2.hashCode());

        getUserInfoInDTO2 = buildGetUserInfoInDTO(userId + 1, loggedInUserId);
        assertNotEquals(getUserInfoInDTO1, getUserInfoInDTO2);
        assertNotEquals(getUserInfoInDTO1.hashCode(), getUserInfoInDTO2.hashCode());

        getUserInfoInDTO2 = buildGetUserInfoInDTO(userId, loggedInUserId + 1);
        assertNotEquals(getUserInfoInDTO1, getUserInfoInDTO2);
        assertNotEquals(getUserInfoInDTO1.hashCode(), getUserInfoInDTO2.hashCode());
    }

    @Test
    public void testToString() {
        GetUserInfoInDTO getUserInfoInDTO = new GetUserInfoInDTO();

        long userId = 101;
        long loggedInUserId = 202;

        getUserInfoInDTO.setUserId(userId);
        getUserInfoInDTO.setLoggedInUserId(loggedInUserId);

        assertEquals("GetUserInfoInDTO(userId=101, loggedInUserId=202)", getUserInfoInDTO.toString());
    }

    private GetUserInfoInDTO buildGetUserInfoInDTO(long userId, long loggedInUserId) {
        GetUserInfoInDTO getUserInfoInDTO = new GetUserInfoInDTO();
        getUserInfoInDTO.setUserId(userId);
        getUserInfoInDTO.setLoggedInUserId(loggedInUserId);
        return getUserInfoInDTO;
    }
}
