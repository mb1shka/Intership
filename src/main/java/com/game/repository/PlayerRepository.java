package com.game.repository;

import com.game.controller.PlayersController;
import com.game.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/*public interface PlayerRepository {

    public List<Player> getAll(PlayersController.PlayerFilters playerFilters);

    public Player save(Player player);

    public Player getById(Long id);

    public void delete(Player player);
}*/

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>, JpaSpecificationExecutor<Player> { }
