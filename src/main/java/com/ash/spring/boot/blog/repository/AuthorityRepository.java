package com.ash.spring.boot.blog.repository;

import com.ash.spring.boot.blog.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
