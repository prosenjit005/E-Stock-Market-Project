package com.estockmarket.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estockmarket.auth.exception.JwtTokenMalformedException;
import com.estockmarket.auth.exception.JwtTokenMissingException;
import com.estockmarket.auth.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class StockmarketAuthController {

	private static final Logger logger = LoggerFactory.getLogger(StockmarketAuthController.class);

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/test")
	public String test() {
		logger.info("StockmarketAuthController endpoint hit success !!!");
		return "StockmarketAuthController endpoint hit success !!!";
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody String userName) {
		String token = jwtUtil.generateToken(userName);
		return new ResponseEntity<String>(token, HttpStatus.OK);
	}

	@PostMapping("/validateToken")
	public ResponseEntity<?> signIn(@RequestParam String token)
			throws JwtTokenMalformedException, JwtTokenMissingException {
		logger.info("Trying to validate token {}", token);
		jwtUtil.validateToken(token);
		return (ResponseEntity<?>) ResponseEntity.ok();
	}

}
