package com.demoapp.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;


@Service
public class ActiveUserService {
  
	private static Map<String,String> activeUsers = new HashMap<String,String>();;
	
    
	public static void addUser(String userName, String principal) {
		System.out.println("act-users-bef"+activeUsers);
		System.out.println("act-users-bef-add"+userName);
		activeUsers.put(userName, principal);
		System.out.println("act-users-af"+activeUsers);
	}
	
	public void addAllUser(Map<String,String> activeSessionData) {
		activeUsers.putAll(activeSessionData);
	}
	
	public void removeUser(String userName) {
		activeUsers.remove(userName);
	}
	
	public static boolean existsUser(String userName) {
		return activeUsers.keySet().contains(userName);
	}
	
	public static String getPrincipal(String userName) {
		System.out.println(userName);
		System.out.println("act-users-get"+activeUsers);
		return activeUsers.get(userName);
	}
	
	public String getUserName(String principal) {
	    Optional<Map.Entry<String,String>> o = activeUsers.entrySet().stream().filter(e-> e.getValue().equals(principal)).findFirst();
	    if(o.isPresent()) {
	    	return o.get().getKey();
	    }
	    return null;
	}
	
	public static void removeUserByPrincipal(String principal) {
		if(principal != null && !principal.isEmpty()) {
			System.out.println("principal is "+principal+ " and map is "+activeUsers);
			activeUsers.entrySet().removeIf((entry)-> entry.getValue().equals(principal));
		}
	}
	
	public static Map<String, String> getAllRegisteredUsers() {
		return activeUsers;
	}
}
