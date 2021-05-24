package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import com.game.transfer.CreatePlayer;
import com.game.transfer.UpdatePlayer;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
//аннотация - контроллер, который управляет Rest запросами и ответами
@RequestMapping("/rest")

public class PlayersController {

    private PlayerService playerService;

    public PlayersController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    @ResponseBody
    public List<Player> getPlayers(PlayerFilters playerFilters) {
        return playerService.getAll(playerFilters);
        //метод должен возвращать из БД игроков по фильтрам свыше

    }

    @GetMapping ("/players/count")
    @ResponseBody
    public long getPlayersCount(PlayerFilters playerFilters) {
        return playerService.getCount(playerFilters);
        //метод должен возвращать количество игроков по фильтру свыше
    }

    @PostMapping("/players")
    @ResponseBody
    public Player createPlayer(@RequestBody CreatePlayer createPlayer) {
        //метод создает игрока
        return playerService.save(createPlayer);
    }

    //TODO:разобраться с @PathVariable (требует только при наличии {id} в пути

    @GetMapping("/players/{id}")
    @ResponseBody
    public Player getPlayer(@PathVariable Long id) throws Exception {
        //возвращает игрока по id
        return playerService.getPlayer(id);
    }

    @PostMapping("/players/{id}")
    @ResponseBody
    public Player updatePlayer(@PathVariable Long id, @RequestBody UpdatePlayer req) throws Exception {
        return playerService.update(id, req);
    }

    @DeleteMapping("/players/{id}")
    @ResponseBody
    public void deletePlayer(@PathVariable Long id) throws Exception {
        //удаляет игрока по id
        playerService.deletePlayer(id);
    }

    public static class PlayerFilters {

        private Optional<String> name = Optional.empty();
        private Optional<String> title = Optional.empty();
        private Optional<Race> race = Optional.empty();
        private Optional<Profession> profession = Optional.empty();
        private Optional<Long> after = Optional.empty();
        private Optional<Long> before = Optional.empty();
        private Optional<Boolean> banned = Optional.empty();
        private Optional<Integer> minExperience = Optional.empty();
        private Optional<Integer> maxExperience = Optional.empty();
        private Optional<Integer> minLevel = Optional.empty();
        private Optional<Integer> maxLevel = Optional.empty();
        private Optional<PlayerOrder> order = Optional.empty();
        private Optional<Integer> pageNumber = Optional.empty();
        private Optional<Integer> pageSize = Optional.empty();



        public Optional<String> getName() {
            return name;
        }

        public Optional<String> getTitle() {
            return title;
        }

        public Optional<Race> getRace() {
            return race;
        }

        public Optional<Profession> getProfession() {
            return profession;
        }

        public Optional<Long> getAfter() {
            return after;
        }

        public Optional<Long> getBefore() {
            return before;
        }

        public Optional<Boolean> getBanned() {
            return banned;
        }

        public Optional<Integer> getMinExperience() {
            return minExperience;
        }

        public Optional<Integer> getMaxExperience() {
            return maxExperience;
        }

        public Optional<Integer> getMinLevel() {
            return minLevel;
        }

        public Optional<Integer> getMaxLevel() {
            return maxLevel;
        }

        public String getOrder() {
            return order.orElse(PlayerOrder.ID).getFieldName();
        }

        public Integer getPageNumber() {
            return pageNumber.orElse(0);
        }

        public Integer getPageSize() {
            return pageSize.orElse(3);
        }

        public void setName(Optional<String> name) {
            this.name = name;
        }

        public void setTitle(Optional<String> title) {
            this.title = title;
        }

        public void setRace(Optional<Race> race) {
            this.race = race;
        }

        public void setProfession(Optional<Profession> profession) {
            this.profession = profession;
        }

        public void setAfter(Optional<Long> after) {
            this.after = after;
        }

        public void setBefore(Optional<Long> before) {
            this.before = before;
        }

        public void setBanned(Optional<Boolean> banned) {
            this.banned = banned;
        }

        public void setMinExperience(Optional<Integer> minExperience) {
            this.minExperience = minExperience;
        }

        public void setMaxExperience(Optional<Integer> maxExperience) {
            this.maxExperience = maxExperience;
        }

        public void setMinLevel(Optional<Integer> minLevel) {
            this.minLevel = minLevel;
        }

        public void setMaxLevel(Optional<Integer> maxLevel) {
            this.maxLevel = maxLevel;
        }

        public void setOrder(Optional<PlayerOrder> order) {
            this.order = order;
        }

        public void setPageNumber(Optional<Integer> pageNumber) {
            this.pageNumber = pageNumber;
        }

        public void setPageSize(Optional<Integer> pageSize) {
            this.pageSize = pageSize;
        }
    }
}
