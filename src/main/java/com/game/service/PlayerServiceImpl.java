package com.game.service;

import com.game.controller.PlayersController;
import com.game.entity.Player;
import com.game.repository.PlayerSpecification;
import com.game.repository.SpecificationBuilder;
import com.game.service.exceptions.PlayerInvalidField;
import com.game.service.exceptions.PlayerNotFound;
import com.game.transfer.CreatePlayer;
import com.game.transfer.UpdatePlayer;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class PlayerServiceImpl implements PlayerService {
    //тут также делаем всю обработку ошибок


    @Autowired
    PlayerRepository playerRepository;

    @Override
    @Transactional
    public List<Player> getAll(PlayersController.PlayerFilters playerFilters) {
        Specification<Player> specification = specificationFromFilters(playerFilters);

        Sort sort = Sort.by(Sort.Direction.ASC, playerFilters.getOrder());
        Pageable limit = PageRequest.of(playerFilters.getPageNumber(), playerFilters.getPageSize(), sort);
        return playerRepository.findAll(specification, limit).toList();
    }

    @Override
    public long getCount(PlayersController.PlayerFilters playerFilters) {
        Specification<Player> specification = specificationFromFilters(playerFilters);
        return playerRepository.count(specification);
    }

    private Specification<Player> specificationFromFilters(PlayersController.PlayerFilters playerFilters) {
        SpecificationBuilder<Player> builder = new SpecificationBuilder<Player>();

        if (playerFilters.getName().isPresent()) {
            builder.with(PlayerSpecification.playerNameLike(playerFilters.getName().get()));
        }
        if (playerFilters.getTitle().isPresent()) {
            builder.with(PlayerSpecification.playerTitleLike(playerFilters.getTitle().get()));
        }
        if (playerFilters.getRace().isPresent()) {
            builder.with(PlayerSpecification.playerRace(playerFilters.getRace().get()));
        }
        if (playerFilters.getProfession().isPresent()) {
            builder.with(PlayerSpecification.playerProfession(playerFilters.getProfession().get()));
        }
        if (playerFilters.getBanned().isPresent()) {
            builder.with(PlayerSpecification.playerBanned(playerFilters.getBanned().get()));
        }
        if (playerFilters.getBefore().isPresent()) {
            Date before = new Date(playerFilters.getBefore().get());
            builder.with(PlayerSpecification.playerBirthdayBefore(before));
        }
        if (playerFilters.getAfter().isPresent()) {
            Date after = new Date(playerFilters.getAfter().get());
            builder.with(PlayerSpecification.playerBirthdayAfter(after));
        }
        if (playerFilters.getMinExperience().isPresent()) {
            builder.with(PlayerSpecification.playerMinExperience(playerFilters.getMinExperience().get()));
        }
        if (playerFilters.getMaxExperience().isPresent()) {
            builder.with(PlayerSpecification.playerMaxExperience(playerFilters.getMaxExperience().get()));
        }
        if (playerFilters.getMinLevel().isPresent()) {
            builder.with(PlayerSpecification.playerMinLevel(playerFilters.getMinLevel().get()));
        }
        if (playerFilters.getMaxLevel().isPresent()) {
            builder.with(PlayerSpecification.playerMaxLevel(playerFilters.getMaxLevel().get()));
        }

        return builder.build();
    }

    @Override
    @Transactional
    public Player update(Long id, UpdatePlayer upd) throws Exception {
        Player updatePlayer = getPlayer(id);

        upd.name.ifPresent(updatePlayer::setName);

        upd.title.ifPresent(updatePlayer::setTitle);

        upd.race.ifPresent(updatePlayer::setRace);

        upd.profession.ifPresent(updatePlayer::setProfession);
        if (upd.birthday.isPresent()) {
            Date date = new Date(upd.birthday.get());
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            if (calendar.get(Calendar.YEAR) < 2000 || calendar.get(Calendar.YEAR) > 3000)
                throw new PlayerInvalidField();
            updatePlayer.setBirthday(date);
        }

        upd.banned.ifPresent(updatePlayer::setBanned);

        if (upd.experience.isPresent()) {
            if (upd.experience.get() < 0 || upd.experience.get() > 10000000) {
                throw new PlayerInvalidField();
            }
            updatePlayer.setExperience(upd.experience.get());
        }

        calculateBeforeSaving(updatePlayer);
        return playerRepository.save(updatePlayer);
    }

    @Override
    @Transactional
    public Player getPlayer(Long id) throws PlayerNotFound {
        if (id == 0) {
            throw new PlayerInvalidField();
        }
        Optional<Player> player = playerRepository.findById(id);
        if (!player.isPresent()) {
           throw new PlayerNotFound();
        }
        return player.get();
    }

    @Override
    @Transactional
    public void deletePlayer(Long id) throws PlayerNotFound {
        Player player = getPlayer(id);
        playerRepository.delete(player);
    }

    @Override
    @Transactional
    public Player save(CreatePlayer createPlayer) {
        Player newPlayer = new Player();

        if (createPlayer.getName() == null || createPlayer.getName().length() > 12) {
            throw new PlayerInvalidField();
        }
        newPlayer.setName(createPlayer.getName());


        if (createPlayer.getTitle() == null || createPlayer.getTitle().length() > 30 ) {
            throw new PlayerInvalidField();
        }
        newPlayer.setTitle(createPlayer.getTitle());


        if (createPlayer.getRace() == null) {
            throw new PlayerInvalidField();
        }
        newPlayer.setRace(createPlayer.getRace());


        if (createPlayer.getProfession() == null) {
            throw new PlayerInvalidField();
        }
        newPlayer.setProfession(createPlayer.getProfession());


        if (createPlayer.getBirthday() == null || createPlayer.getBirthday() < 0) {
            throw new PlayerInvalidField();
        }
        newPlayer.setBirthday(new Date(createPlayer.getBirthday()));


        if (createPlayer.getBanned() == null) {
            throw new PlayerInvalidField();
        }
        newPlayer.setBanned(createPlayer.getBanned());


        if (createPlayer.getExperience() > 10000000) {
            throw new PlayerInvalidField();
        }
        newPlayer.setExperience(createPlayer.getExperience());

        calculateBeforeSaving(newPlayer);

        return playerRepository.save(newPlayer);
    }

    private void calculateBeforeSaving(Player player) {
        int level; //текущий опыт персонажа
        int expBeforeNextLevel; //опыт до следующего уровня персонажа
        int exp = player.getExperience();

        level = ( ( (int) Math.sqrt(2500 + 200 * exp) ) - 50 ) / 100;

        expBeforeNextLevel = 50 * (level + 1) * (level + 2) - exp;

        player.setLevel(level);
        player.setUntilNextLevel(expBeforeNextLevel);
    }
}
