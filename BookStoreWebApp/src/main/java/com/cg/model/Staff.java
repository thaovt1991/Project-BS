package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "staffs")
public class Staff extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên không được trống")
    private String name;

    @NotBlank(message = "Địa chỉ không được trống")
    private String address;

    @NotBlank(message = "Số điện thoại không được trống")
    private String phone;

    @NotBlank(message = "Email không được trống")
    private String email;

    @Column(name = "user_id")
    private Long userId = null ;
}
