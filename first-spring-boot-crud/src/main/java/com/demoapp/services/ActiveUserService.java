package com.demoapp.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ActiveUserService {
  
	private static Map<String,String> activeUsers = new HashMap<String,String>();
	
    
	public void addUser(String userName, String principal) {
		activeUsers.put(userName, principal);
	}
	
	public void addAllUser(Map<String,String> activeSessionData) {
		activeUsers.putAll(activeSessionData);
	}
	
	public void removeUser(String userName) {
		activeUsers.remove(userName);
	}
	
	public boolean existsUser(String userName) {
		return activeUsers.keySet().contains(userName);
	}
	
	public String getPrincipal(String userName) {
		return activeUsers.get(userName);
	}
	
	public String getUserName(String principal) {
	    Optional<Map.Entry<String,String>> o = activeUsers.entrySet().stream().filter(e-> e.getValue().equals(principal)).findFirst();
	    if(o.isPresent()) {
	    	return o.get().getKey();
	    }
	    return null;
	}
	
	public void removeUserByPrincipal(String principal) {
		if(principal != null && !principal.isEmpty()) {
			System.out.println("principal is "+principal+ " and map is "+activeUsers);
			activeUsers.entrySet().removeIf((entry)-> entry.getValue().equals(principal));
		}
	}
	
	public Map<String, String> getAllRegisteredUsers() {
		return activeUsers;
	}
}
