package com.bookstore.bookstore.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    USER(Set.of(Permission.USER_READ)),
    ADMIN(Set.of(Permission.ADMIN_DELETE, Permission.ADMIN_CREATE, Permission.ADMIN_UPDATE, Permission.ADMIN_READ, Permission.USER_READ));

    @Getter
    private final Set<Permission> permissionSet;

    public List<SimpleGrantedAuthority> authorities() {
        List<SimpleGrantedAuthority> grantedAuthorities = permissionSet.stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toList());
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return grantedAuthorities;
    }

}
