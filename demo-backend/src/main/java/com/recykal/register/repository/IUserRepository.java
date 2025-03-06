package com.recykal.register.repository;

import com.recykal.register.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User,String> {

}
