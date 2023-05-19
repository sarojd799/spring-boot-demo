package com.demoapp.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demoapp.dto.ChatsDTO;

public interface ChatRepository extends JpaRepository<ChatsDTO,Integer> {

}
