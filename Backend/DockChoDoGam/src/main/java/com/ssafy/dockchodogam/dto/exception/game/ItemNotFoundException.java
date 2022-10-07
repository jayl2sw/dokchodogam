package com.ssafy.dockchodogam.dto.exception.game;

import com.ssafy.dockchodogam.dto.exception.CustomException;
import com.ssafy.dockchodogam.dto.exception.ErrorCode;

public class ItemNotFoundException extends CustomException {
    public ItemNotFoundException(){
        super(ErrorCode.ITEM_NOT_FOUND);
    }
}
