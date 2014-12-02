package com.dstevens.players;

import static com.dstevens.collections.Sets.*;

import java.util.*;
import java.util.function.Function;
import javax.persistence.*;

import com.dstevens.characters.PlayerCharacter;
import com.dstevens.persistence.auditing.Auditable;
import com.dstevens.utilities.ObjectExtensions;

@Entity
@Table(name="Player")
public class Player implements Auditable<Player>, Comparable<Player> {

    @Id
    private final String id;
    
    @Column(name="name")
    private final String name;
    
    @Column(name="email")
    private final String email;
    
    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="player_id", referencedColumnName="id")
    private final Set<PlayerCharacter> characters;

    @Column(name="deleted_at")
    private final Date deleteTimestamp;
    
    //Used only for hibernate
    @Deprecated
    public Player() {
        this(null, null, null, null);
    }
    
    Player(String id, String name, String email, Set<PlayerCharacter> characters) {
        this(id, name, email, characters, null);
    }
    
    private Player(String id, String name, String email, Set<PlayerCharacter> characters, Date deleteTimestamp) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.characters = characters;
        this.deleteTimestamp = deleteTimestamp;
    }
    
    public final String getId() {
        return id;
    }

    public final Player withName(String name) {
        return new Player(id, name, email, characters, deleteTimestamp);
    }
    
    public final String getName() {
        return name;
    }

    public final Player withEmail(String email) {
        return new Player(id, name, email, characters, deleteTimestamp);
    }
    
    public final String getEmail() {
        return email;
    }

    public final Player withCharacter(PlayerCharacter character) {
        return new Player(id, name, email, setWith(characters, character), deleteTimestamp);
    }
    
    public final Player removeCharacter(PlayerCharacter character) {
        return new Player(id, name, email, setWithout(characters, character), deleteTimestamp);
    }
    
    public final Player clearCharacters() {
        return new Player(id, name, email, set(), deleteTimestamp);
    }
    
    public final Set<PlayerCharacter> getCharacters() {
        return characters;
    }

    public Player delete(Date deleteTimestamp) {
        return new Player(id, name, email, characters, deleteTimestamp);
    }
    
    public Player undelete() {
        return new Player(id, name, email, characters, null);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player that = Player.class.cast(obj);
            return this.id.equals(that.id);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    @Override
    public String toString() {
        return ObjectExtensions.toStringFor(this);
    }

    @Override
    public int compareTo(Player that) {
        Function<Player, Date>  byDeletedTimestamp = ((Player p) -> p.deleteTimestamp);
        Function<Player, String> byName = ((Player p) -> p.name);
        return Comparator.comparing(byDeletedTimestamp, Comparator.nullsFirst(Comparator.naturalOrder())).
                      thenComparing(Comparator.comparing(byName)).
                      compare(this, that);
    }
}
