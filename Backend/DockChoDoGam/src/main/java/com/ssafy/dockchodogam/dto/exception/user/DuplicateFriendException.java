package com.ssafy.dockchodogam.dto.exception.user;

import com.ssafy.dockchodogam.dto.exception.CustomException;
import com.ssafy.dockchodogam.dto.exception.ErrorCode;

public class DuplicateFriendException extends CustomException {
    public DuplicateFriendException(){
        super(ErrorCode.DUPLICATE_FRIEND);
    }
}
