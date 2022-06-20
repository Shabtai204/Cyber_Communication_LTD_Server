package com.ltd.logic.converters;

import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonConverter {

	private ObjectMapper jackson;
	
	@Autowired
	public void setJackson(ObjectMapper jackson) {
		this.jackson = jackson;
	}
	
	public String stackToJSON(Stack<String> value) {
		try {
			return this.jackson.writeValueAsString(value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Stack<String> JSONToStack(String json) {
		try {
			return this.jackson.readValue(json, Stack.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
