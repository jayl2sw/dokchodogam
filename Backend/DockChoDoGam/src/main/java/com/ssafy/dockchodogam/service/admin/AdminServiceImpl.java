package com.ssafy.dockchodogam.service.admin;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.dockchodogam.domain.Monster;
import com.ssafy.dockchodogam.domain.Plant;
import com.ssafy.dockchodogam.domain.User;
import com.ssafy.dockchodogam.dto.exception.game.MonsterNotFoundException;
import com.ssafy.dockchodogam.dto.exception.plant.PlantNotFoundException;
import com.ssafy.dockchodogam.dto.exception.user.ExceededMoneyException;
import com.ssafy.dockchodogam.dto.exception.user.UserNotFoundException;
import com.ssafy.dockchodogam.dto.game.MonsterRequestDto;
import com.ssafy.dockchodogam.dto.plant.PlantRequestDto;
import com.ssafy.dockchodogam.dto.user.UpdateUserRequestDto;
import com.ssafy.dockchodogam.dto.user.UserResponseDto;
import com.ssafy.dockchodogam.repository.MonsterRepository;
import com.ssafy.dockchodogam.repository.PlantRepository;
import com.ssafy.dockchodogam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final MonsterRepository monsterRepository;
    private final PlantRepository plantRepository;
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Override
    public List<UserResponseDto> getUserList(int page) {
        return userRepository.findAll(PageRequest.of(page, 10)).stream()
                .map(user -> UserResponseDto.from(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getUserDetail(Long id) {
        return UserResponseDto.from(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    @Override
    public void updateUser(Long id, UpdateUserRequestDto dto) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        user.update(dto);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    @Override
    public void updateMoney(Long id, Integer money) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if(money<0 && user.getMoney()<(money*-1)){
            throw new ExceededMoneyException();
        }
        user.changeMoney(money);
        userRepository.save(user);
    }

    @Override
    public void insertDokcho(PlantRequestDto dto) {
        plantRepository.save(Plant.builder()
                .name(dto.getName())
                        .engNm(dto.getEngNm())
                        .familyKorNm(dto.getFamilyKorNm())
                        .familyNm(dto.getFamilyNm())
                        .genusKorNm(dto.getGenusKorNm())
                        .genusNm(dto.getGenusNm())
                        .plantSpecsScnm(dto.getPlantSpecsScnm())
                        .imgUrl(dto.getImgUrl())
                        .shpe(dto.getShpe())
                        .spft(dto.getSpft())
                        .orplcNm(dto.getOrplcNm())
                        .sz(dto.getSz())
                        .smlrPlntDesc(dto.getSmlrPlntDesc())
                        .flwrDesc(dto.getFlwrDesc())
                        .leafDesc(dto.getLeafDesc())
                        .dstrb(dto.getDstrb())
                        .stemDesc(dto.getStemDesc())
                        .fritDesc(dto.getFritDesc())
                        .branchDesc(dto.getBranchDesc())
                        .woodDesc(dto.getWoodDesc())
                        .sporeDesc(dto.getSporeDesc())
                        .rootDesc(dto.getRootDesc())
                        .farmSpftDesc(dto.getFarmSpftDesc())
                        .grwEvrntDesc(dto.getGrwEvrntDesc())
                        .useMthdDesc(dto.getUseMthdDesc())
                        .cprtCtnt(dto.getCprtCtnt())
                .monster(monsterRepository.findById(dto.getMonsterId()).orElseThrow(MonsterNotFoundException::new))
                .build());
    }

    @Override
    public void updateDokcho(Long id, PlantRequestDto dto) {
        Plant plant = plantRepository.findById(id).orElseThrow(PlantNotFoundException::new);

        plant.update(dto);
        plantRepository.save(plant);
    }

    @Override
    public void deleteDokcho(Long id) {
        Plant plant = plantRepository.findById(id).orElseThrow(PlantNotFoundException::new);
        plantRepository.delete(plant);
    }

    @Override
    public void insertMonster(MonsterRequestDto dto, MultipartFile img) {
        if(img.isEmpty()){
            throw new RuntimeException("이미지가 없습니다.");
        }

        String fileName = img.getOriginalFilename();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(img.getContentType());

        try(InputStream inputStream = img.getInputStream()){
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }catch (IOException e){
            throw new RuntimeException("이미지 업로드 실패");
        }

        monsterRepository.save(Monster.builder()
                .name(dto.getName()).hp(dto.getHp())
                .maxAttack(dto.getMaxAttack())
                .minAttack(dto.getMinAttack())
                .type(dto.getType())
                .grade(dto.getGrade())
                .images(fileName)
                .build());
    }

    @Override
    public void updateMonster(Long id, MonsterRequestDto dto, MultipartFile img) {
        Monster monster = monsterRepository.findById(id).orElseThrow(MonsterNotFoundException::new);
        monster.update(dto);

        if(!img.isEmpty()){
            String fileName = img.getOriginalFilename();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(img.getContentType());

            try(InputStream inputStream = img.getInputStream()){
                amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            }catch (IOException e){
                throw new RuntimeException("이미지 업로드 실패");
            }
            monster.changeImage(fileName);
        }

        monsterRepository.save(monster);
    }

    @Override
    public void deleteMonster(Long id) {
        Monster monster = monsterRepository.findById(id).orElseThrow(MonsterNotFoundException::new);
        monsterRepository.delete(monster);
    }
}
