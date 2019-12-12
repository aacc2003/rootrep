package com.xxxxx.app;

import com.xxxxx.util.StringUtils;

public class App {
	
	public static final String SPRING_PROFILE_ACTIVE = "spring.profiles.active";

	public static void setProfileIfNotExists(String profile) {
		if (!StringUtils.hasLength(System.getProperty(SPRING_PROFILE_ACTIVE))
			&& !System.getenv().containsKey("SPRING_PROFILES_ACTIVE")) {
			System.setProperty(SPRING_PROFILE_ACTIVE, profile);
		}
	}
}
