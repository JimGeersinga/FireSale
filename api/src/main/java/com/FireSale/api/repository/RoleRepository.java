package com.FireSale.api.repository;

import com.FireSale.api.model.Address;
import com.FireSale.api.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}