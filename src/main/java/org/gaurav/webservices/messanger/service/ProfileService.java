package org.gaurav.webservices.messanger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.gaurav.webservices.messanger.database.DatabaseClass;
import org.gaurav.webservices.messanger.model.Profile;

public class ProfileService {
	
	private Map<String, Profile> profiles = DatabaseClass.getProfiles();

	public ProfileService() {
		profiles.put("Mohan", new Profile("Mohankj", "Mohan", "Shakya"));
		profiles.put("Sonu", new Profile("Sonupj", "Sonu", "Bajrangi"));
	}
	
	public List<Profile> getAllProfile(){
		return new ArrayList<>(profiles.values());
	}
	
	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}
	
	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size()+1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile updateProfile(Profile profile) {
		
		/*if(profile.getId() == 0) {
			return null;
		}*/
		profiles.put(profile.getProfileName(), profile);
		return profile;
		
	}
	
	public Profile removeProfile(String profileName) {
		return profiles.remove(profileName);
		
	}
	

}
