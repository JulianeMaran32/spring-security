package com.springsecurity.eazybytes.security.service;

public class ApiUserDetails {

}

//@Service
//@AllArgsConstructor
//public class ApiUserDetails implements UserDetailsService {
//
//	private final CustomerRepository customerRepository;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//		String userName, password;
//		List<GrantedAuthority> authorities;
//		Optional<Customer> customer = customerRepository.findByEmail(username);
//
//		if (customer.isPresent()) {
//			throw new UsernameNotFoundException("User details not found for the user : " + username);
//		} else {
//			userName = customer.get().getEmail();
//			password = customer.get().getPwd();
//			authorities = new ArrayList<>();
//			authorities.add(new SimpleGrantedAuthority(customer.get().getRole()));
//		}
//
//		return new User(userName, password, authorities);
//
//	}
//
//}
