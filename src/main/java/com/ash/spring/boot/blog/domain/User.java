package com.ash.spring.boot.blog.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User 实体.
 */
@Entity // 实体
public class User implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Id // 主键
	@GeneratedValue(strategy=GenerationType.IDENTITY) // 自增长策略
	private Long id; // 实体一个唯一标识

	@NotEmpty(message = "姓名不能为空")
	@Size(min = 2, max = 20)
	@Column(nullable = false, length = 20) // 映射为字段，值不能为空
	private String name;

	@NotEmpty(message = "邮箱不能为空")
	@Size(max = 50)
	@Email(message = "邮箱格式不对")
	@Column(nullable = false, length = 50, unique = true)
	private String email;

	@NotEmpty(message = "账号不能为空")
	@Size(min = 3, max = 20)
	@Column(nullable = false, length = 20, unique = true)
	private String username; // 用户账号，用户登录时的唯一标识

	@NotEmpty(message = "密码不能为空")
	@Size(max = 100)
	@Column(length = 100)
	private String password; // 登录时密码

	@Column(length = 200)
	private String avatar; // 头像图片地址

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private List<Authority> authorities;

	protected User() { // 无参构造函数；设为 protected 防止直接使用
	}
	
	public User(String name, String email, String username, String password) {
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
	    return name;
    }
	@Override
	public String getUsername() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return String.format("User [id=%d, name='%s', username='%s', email='%s']", id, name, username, email);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 需将List<Authority>转成List<SimpleGrantedAuthority>,否则前端拿不到角色列表名称
		List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

		for (GrantedAuthority authority : this.authorities) {
			simpleGrantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
		}
		return simpleGrantedAuthorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
    public String getPassword() {
	    return "";
    }
}
