package com.ltd.data.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ltd.data.UserEntity;

public interface UserDao extends PagingAndSortingRepository<UserEntity, String> {
}