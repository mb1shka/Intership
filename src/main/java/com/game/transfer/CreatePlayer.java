package com.game.transfer;

import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

public class CreatePlayer {

    private String name;

    private String title;

    private Race race;

    private Profession profession;

    private Long birthday;

    @Value("false")
    private Boolean banned;

    private Integer experience;

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public Race getRace() {
        return race;
    }

    public Profession getProfession() {
        return profession;
    }

    public Long getBirthday() {
        return birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public Integer getExperience() {
        return experience;
    }
}
