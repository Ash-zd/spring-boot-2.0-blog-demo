package com.ash.spring.boot.blog.service;

import com.ash.spring.boot.blog.domain.Authority;

import java.util.Optional;

public interface AuthorityService {
    /**
     * 根据ID查询Authority
     * @param id
     * @return
     */
    Optional<Authority> getAuthorityById(Long id);
}
