package com.cg.model.dto;

import com.cg.model.Customer;
import com.cg.model.Staff;
import com.cg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserDTO {

    private Long id;

    @NotBlank(message = "The email is required")
    @Email(message = "The email address is invalid")
    @Size(max = 50, message = "The length of email must be between 5 and 50 characters")
    private String username;

    @NotBlank(message = "The password is required")
    @Size(max = 30, message = "Maximum password length 30 characters")
    private String password;



    @Valid
    private RoleDTO role;

    @NotBlank(message = "Tên không được trống")
    private String name;

    private String address;

    private String phone;



    private Long staffId;


    public UserDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Customer toCustomer(User user){
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(username);
        customer.setAddress(address);
        customer.setPhone(phone);
        customer.setUser(user);
        return customer ;
    }



    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role.toRole());
        return user;
    }

}
