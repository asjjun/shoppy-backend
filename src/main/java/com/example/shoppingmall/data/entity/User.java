package com.example.shoppingmall.data.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@ToString
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username; // UserID, 실제 로그인하는 ID

    @Column(nullable = false)
    private String nickname; // Username, 실제 유저 이름

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String e_mail;

    @Column(nullable = false)
    private String authority;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Product> product_list = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Product> order_list = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Product> cart_list = new ArrayList<>();
}
