package com.dstevens.characters;

import static com.dstevens.collections.Lists.list;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import com.dstevens.characters.changes.SetTrait;
import com.dstevens.persistence.auditing.Auditable;
import com.dstevens.utilities.ObjectExtensions;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@Entity
@Table(name="PlayerCharacter")
public class PlayerCharacter implements Auditable<PlayerCharacter>, Comparable<PlayerCharacter> {

    @Id
    private final String id;
    
    @Column(name="deleted_at")
    private final Date deleteTimestamp;
    
    @Column(name="name")
    private final String name;
    
    @OneToMany(cascade={CascadeType.ALL})
    @OrderColumn(name="order_by")
    private final List<SetTrait> traitChangeEvents;
    
    //Hibernate only
    @Deprecated
    public PlayerCharacter() {
        this(null, null, null);
    }
    
    PlayerCharacter(String id, String name) {
        this(id, name, null);
    }
    
    private PlayerCharacter(String id, String name, Date deleteTimestamp) {
		this.id = id;
		this.name = name;
		this.deleteTimestamp = deleteTimestamp;
		this.traitChangeEvents = list();
    }
    
    public String getId() {
        return id;
    }
    
    public PlayerCharacter withName(String name) {
        return new PlayerCharacter(id,  name, deleteTimestamp);
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public PlayerCharacter delete(Date timestamp) {
        return new PlayerCharacter(id, name, timestamp);
    }

    @Override
    public PlayerCharacter undelete() {
        return new PlayerCharacter(id,  name, null);
    }
    
    public boolean isDeleted() {
        return deleteTimestamp != null;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlayerCharacter) {
            PlayerCharacter that = PlayerCharacter.class.cast(obj);
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
    public int compareTo(PlayerCharacter that) {
        Function<PlayerCharacter, Date>  byDeletedTimestamp = ((PlayerCharacter p) -> p.deleteTimestamp);
        Function<PlayerCharacter, String> byName = ((PlayerCharacter p) -> p.name);
        return Comparator.comparing(byDeletedTimestamp, Comparator.nullsFirst(Comparator.naturalOrder())).
                      thenComparing(Comparator.comparing(byName)).
                      compare(this, that);
    }
    
    public List<SetTrait> getTraitChangeEvents() {
        return traitChangeEvents;
    }
    
    public PlayerCharacter withTraitChangeEvent(SetTrait event) {
        this.traitChangeEvents.add(event);
        return this;
    }
    
    public PlayerCharacter approvePendingChange(SetTrait event) {
        event.approve(this);
        return this;
    }
    
    public PlayerCharacter approvePendingChanges() {
        this.traitChangeEvents.forEach((SetTrait t) -> t.approve(this));
        return this;
    }
}
