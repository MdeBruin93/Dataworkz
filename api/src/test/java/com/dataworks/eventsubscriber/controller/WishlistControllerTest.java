package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.WishlistNotFoundException;
import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.model.dto.WishlistDto;
import com.dataworks.eventsubscriber.service.auth.WebAuthDetailService;
import com.dataworks.eventsubscriber.service.wishlist.WishlistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WishlistController.class)
public class WishlistControllerTest {
    @MockBean
    WebAuthDetailService webAuthService;
    @MockBean
    BindingResult bindingResult;
    @MockBean
    WishlistService wishlistService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findById_ShouldReturnAWishlist() throws Exception {
        // given
        var wishlistId = 1;
        var wishlistDto = new WishlistDto();

        // when
        when(wishlistService.findByIdAndUserId(wishlistId)).thenReturn(wishlistDto);

        // then
        mockMvc.perform(get("/api/wishlists/" + wishlistId))
                .andExpect(status().isOk());
    }

    @Test
    public void findById_ShouldReturnANotFound() throws Exception {
        // given
        var wishlistId = 1;

        // when
        when(wishlistService.findByIdAndUserId(wishlistId)).thenThrow(WishlistNotFoundException.class);

        // then
        mockMvc.perform(get("/api/wishlists/" + wishlistId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void store_ShouldReturnANotFound() throws Exception {
        // given
        var wishlistDto = new WishlistDto();
        wishlistDto.setName("some test wishlist");

        var json = new ObjectMapper().writeValueAsString(wishlistDto);

        // when
        when(wishlistService.store(any(WishlistDto.class))).thenThrow(UserNotFoundException.class);

        // then
        mockMvc.perform(post("/api/wishlists/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void store_ShouldSaveSuccessfully() throws Exception {
        // given
        var wishlistDto = new WishlistDto();
        wishlistDto.setName("some test wishlist");

        var json = new ObjectMapper().writeValueAsString(wishlistDto);

        // when
        when(wishlistService.store(any(WishlistDto.class))).thenReturn(wishlistDto);

        // then
        mockMvc.perform(post("/api/wishlists/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void update_ShouldSaveSuccessfully() throws Exception {
        // given
        var wishlistId = 1;
        var wishlistDto = new WishlistDto();
        wishlistDto.setName("some test wishlist");

        var json = new ObjectMapper().writeValueAsString(wishlistDto);

        // when
        when(wishlistService.update(any(Integer.class), any(WishlistDto.class))).thenReturn(wishlistDto);

        // then
        mockMvc.perform(put("/api/wishlists/" + wishlistId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void update_ShouldThrowUserNotFoundException() throws Exception {
        // given
        var wishlistId = 1;
        var wishlistDto = new WishlistDto();
        wishlistDto.setName("Some test wishlist");

        var json = new ObjectMapper().writeValueAsString(wishlistDto);

        // when
        when(wishlistService.update(any(Integer.class), any(WishlistDto.class))).thenThrow(UserNotFoundException.class);

        // then
        mockMvc.perform(put("/api/wishlists/" + wishlistId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_ShouldThrowEventNotFoundException() throws Exception {
        // given
        var wishlistId = 1;
        var wishlistDto = new WishlistDto();
        wishlistDto.setName("some test wishlist");

        var json = new ObjectMapper().writeValueAsString(wishlistDto);

        // when
        when(wishlistService.update(any(Integer.class), any(WishlistDto.class))).thenThrow(EventNotFoundException.class);

        // then
        mockMvc.perform(put("/api/wishlists/" + wishlistId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByuser_ShouldReturnTheWishlistsOfTheUser() throws Exception {
        // given
        var wishlistDto = new WishlistDto();
        var wishlists = new ArrayList<WishlistDto>();
        wishlists.add(wishlistDto);

        // when
        when(wishlistService.findByUserId()).thenReturn(wishlists);

        // then
        mockMvc.perform(get("/api/wishlists/findbyuser"))
                .andExpect(status().isOk());
    }

    @Test
    public void findByuser_ShouldReturnUserNotFoundException() throws Exception {
        // given

        // when
        when(wishlistService.findByUserId()).thenThrow(UserNotFoundException.class);

        // then
        mockMvc.perform(get("/api/wishlists/findbyuser"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void delete_ShouldReturnNoContent() throws Exception {
        // given
        var wishlistId = 1;

        // when
        doNothing().when(wishlistService).delete(wishlistId);

        // then
        mockMvc.perform(delete("/api/wishlists/" + wishlistId))
                .andExpect(status().isNoContent());
    }
}