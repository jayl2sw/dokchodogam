package com.ssafy.dockchodogam.dto.exception.plant;

import com.ssafy.dockchodogam.dto.exception.CustomException;
import com.ssafy.dockchodogam.dto.exception.ErrorCode;

public class PlantNotFoundException extends CustomException {
    public PlantNotFoundException(){
        super(ErrorCode.PLANT_NOT_FOUND);
    }
}
