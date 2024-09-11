package com.game.repository;

import com.game.entity.Player;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPlayerRepository {
    List<Player> getAll(int pageNumber, int pageSize);

    int getAllCount();

    Player save(Player player);

    Player update(Player player);

    Optional<Player> findById(long id);

    void delete(Player player);
}