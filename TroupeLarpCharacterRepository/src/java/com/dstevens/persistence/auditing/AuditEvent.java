package com.dstevens.persistence.auditing;

import java.util.Date;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

import com.dstevens.characters.PlayerCharacter;
import com.dstevens.players.*;
import com.dstevens.utilities.ObjectExtensions;

@Entity
@Table(name="AuditEvent")
public class AuditEvent<E> {

    @Id
    private final String id;
    
    @Column(name="timestamp")
    private final Date timestamp;
    
    @Any( metaColumn = @Column( name = "audited_type") )
    @AnyMetaDef(
            idType = "string",
            metaType = "string",
            metaValues = {
                    @MetaValue( value = "Troupe", targetEntity = Troupe.class),
                    @MetaValue( value = "Player", targetEntity = Player.class),
                    @MetaValue( value = "PlayerCharacter", targetEntity = PlayerCharacter.class)
                }
            )
    @JoinColumn(name="audited_id")
    private final E audited;

    @Column(name="audit_message")
    private final String auditMessage;
    
    //Hibernate only
    @Deprecated
    public AuditEvent() {
        this(null, null, null, null);
    }
    
    public AuditEvent(String id, E audited, Date timestamp, String auditMessage) {
        this.id = id;
        this.audited = audited;
        this.timestamp = timestamp;
        this.auditMessage = auditMessage;
    }
    
    public final String getId() {
        return id;
    }

    public final Date getTimestamp() {
        return timestamp;
    }

    public final E getAudited() {
        return audited;
    }

    public final String getAuditMessage() {
        return auditMessage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AuditEvent) {
            AuditEvent<?> that = this.getClass().cast(obj);
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

}
