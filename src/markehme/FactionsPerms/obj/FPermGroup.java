package markehme.FactionsPerms.obj;

public enum FPermGroup {
	
	Global		(true , false, false, false, false, false, false, false),
	Current 	(false, true , false, false, false, false, false, false),
	Ally 		(false, false, true , false, false, false, false, false),
	Neutral 	(false, false, false, true , false, false, false, false),
	Enemy 		(false, false, false, false, true , false, false, false),
	Safezone	(false, false, false, false, false, true , false, false),
	Warzone 	(false, false, false, false, false, false, true , false),
	Wilderness 	(false, false, false, false, false, false, false, true ),
	;

	private final boolean _Global, _Current, _Ally, _Neutral, _Enemy, _Safezone, _Warzone, _Wilderness;
	
	private FPermGroup(
						final boolean setGlobal,
						final boolean setCurrent,
						final boolean setAlly,
						final boolean setNeutral,
						final boolean setEnemy,
						final boolean setSafezone,
						final boolean setWarzone,
						final boolean setWilderness
	) { 
		this._Global = setGlobal;
		this._Current = setCurrent;
		this._Ally = setAlly;
		this._Neutral = setNeutral;
		this._Enemy = setEnemy;
		this._Safezone = setSafezone;
		this._Warzone = setWarzone;
		this._Wilderness = setWilderness;

	}
	
	public boolean isGlobal() { return this._Global; }
	public boolean isCurrent() { return this._Current; }
	public boolean isAlly() { return this._Ally; }
	public boolean isNeutral() { return this._Neutral; }
	public boolean isEnemy() { return this._Enemy; }
	public boolean isSafezone() { return this._Safezone; }
	public boolean isWarzone() { return this._Warzone; }
	public boolean isWilderness() { return this._Wilderness; }
	
	

}
