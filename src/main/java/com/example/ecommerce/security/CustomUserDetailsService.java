//package com.example.ecommerce.security;
//
//
//import java.util.stream.Collectors;
// 
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import org.springframework.security.core.userdetails.UserDetails;
//
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import org.springframework.stereotype.Service;
// 
//import com.example.lbms.model.User;
//
//import com.example.lbms.repository.UserRepository;
// 
//@Service
//
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository repo;
//
//    public CustomUserDetailsService(UserRepository repo) { this.repo = repo; }
// 
//    @Override
//
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        User user = repo.findByUsername(username)
//
//              .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        return new org.springframework.security.core.userdetails.User(
//
//            user.getUsername(),
//
//            user.getPassword(),
//
//            user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
//
//        );
//
//    }
//
//}
//
// 
