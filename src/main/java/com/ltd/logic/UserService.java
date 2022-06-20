package com.ltd.logic;

import com.ltd.boundaries.UserBoundary;

public interface UserService {
	public UserBoundary register(UserBoundary user);

	public UserBoundary updateUser(UserBoundary update);

	public UserBoundary resetPassword(String userEmail, String newPassword, String otk);

	public void sendKeyToEmail(String email);

	public void checkKeyMatch(String email, String otk);
}
