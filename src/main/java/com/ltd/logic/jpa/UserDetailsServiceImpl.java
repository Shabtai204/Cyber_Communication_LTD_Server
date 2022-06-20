package com.ltd.logic.jpa;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ltd.data.UserEntity;
import com.ltd.data.dao.UserDao;
import com.ltd.configurations.PasswordConfig;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserDao userDao;

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserEntity> ue = userDao.findById(username);

		if (ue.isEmpty()) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		UserEntity user = ue.get();

		if (!user.tryLogin(user)) {
			userDao.save(user);
			throw new UsernameNotFoundException("Max number of times exceeded");
		}
		if (user.getLoginTries() == PasswordConfig.MAX_TRIES_LOGIN) {
			user.setForbidLoginTime(LocalTime.now().plusMinutes(UserEntity.minsBetweenLogins));
			user.setLoginTries(0);
		} else
			user.resetForbidLoginUntil();
		userDao.save(user);

		ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("User"));

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword().toString(),
				grantedAuthorities);
	}

}
