package br.com.inatel.faketwitch.modelo;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Connection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Channel owner;
	@ManyToOne
	private Channel target;
	private LocalDate startDate = LocalDate.now();
	@Enumerated(EnumType.STRING)
	private ConnectionStatus status = ConnectionStatus.FOLLOW;
	
	public Connection() {
		super();
	}
	
	public Connection(Channel owner, Channel target) {
		this.owner = owner;
		this.target = target;
	}
	
	public Connection(Channel owner, Channel target, ConnectionStatus status) {
		this.owner = owner;
		this.target = target;
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Channel getOwner() {
		return owner;
	}

	public void setOwner(Channel owner) {
		this.owner = owner;
	}

	public Channel getTarget() {
		return target;
	}

	public void setTarget(Channel target) {
		this.target = target;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public ConnectionStatus getStatus() {
		return status;
	}

	public void setStatus(ConnectionStatus status) {
		this.status = status;
	}

}
