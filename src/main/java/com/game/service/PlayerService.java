package com.game.service;

import com.game.controller.PlayersController;
import com.game.entity.Player;
import com.game.service.exceptions.PlayerNotFound;
import com.game.transfer.CreatePlayer;
import com.game.transfer.UpdatePlayer;

import java.util.List;

public interface PlayerService {

    public List<Player> getAll(PlayersController.PlayerFilters playerFilters);

    public Player update(Long id, UpdatePlayer upd) throws Exception;

    public Player getPlayer(Long id) throws Exception;

    public void deletePlayer(Long id) throws PlayerNotFound;

    public Player save(CreatePlayer player);

    public long getCount(PlayersController.PlayerFilters playerFilters);
}
