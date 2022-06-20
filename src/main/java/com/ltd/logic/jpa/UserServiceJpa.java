package com.ltd.logic.jpa;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ltd.boundaries.UserBoundary;
import com.ltd.configurations.PasswordChecks;
import com.ltd.data.UserEntity;
import com.ltd.data.dao.UserDao;
import com.ltd.logic.EmailService;
import com.ltd.logic.OtpService;
import com.ltd.logic.UserService;
import com.ltd.logic.converters.JsonConverter;
import com.ltd.logic.converters.UserEntityConverter;
import com.ltd.utilities.AlreadyExistingException;
import com.ltd.utilities.BadRequestException;
import com.ltd.utilities.NotFoundException;
import com.ltd.utilities.Validations;

@Service
public class UserServiceJpa implements UserService {

	private UserDao userDao;
	private Validations utils;
	private EmailService emailService;
	private OtpService otpService;
	private UserEntityConverter userEntityConverter;
	private PasswordChecks passwordChecks;
	private PasswordEncoder passwordEncoder;
	private JsonConverter jsConverter;

	public UserServiceJpa() {
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setUtils(Validations utils) {
		this.utils = utils;
	}

	@Autowired
	public void setUserEntityConverter(UserEntityConverter userEntityConverter) {
		this.userEntityConverter = userEntityConverter;
	}

	@Autowired
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	@Autowired
	public void setOtpService(OtpService otpService) {
		this.otpService = otpService;
	}

	@Autowired
	public void setPasswordChecks(PasswordChecks passwordChecks) {
		this.passwordChecks = passwordChecks;
	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Autowired
	public void setJsConverter(JsonConverter jsConverter) {
		this.jsConverter = jsConverter;
	}

	@Override
	@Transactional
	public UserBoundary register(UserBoundary user) {
		utils.assertNull(user);
		// utils.assertValidEmail(user.getEmail());
		// utils.assertNull(user.getPassword());

		Optional<UserEntity> existingUser = userDao.findById(user.getEmail());
		if (existingUser.isPresent())
			throw new AlreadyExistingException("Email already in the database");

		if (!passwordChecks.checkMail(user.getEmail()))
			throw new BadRequestException("Invalid email");
		if (!passwordChecks.checkPassword(user.getPassword()))
			throw new BadRequestException("Invalid password");
		if (passwordChecks.isPasswordInDictionary(user.getPassword()))
			throw new BadRequestException("Password appeared in dictionary");

		String hashedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashedPassword);
		user.setCreationTime(new Date());

		UserEntity userEntity = userEntityConverter.fromBoundary(user);
		userDao.save(userEntity);
		return user;
	}

	@Override
	@Transactional
	public UserBoundary updateUser(UserBoundary update) {
		utils.assertNull(update);
		utils.assertNull(update.getPassword());
		UserEntity existingUser = userDao.findById(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new NotFoundException("Could not find user by id"));

		String newPassword = update.getPassword();

		if (existingUser.getPassword() != null && newPassword != null) {
			if (!passwordEncoder.matches(update.getOptionalPassword(), existingUser.getPassword()))
				throw new BadRequestException("Failed to verify old password");
			if (passwordChecks.isPasswordInHistory(newPassword, existingUser))
				throw new BadRequestException("Password in history");
			if (passwordChecks.isPasswordInDictionary(newPassword))
				throw new BadRequestException("Password appeared in dictionary");
			if (!passwordChecks.checkPassword(newPassword))
				throw new BadRequestException("Invalid password");
			existingUser.updatePassword(passwordEncoder.encode(newPassword), jsConverter);
		} else {
			throw new BadRequestException("Passwords are null");
		}
		userDao.save(existingUser);
		return userEntityConverter.toBoundary(existingUser);
	}

	@Override
	@Transactional
	public void sendKeyToEmail(String email) {
		utils.assertValidEmail(email);
		userDao.findById(email).orElseThrow(() -> new NotFoundException("Could not find user by id"));
		String randOtk = otpService.generateRandomPassword();
		String hashRandOtk = otpService.generateHash(randOtk);
		if (otpService.newResetRequest(email, hashRandOtk))
			emailService.sendEmail(email, hashRandOtk, "Key for reset");
	}

	@Override
	@Transactional
	public void checkKeyMatch(String email, String otk) {
		utils.assertNull(otk);
		utils.assertValidEmail(email);

		if (!otpService.authenticate(email, otk))
			throw new BadRequestException("Failed to verify otk");
	}

	@Override
	@Transactional
	public UserBoundary resetPassword(String email, String newPassword, String otk) {
		utils.assertValidEmail(email);
		utils.assertNull(newPassword);
		utils.assertNull(otk);

		UserEntity existingUser = userDao.findById(email)
				.orElseThrow(() -> new NotFoundException("Could not find user by email: " + email));

		if (passwordChecks.isPasswordInHistory(newPassword, existingUser))
			throw new BadRequestException("Password in history");
		if (passwordChecks.isPasswordInDictionary(newPassword))
			throw new BadRequestException("Password appeared in dictionary");
		if (!passwordChecks.checkPassword(newPassword))
			throw new BadRequestException("Invalid password");

		if (!otpService.authenticate(email, otk))
			throw new BadRequestException("Failed to verify otk");

		otpService.removeResetRequest(email);

		String hashedPassword = passwordEncoder.encode(newPassword);
		existingUser.updatePassword(hashedPassword, jsConverter);
		userDao.save(existingUser);

		return userEntityConverter.toBoundary(existingUser);
	}
}
