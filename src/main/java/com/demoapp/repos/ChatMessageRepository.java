package com.demoapp.repos;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demoapp.dto.MessageDTO;

public interface ChatMessageRepository extends JpaRepository<MessageDTO, Integer> {
	
	
	@Query("Select m from message_entity m where m.senderId = :senderId and m.receipentId = :receipentId or m.senderId = :receipentId and m.receipentId = :senderId order by m.timeStamp")
	public Set<MessageDTO> findBySenderIdAndReceipentId(@Param("senderId") Integer senderId,@Param("receipentId") Integer receipentId);

}
