package org.vlog.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.vlog.entity.UserEntity;
import org.vlog.exception.custom.GlobalNotFoundException;
import org.vlog.repository.UserRepository;

@Service("userDetailsServicesImpl")
@AllArgsConstructor
public class UserDetailsServicesImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByName(name).orElseThrow(() -> new GlobalNotFoundException("There isn`t any user with name: " + name));
        return SecurityUser.fromUser(user);
    }
}
