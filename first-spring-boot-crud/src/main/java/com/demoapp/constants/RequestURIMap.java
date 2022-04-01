package com.demoapp.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class RequestURIMap {
	
	public static final String loginURI = "/login";
	
	public static final String regUserCheckURI = "/api/existsUserWithName";
	
	public static final String registerURI = "/registerNewUser";
	
	public static final String webSocketURI = "/ws";
	
	public static final String webSocketInfoURI = "/ws/info";
	
	
//	public static String[] getNoAuthURIs() {
//		Field[] declaredFields = RequestURIMap.class.getDeclaredFields();
//		Class<RequestURIMap> types = RequestURIMap.class;
//		List<String> staticFields = new ArrayList<String>();
//		
//		for (Field field : declaredFields) {
//		    if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
//		        try {
//					staticFields.add(field.get(types).toString());
//				} catch (IllegalArgumentException e) {
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				}
//		    }
//		}
//		System.out.println(staticFields);
//		return (String[]) staticFields.stream().map(v-> v.toString()).toArray();
//	}

	public static final Map<String, String> noAuthRequiredURI = Collections
			.unmodifiableMap(
					Map.ofEntries(
							entry("base", "/"),
							entry("loginURI", "/login"), 
							entry("registerURI", "/registerNewUser"), 
							entry("regEmailCheckURI", "/api/existsUserWithName")));
	
	public static final String[] getAllAuthURIVal() {
		String[] uriVals = new String[RequestURIMap.noAuthRequiredURI.size()];
		for(int i =0; i < RequestURIMap.noAuthRequiredURI.values().size(); i++) {
			uriVals[i] = (String) RequestURIMap.noAuthRequiredURI.values().toArray()[i];
		}
		return uriVals;
	}
}
