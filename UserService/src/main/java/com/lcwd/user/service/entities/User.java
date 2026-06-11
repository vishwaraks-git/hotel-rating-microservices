package com.lcwd.user.service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "micro_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "id")
    private String userId;

    @Column(name = "name", length = 20)
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "about")
    private String about;

    @Transient
    private List<Rating> ratings;
}
