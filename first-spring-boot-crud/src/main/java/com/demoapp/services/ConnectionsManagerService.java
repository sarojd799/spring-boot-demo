package com.demoapp.services;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.search.IntegerComparisonTerm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.demoapp.dto.UserDetailsDTO;
import com.demoapp.repos.UserDetailsRepository;

@Service
public class ConnectionsManagerService {

	
	@Value("${app.timeout}")
	private Integer timeout;
	
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	public boolean saveNewCollection(Integer userId, UserDetailsDTO u) {
	
		if(userId ==  null || u == null) return false;
		
		List<UserDetailsDTO> users = userDetailsRepository.findByUserDetailsIdIn(Arrays.asList(userId, u.getUserDetailsId()))
				.stream()
				.collect(Collectors.toList());
		
		if(users.size() > 1) {
			users.get(0).getConnections().add(users.get(1));
			users.get(1).getConnections().add(users.get(0));
			userDetailsRepository.saveAll(users);
			return true;
		}
		return false;	
	}
	
	public Set<UserDetailsDTO> getAllConnectionsOfUser(Integer userId) {
		Optional<UserDetailsDTO> user = userDetailsRepository.findById(userId);
		if(user.isPresent()) {
			Set<UserDetailsDTO> connections = user.get().getConnections().stream().filter(c-> {
				return c.getUserDetailsId() != userId;
			}).collect(Collectors.toSet());
			
			connections.forEach(connection-> {			
				boolean isOnline = ActiveUserService.existsUser(connection.getEmail());
				if(isOnline) {
			    	connection.setStatus("ONLINE");
			    } else  {
			    	connection.setStatus("OFFLINE");
			    } 
			});
			
			return connections;
		}
		return new HashSet<UserDetailsDTO>();
	}

	
	public Set<UserDetailsDTO> getAllUsersWithKeyword(Integer id, String keyword) {
	       if(id == null || keyword == null || (keyword != null && keyword.trim().length() ==0)) {
	    	   throw new RuntimeException("Invalid argument");
	       } else {
	    	   List<UserDetailsDTO> users = userDetailsRepository.findByEmailContaining(keyword).stream().collect(Collectors.toList());
	    	   users.sort((a,b)-> b.getEmail().compareToIgnoreCase(a.getEmail()));
	    	   
	    	   if(users.size() > 0) {
	    		   UserDetailsDTO reqUser = userDetailsRepository.findByUserDetailsId(id);
	    		   if(reqUser != null) {
	    			   Set<Integer> connectionsId = reqUser.getConnections().stream().map(u-> u.getUserDetailsId()).collect(Collectors.toSet());
	    			   users.forEach(u-> {
	    				   if(connectionsId.contains(u.getUserDetailsId())) {
	    					   u.setConnection(true);
	    				   }
	    			   });
	    			   return users.stream().collect(Collectors.toSet());
	    		   }
	    		   return new HashSet<UserDetailsDTO>();
	    	   }
	    	   return new HashSet<UserDetailsDTO>();
	       }
	}
	
}
