//package com.example.jwtService.repository;
//
//import java.util.Optional;
//import org.springframework.data.jpa.repository.JpaRepository;
//import com.example.jwtService.Entity.Role;
//import com.example.jwtService.enumm.RoleName;
//
//public interface RoleRepository extends JpaRepository<Role, Long> {
//    Optional<Role> findByName(RoleName name);
//}