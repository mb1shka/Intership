package com.game.transfer;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.Optional;


public class UpdatePlayer {
    public Optional<String> name = Optional.empty();
    @JsonSetter("name")
    private void _setName(String name) {
        this.name = Optional.of(name);
    }


    public Optional<String> title = Optional.empty();
    @JsonSetter("title")
    private void _setTitle(String title) {
        this.title = Optional.of(title);
    }


    public Optional<Race> race = Optional.empty();
    @JsonSetter("race")
    private void _setRace(Race race) {
        this.race = Optional.of(race);
    }


    public Optional<Profession> profession = Optional.empty();
    @JsonSetter("profession")
    private void _setProfession(Profession profession) {
        this.profession = Optional.of(profession);
    }


    public Optional<Long> birthday = Optional.empty();
    @JsonSetter("birthday")
    private void _setBirthday(Long birthday) {
        this.birthday = Optional.of(birthday);
    }


    public Optional<Boolean> banned = Optional.empty();
    @JsonSetter("banned")
    private void _setBanned(Boolean banned) {
        this.banned = Optional.of(banned);
    }


    public Optional<Integer> experience = Optional.empty();
    @JsonSetter("experience")
    private void _setExperience(Integer experience) {
        this.experience = Optional.of(experience);
    }



}
