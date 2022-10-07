package com.ssafy.dockchodogam.service.admin;


import com.ssafy.dockchodogam.dto.game.MonsterRequestDto;
import com.ssafy.dockchodogam.dto.plant.PlantRequestDto;
import com.ssafy.dockchodogam.dto.user.UpdateUserRequestDto;
import com.ssafy.dockchodogam.dto.user.UserResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminService {
    public List<UserResponseDto> getUserList(int page);
    public UserResponseDto getUserDetail(Long id);
    public void updateUser(Long id, UpdateUserRequestDto dto);
    public void deleteUser(Long id);
    public void updateMoney(Long id, Integer money);
    public void insertDokcho(PlantRequestDto dto);
    public void updateDokcho(Long id, PlantRequestDto dto);
    public void deleteDokcho(Long id);
    public void insertMonster(MonsterRequestDto dto, MultipartFile img);
    public void updateMonster(Long id, MonsterRequestDto dto, MultipartFile img);
    public void deleteMonster(Long id);
}
