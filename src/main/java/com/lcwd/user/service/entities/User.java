package com.lcwd.user.service.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "microservices_user")
public class User {
    @Id
    @Column
    private String userId;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String about;

    @Transient
    private List<Rating> ratings = new ArrayList<>();



}
