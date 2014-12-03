package com.dstevens.characters.changes;

import com.dstevens.characters.PlayerCharacter;

public enum TraitChangeStatus {

    PENDING() {
		@Override
		public <PC extends PlayerCharacter> PC approve(PC character, SetTrait<PC> traitToApprove) {
        	SetTrait<PC> currentTraitToApprove = traitToApprove;
        	while(currentTraitToApprove != null) {
        		currentTraitToApprove.setStatus(APPLIED);
        		character = currentTraitToApprove.apply(character);
        		currentTraitToApprove = currentTraitToApprove.associatedTrait();
        	}
            return character;
		}

		@Override
		public <PC extends PlayerCharacter> PC deny(PC character, SetTrait<PC> traitToDeny) {
			traitToDeny.setStatus(TraitChangeStatus.DENIED);
			return character;
		}

    },
    DENIED() {
        @Override
        public <PC extends PlayerCharacter> PC approve(PC character, SetTrait<PC> traitToApprove) {
            return character;
        }

        @Override
        public <PC extends PlayerCharacter> PC deny(PC character, SetTrait<PC> traitToDeny) {
            return character;
        }
    },
    APPLIED() {
        @Override
        public <PC extends PlayerCharacter> PC approve(PC character, SetTrait<PC> traitToApprove) {
            return character;
        }

        @Override
        public <PC extends PlayerCharacter> PC deny(PC character, SetTrait<PC> traitToDeny) {
            return character;
        }
    };
    
    public abstract <PC extends PlayerCharacter> PC approve(PC character, SetTrait<PC> traitToApprove);

	public abstract <PC extends PlayerCharacter> PC deny(PC character, SetTrait<PC> traitToDeny);
    
}
