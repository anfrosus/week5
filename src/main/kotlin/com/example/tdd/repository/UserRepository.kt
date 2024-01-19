package com.example.tdd.repository

import com.example.tdd.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface UserRepository: JpaRepository<User, Long> {

}
