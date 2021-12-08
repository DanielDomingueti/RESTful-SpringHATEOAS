package com.domingueti.profile.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.ObjectNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domingueti.profile.assembler.ProfileAssembler;
import com.domingueti.profile.entity.Profile;
import com.domingueti.profile.repo.Repo;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/profile")
@AllArgsConstructor
public class Controller {

	private final Repo repo;
	private final ProfileAssembler assembler;
	
//	@GetMapping
//	public CollectionModel<EntityModel<Profile>> getAll() {
//		List<EntityModel<Profile>> profiles = repo.findAll().stream().map(profile -> EntityModel.of(profile,
//				linkTo(methodOn(Controller.class).getById(profile.getId())).withSelfRel(),
//				linkTo(methodOn(Controller.class).getAll()).withRel("profiles"))).collect(Collectors.toList());
//		return CollectionModel.of(profiles,
//				linkTo(methodOn(Controller.class).getAll()).withSelfRel());
//	}
	
	@GetMapping
	public CollectionModel<EntityModel<Profile>> getAll() {
		List<EntityModel<Profile>> profiles = repo.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(profiles, 
				linkTo(methodOn(Controller.class).getAll()).withSelfRel());
	}
	
	@GetMapping(path = "id/{id}")
	public EntityModel<Profile> getById(@PathVariable Long id) {
		Profile profile = repo.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, null));
		return assembler.toModel(profile);
	}
	
	@PostMapping(path = "/add")
	public ResponseEntity<EntityModel<Profile>> addProfile(@RequestBody Profile profile) {
		EntityModel<Profile> entityModel = assembler.toModel(repo.save(profile));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@PutMapping(path = "update/{id}")
	public ResponseEntity<EntityModel<Profile>> updateProfile(@RequestBody Profile newProfile, @PathVariable Long id) {
		Profile updatedProfile = repo.findById(id).map(profile -> {
			profile.setName(newProfile.getName());
			profile.setEmail(newProfile.getEmail());
			return repo.save(profile);
		})
			.orElseGet(() -> {
				newProfile.setId(id);
				return repo.save(newProfile);
			});
		EntityModel<Profile> entityModel = assembler.toModel(updatedProfile);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	
}
