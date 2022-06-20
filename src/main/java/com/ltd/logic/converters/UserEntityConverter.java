package com.ltd.logic.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ltd.boundaries.UserBoundary;
import com.ltd.data.UserEntity;

@Component
public class UserEntityConverter {

	private JsonConverter jsConverter;
	
	@Autowired
	public void setJsConverter(JsonConverter jsConverter) {
		this.jsConverter = jsConverter;
	}
	
	public UserBoundary toBoundary(UserEntity entity) {
		UserBoundary userBoundary = new UserBoundary();
		userBoundary.setOldPasswords(jsConverter.JSONToStack(entity.getOldPasswords()));
		userBoundary.setCreationTime(entity.getCreationTime());
		userBoundary.setPassword(entity.getPassword());
		userBoundary.setEmail(entity.getEmail());
		return userBoundary;
	}

	public UserEntity fromBoundary(UserBoundary boundary) {
		UserEntity userEntity = new UserEntity();
		userEntity.setOldPasswords(jsConverter.stackToJSON(boundary.getOldPasswords()));
		userEntity.setCreationTime(boundary.getCreationTime());
		userEntity.setPassword(boundary.getPassword());
		userEntity.setEmail(boundary.getEmail());
		return userEntity;
	}


}
