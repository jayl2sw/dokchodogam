package com.ssafy.dockchodogam.controller;

import com.ssafy.dockchodogam.dto.game.MonsterRequestDto;
import com.ssafy.dockchodogam.dto.plant.PlantRequestDto;
import com.ssafy.dockchodogam.dto.user.UpdateUserRequestDto;
import com.ssafy.dockchodogam.dto.user.UserResponseDto;
import com.ssafy.dockchodogam.service.admin.AdminService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasAnyRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/user/list/{page}")
    @ApiOperation(value = "회원 목록 조회")
    public ResponseEntity<List<UserResponseDto>> getUserList(@PathVariable int page) {

        return new ResponseEntity<>(adminService.getUserList(page), HttpStatus.OK);
    }

    @GetMapping("/user/{user_id}")
    @ApiOperation(value = "회원 상세 조회")
    public ResponseEntity<UserResponseDto> getUserDetail(@PathVariable Long user_id) {

        return new ResponseEntity<>(adminService.getUserDetail(user_id), HttpStatus.OK);
    }

    @PutMapping("/user/{user_id}")
    @ApiOperation(value = "회원 정보 수정")
    public ResponseEntity<String> updateUser(@PathVariable Long user_id, @RequestBody UpdateUserRequestDto dto){
        adminService.updateUser(user_id, dto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @DeleteMapping("/user/{user_id}")
    @ApiOperation(value = "회원 삭제")
    public ResponseEntity<String> deleteUser(@PathVariable Long user_id){
        adminService.deleteUser(user_id);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PutMapping("/money")
    @ApiOperation(value = "돈 주기/뺏기")
    public ResponseEntity<String> updateMoney(@RequestBody Map<String, Object> map){
        Long user_id = (Long) map.get("user_id");
        Integer money = (Integer) map.get("money");
        adminService.updateMoney(user_id, money);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PostMapping("/dokcho")
    @ApiOperation(value = "식물 추가")
    public ResponseEntity<String> insertDokcho(@RequestBody PlantRequestDto dto){
        adminService.insertDokcho(dto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PutMapping("/dokcho/{plant_id}")
    @ApiOperation(value = "식물 수정")
    public ResponseEntity<String> updateDokcho(@PathVariable Long plant_id, @RequestBody PlantRequestDto dto){
        adminService.updateDokcho(plant_id, dto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @DeleteMapping("/dokcho/{plant_id}")
    @ApiOperation(value = "식물 삭제")
    public ResponseEntity<String> deleteDokcho(@PathVariable Long plant_id){
        adminService.deleteDokcho(plant_id);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PostMapping("/monster")
    @ApiOperation(value = "독초몬 추가")
    public ResponseEntity<String> insertMonster(@RequestPart(value = "dto")MonsterRequestDto dto, @RequestPart(value = "img")MultipartFile img){
        adminService.insertMonster(dto, img);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PutMapping("/monster/{monster_id}")
    @ApiOperation(value = "독초몬 수정")
    public ResponseEntity<String> updateMonster(@PathVariable Long monster_id, @RequestPart(value = "dto")MonsterRequestDto dto, @RequestPart(value = "img", required = false)MultipartFile img){
        adminService.updateMonster(monster_id, dto, img);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @DeleteMapping("/monster/{monster_id}")
    @ApiOperation(value = "독초몬 삭제")
    public ResponseEntity<String> deleteMonster(@PathVariable Long monster_id){
        adminService.deleteMonster(monster_id);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}