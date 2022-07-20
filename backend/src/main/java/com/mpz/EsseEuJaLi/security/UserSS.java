package com.mpz.EsseEuJaLi.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mpz.EsseEuJaLi.model.enums.Role;

public class UserSS implements UserDetails{
	private static final long serialVersionUID = 2073973497172946799L;

	private Long id;
	private String name;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserSS() {
	}
	
	public UserSS(Long id, String name, String password, Set<Role> roles) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.authorities = roles.stream().map(x -> new SimpleGrantedAuthority(x.getDescription())).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return name;
	}

	// Não aplicável no momento!
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

	public boolean hasRole(Role role) {
		return getAuthorities().contains(new SimpleGrantedAuthority(role.getDescription()));
	}
}
