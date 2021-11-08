package com.crypto.helper;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Component
@Data
@Getter
@Setter
public class ApiDetails {
//	private String exchangeName;
	private Map<String, String> api;
}
