package com.dstevens.characters.changes;

import com.dstevens.characters.PlayerCharacter;

public interface ITraitChangeStatus<PC extends PlayerCharacter> {

	PC approve(PC character, SetTrait traitToApprove);

	PC deny(PC character, SetTrait traitToDeny);
	
}
