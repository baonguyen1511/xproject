/**
 * 
 */
package com.xproject.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xproject.demo.models.ERole;
import com.xproject.demo.models.Role;

/**
 * @author bao.nguyentx
 *
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
