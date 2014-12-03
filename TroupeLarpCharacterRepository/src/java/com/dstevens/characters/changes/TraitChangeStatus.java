package com.dstevens.characters.changes;

import com.dstevens.characters.PlayerCharacter;

public enum TraitChangeStatus {

    PENDING() {
        @Override
        public PlayerCharacter approve(PlayerCharacter character, SetTrait traitToApprove) {
        	SetTrait currentTraitToApprove = traitToApprove;
        	while(currentTraitToApprove != null) {
        		currentTraitToApprove.setStatus(APPLIED);
        		character = currentTraitToApprove.apply(character);
        		currentTraitToApprove = currentTraitToApprove.associatedTrait();
        	}
            return character;
        }
        
        @Override
        public PlayerCharacter deny(PlayerCharacter character, SetTrait traitToDeny) {
        	traitToDeny.setStatus(TraitChangeStatus.DENIED);
            return character;
        }
    },
    DENIED() {
        @Override
        public PlayerCharacter approve(PlayerCharacter character, SetTrait traitToApprove) {
            return character;
        }

        @Override
        public PlayerCharacter deny(PlayerCharacter character, SetTrait traitToDeny) {
            return character;
        }
    },
    APPLIED() {
        @Override
        public PlayerCharacter approve(PlayerCharacter character, SetTrait traitToApprove) {
            return character;
        }

        @Override
        public PlayerCharacter deny(PlayerCharacter character, SetTrait traitToDeny) {
            return character;
        }
    };
    
    public abstract PlayerCharacter approve(PlayerCharacter character, SetTrait traitToApprove);

	public abstract PlayerCharacter deny(PlayerCharacter character, SetTrait traitToDeny);
    
}
