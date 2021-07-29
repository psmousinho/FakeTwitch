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
public class AutenticationService implements UserDetailsService {
	
	@Autowired
	private ChannelRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Channel> usuario = repository.findByName(username);
		if (usuario.isPresent()) {
			return usuario.get();
		}
		
		throw new UsernameNotFoundException("Dados inválidos!");
	}

}
