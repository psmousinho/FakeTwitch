package br.com.inatel.faketwitch.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String genre;
	@ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Channel> followers = new ArrayList<>();
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	private List<LiveStream> streams = new ArrayList<>();

	public Category() {
		super();
	}

	public Category(String name, String genre) {
		this.name = name;
		this.genre = genre;
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

	public void setName(String name) {
		this.name = name;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public List<Channel> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Channel> followers) {
		this.followers = followers;
	}

	public List<LiveStream> getStreams() {
		return streams;
	}

	public void setStreams(List<LiveStream> streams) {
		this.streams = streams;
	}

}
