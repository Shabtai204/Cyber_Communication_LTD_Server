package com.ltd.data.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.ltd.data.CustomerEntity;

public interface CustomerDao extends PagingAndSortingRepository<CustomerEntity,String> {
}
