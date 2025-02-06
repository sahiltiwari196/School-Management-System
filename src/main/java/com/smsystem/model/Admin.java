package com.smsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    
    public Admin() {
        super.setRole("ADMIN");
    }
}
