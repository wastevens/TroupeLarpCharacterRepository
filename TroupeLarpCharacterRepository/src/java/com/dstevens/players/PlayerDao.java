package com.dstevens.players;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PlayerDao extends CrudRepository<Player, String> {
    
    @Query("SELECT a FROM Player a WHERE a.name = ?1 AND a.deleteTimestamp is null")
    Player findUndeletedNamed(String name);
    
    @Query("SELECT a FROM Player a WHERE a.email = ?1 AND a.deleteTimestamp is null")
    Player findUndeletedWithEmail(String playerEmail);
    
    @Query("SELECT a FROM Player a WHERE a.name = ?1")
    Iterable<Player> findNamed(String name);

    
}
