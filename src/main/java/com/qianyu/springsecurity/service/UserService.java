package com.qianyu.springsecurity.service;

import java.nio.CharBuffer;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.qianyu.springsecurity.dto.LoginDto;
import com.qianyu.springsecurity.dto.UserDto;
import com.qianyu.springsecurity.entity.UserEntity;
import com.qianyu.springsecurity.exception.AppException;
import com.qianyu.springsecurity.mapper.UserMapper;
import com.qianyu.springsecurity.repository.UserRepository;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class UserService {

	private JwtEncoder jwtEncoder;
	
	private AuthenticationManager authenticationManager;
	
	private final UserRepository userRepository;

	private final PasswordEncoder encoder;

	private final UserMapper userMapper;

	public UserDto addUser(UserEntity user) {

		Optional<UserEntity> oUser = userRepository.findByName(user.getName());


		if (oUser.isPresent()) {

			throw new AppException("username is already used.", HttpStatus.BAD_REQUEST);
			
		} 

		user.setPassword(
				encoder.encode(user.getPassword())
				);
		
		userRepository.save(user);
		
		UserDto userDto = userMapper.toUserDto(user);
		
		userDto.setToken(getJwt(user.getName(), user.getRoles()));

		return userDto;

	}

	public UserDto login(LoginDto loginDto) {
		
		UserEntity user = userRepository.findByName(loginDto.name())
				.orElseThrow(()->new AppException("user doesn't exists", HttpStatus.NOT_FOUND));
		
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
						loginDto.name(), 
						CharBuffer.wrap(loginDto.password())))
				;
		
		if (!authentication.isAuthenticated()) {
			throw new AppException("user password incorrect", HttpStatus.BAD_REQUEST);
		}
		
		String roles= authentication.getAuthorities()
                .stream().map(auth->auth.getAuthority())
                .collect(Collectors.joining(" "));
		
        
        String jwtValue = getJwt(authentication.getName(),roles);
        
		
		
		UserDto userDto = userMapper.toUserDto(user);
		
        userDto.setToken(jwtValue);
        
        return userDto;
//		UserEntity user = userRepository.findByName(loginDto.name())
//		.orElseThrow(()->new AppException("user doesn't exists", HttpStatus.NOT_FOUND));
//		
//		if (encoder.matches(CharBuffer.wrap(loginDto.password()),user.getPassword())
//				) {
//			return userMapper.toUserDto(user);
//		}
//		
//		throw new AppException("password incorrect", HttpStatus.BAD_REQUEST);
//		
	}
	
	public String getJwt(String name, String roles) {
		Instant now = Instant.now();
		
		JwtClaimsSet jwtClaimsSet=JwtClaimsSet.builder()
                .issuedAt(now)
                .subject(name)
                .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                .claim("roles",roles)
                .build();
        JwtEncoderParameters jwtEncoderParameters=
                JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS512).build(),
                        jwtClaimsSet
                );
        Jwt jwt = jwtEncoder.encode(jwtEncoderParameters);
        
        return jwt.getTokenValue();
	}

}
