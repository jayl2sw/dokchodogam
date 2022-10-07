package com.ssafy.dockchodogam.dto.exception.game;

import com.ssafy.dockchodogam.dto.exception.CustomException;
import com.ssafy.dockchodogam.dto.exception.ErrorCode;

public class MonsterNotFoundException extends CustomException {
    public MonsterNotFoundException(){
        super(ErrorCode.MONSTER_NOT_FOUND);
    }
}
