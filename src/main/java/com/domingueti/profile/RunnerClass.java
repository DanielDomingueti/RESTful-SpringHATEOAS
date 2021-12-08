package com.domingueti.profile;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.domingueti.profile.entity.Profile;
import com.domingueti.profile.repo.Repo;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RunnerClass implements CommandLineRunner{

	private final Repo repo;
	
	@Override
	public void run(String... args) throws Exception {

		
		Profile p = new Profile(null, "Josh", "josh@gmail.com");
		Profile p1 = new Profile(null, "Gabriel", "gabriel@gmail.com");
		Profile p2 = new Profile(null, "Daniel", "daniel@gmail.com");

		repo.save(p);
		repo.save(p1);
		repo.save(p2);

		
	}

}
