package com.domingueti.profile.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.domingueti.profile.controller.Controller;
import com.domingueti.profile.entity.Profile;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProfileAssembler implements RepresentationModelAssembler<Profile, EntityModel<Profile>> {

	@Override
	public EntityModel<Profile> toModel(Profile profile) {
		return EntityModel.of(profile, linkTo(methodOn(Controller.class).getById(profile.getId())).withSelfRel(),
				linkTo(methodOn(Controller.class).getAll()).withRel("profiles"));
	}
}
