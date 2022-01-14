package com.cg.controller.cp;

import com.cg.model.Role;
import com.cg.model.Staff;
import com.cg.model.User;
import com.cg.service.role.RoleService;
import com.cg.service.staff.StaffService;
import com.cg.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("cp/users")
public class UserCPController {

    @Autowired
    private UserService userService ;

    @Autowired
    private StaffService staffService ;

    @Autowired
    private RoleService roleService;

    @GetMapping("")
    public ModelAndView showAllUser(){
        ModelAndView modelAndView = new ModelAndView("cp/user/list") ;
        List<User> userList = userService.findAllByDeletedIsFalse() ;
        modelAndView.addObject("userList",userList);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createUser(){
        ModelAndView modelAndView = new ModelAndView("cp/user/create") ;
        List<Role> roleList = roleService.findAllByIdIsNot(3L);// 3 la id Customer

        List<Staff> staffList = staffService.findAllByDeletedIsFalseAndUserIdIsNull();

        modelAndView.addObject("roleList",roleList);
        modelAndView.addObject("user", new User());
        modelAndView.addObject("staffList", staffList);

        return modelAndView ;
    }

}
