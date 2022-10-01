package com.ssafy.dockchodogam.dto.exception.user;

import com.ssafy.dockchodogam.dto.exception.CustomException;
import com.ssafy.dockchodogam.dto.exception.ErrorCode;

public class DuplicateProposeException extends CustomException {
    public DuplicateProposeException(){
        super(ErrorCode.DUPLICATE_PROPOSE);
    }
}
