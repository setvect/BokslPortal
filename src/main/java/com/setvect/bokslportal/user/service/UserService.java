package com.setvect.bokslportal.user.service;

import com.setvect.bokslportal.BokslPortalConstant;
import com.setvect.bokslportal.user.repository.UserRepository;
import com.setvect.bokslportal.user.vo.RoleName;
import com.setvect.bokslportal.user.vo.UserRoleVo;
import com.setvect.bokslportal.user.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	/** 암호화 인코더. */
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void init() {
		UserVo user = userRepository.findById(BokslPortalConstant.Login.ID).orElse(null);
		if (user != null) {
			return;
		}
		// 계정이 없으면, 기본 계정 생성
		user = new UserVo();
		user.setName("복슬이");
		user.setDeleteFlag(false);
		user.setUserId(BokslPortalConstant.Login.ID);
		user.setPassword(passwordEncoder.encode("boksl"));

		UserRoleVo role = new UserRoleVo();
		role.setRoleName(RoleName.ROLE_ADMIN);
		role.setUser(user);
		user.setUserRole(new HashSet<>(Arrays.asList(role)));
		userRepository.save(user);
	}
}
