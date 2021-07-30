package br.com.inatel.faketwitch.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.inatel.faketwitch.modelo.Channel;
import br.com.inatel.faketwitch.repository.ChannelRepository;

@Service
public class AuthenticationService implements UserDetailsService {
	
	@Autowired
	private ChannelRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Channel> channel = repository.findByName(username);
		if (channel.isPresent()) {
			return channel.get();
		}
		
		throw new UsernameNotFoundException("Invalid User!");
	}

}
