package br.com.inatel.faketwitch.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.inatel.faketwitch.modelo.Channel;

public class ChannelForm {

	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String name;

	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String password;

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Channel convert() {
		String encryptedPassword = new BCryptPasswordEncoder().encode(password);
		return new Channel(name, encryptedPassword);
	}

}
