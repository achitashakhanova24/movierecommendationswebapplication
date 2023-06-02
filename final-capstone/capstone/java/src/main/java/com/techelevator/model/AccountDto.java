package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class AccountDto {

        private String email;

        public AccountDto(){}

        public AccountDto(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }


}

