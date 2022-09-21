package com.ssafy.dockchodogam.dto.exception.game;

import com.ssafy.dockchodogam.dto.exception.CustomException;
import com.ssafy.dockchodogam.dto.exception.ErrorCode;

public class SkillNotFoundException extends CustomException {
    public SkillNotFoundException(){
        super(ErrorCode.SKILL_NOT_FOUND);
    }
}
