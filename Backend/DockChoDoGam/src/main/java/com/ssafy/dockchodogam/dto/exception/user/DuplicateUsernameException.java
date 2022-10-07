package com.ssafy.dockchodogam.dto.exception.user;

import com.ssafy.dockchodogam.dto.exception.CustomException;
import com.ssafy.dockchodogam.dto.exception.ErrorCode;

public class DuplicateUsernameException extends CustomException {
    public DuplicateUsernameException(){
        super(ErrorCode.DUPLICATE_USERNAME);
    }
}
