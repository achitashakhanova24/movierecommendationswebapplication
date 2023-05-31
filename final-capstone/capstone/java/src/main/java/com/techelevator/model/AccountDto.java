package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class AccountDto {

        private String email;
        private List<String> favorites = new ArrayList<>();

        public AccountDto(){}

        public AccountDto(List<String> favorites, String email) {
            this.favorites = favorites;
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public List<String> getFavorites() {
            return favorites;
        }

        public void setFavorites(List<String> favorites) {
            this.favorites = favorites;
        }


}

