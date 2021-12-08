package com.domingueti.profile.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domingueti.profile.entity.Profile;

public interface Repo extends JpaRepository<Profile, Long> {

}
