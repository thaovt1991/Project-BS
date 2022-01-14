package com.cg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;

    @OneToMany(targetEntity = User.class, fetch = FetchType.EAGER)
    private List<User> users;

//    @JsonIgnore
//    @OneToMany(targetEntity = User.class, mappedBy = "user")
//    private List<User> users;

}
