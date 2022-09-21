package com.ssafy.dockchodogam.service.dokcho;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.ssafy.dockchodogam.domain.Monster;
import com.ssafy.dockchodogam.domain.Plant;
import com.ssafy.dockchodogam.domain.User;
import com.ssafy.dockchodogam.domain.UserMonster;
import com.ssafy.dockchodogam.dto.exception.user.UserNotFoundException;
import com.ssafy.dockchodogam.dto.plant.PlantDetailDto;
import com.ssafy.dockchodogam.dto.plant.PlantListDto;
import com.ssafy.dockchodogam.repository.PlantRepository;
import com.ssafy.dockchodogam.repository.UserMonsterRepository;
import com.ssafy.dockchodogam.repository.UserRepository;
import com.ssafy.dockchodogam.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class DokchoServiceImpl implements DokchoService {

    private final String uploadPath = "~/images/";
    private final PlantRepository plantRepository;
    private final UserRepository userRepository;
    private final UserMonsterRepository userMonsterRepository;
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${plant.api.key}")
    private String apiKey;

    @Override
    public List<PlantListDto> findAllPlants() {
        List<PlantListDto> plantList = plantRepository.findAll().stream()
                .map(p -> new PlantListDto(p.getPlantId(), p.getName(), p.getEngNm(), p.getImgUrl(),
                        p.getFamilyKorNm(), p.getGenusKorNm(), p.getPlantSpecsScnm()))
                .collect(Collectors.toList());
        return plantList;
    }

    @Override
    public PlantDetailDto findPlantDetail(Long plantId) {
        Optional<Plant> plant = plantRepository.findById(plantId);
        if (plant.isPresent()) {
            PlantDetailDto result = createDto(plant.get());
            return result;
        }
        return null;
    }

    @Override
    public List<PlantListDto> searchPlants(String keyword, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<PlantListDto> plantList = plantRepository.searchPlantsByKeyword(keyword, pageRequest).stream()
                .map(p -> new PlantListDto(p.getPlantId(), p.getName(), p.getEngNm(), p.getImgUrl(),
                        p.getFamilyKorNm(), p.getGenusKorNm(), p.getPlantSpecsScnm()))
                .collect(Collectors.toList());
        return plantList;
    }

    @Override
    public void addFoundMonster(Monster monster) {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new);
        UserMonster userMonster = UserMonster.builder().user(user).monster(monster).build();
        userMonsterRepository.save(userMonster);
    }

    @Override
    public PlantDetailDto findPlantByPlantSpecsScnm(String plantSpecsScnm) {
        Optional<Plant> plant = plantRepository.findByplantSpecsScnm(plantSpecsScnm);
        if (plant.isPresent()) {
            PlantDetailDto dto = createDto(plant.get());
            return dto;
        }
        return null;
    }

    @Override
    public String savePlantImage(MultipartFile file) {
        if(file.isEmpty()){
            throw new RuntimeException("이미지가 없습니다.");
        }
        String fileName = file.getOriginalFilename();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());

        try(InputStream inputStream = file.getInputStream()){
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }catch (IOException e){
            throw new RuntimeException("이미지 업로드 실패");
        }

        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }

    @Override
    public Map<String, Object> judgeImage(String imgurl) throws Exception {
        String jsonData = sendAPIRequest(imgurl);
        Map<String, Object> data = getGenusAndProb(jsonData);
        String species = (String) data.get("species");
        String genus = (String) data.get("genus");
        Float probability = (Float) data.get("probability");

        Optional<Plant> plantBySpecies = plantRepository.findPlantByEngNm(species);

        Map<String, Object> res = new HashMap<>();
        res.put("plant", null);
        if (probability < 0.5) {
            return res;
        }

        res.put("probability", probability);
        if (plantBySpecies.isPresent()) {
            Plant plant = plantBySpecies.get();
            res.put("plant", plant);
        } else {
            Optional<Plant> plantByGenus = plantRepository.findPlantByGenusNm(genus);
            if (plantByGenus.isPresent()) {
                Plant plant = plantByGenus.get();
                res.put("plant", plant);
            }
        }

        return res;
    }

    public boolean checkUserDogam(Long monsterId){
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new);
        Optional<UserMonster> um = userMonsterRepository.findUserMonsterByMonsterMonsterIdAndUserUserId(user.getUserId(), monsterId);
        if (um.isPresent()) {
            return true;
        } else{
            return false;
        }
    }

    private static String base64EncodeFromFile(String imgurl) throws Exception {
        InputStream is = new URL(imgurl).openStream();
        byte[] bytes = IOUtils.toByteArray(is);
        String base64img = Base64.getEncoder().encodeToString(bytes);
        return base64img;
    }

    public static String sendPostRequest(String urlString, JSONObject data) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");

        OutputStream os = con.getOutputStream();
        os.write(data.toString().getBytes());
        os.close();

        InputStream is = con.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is);
        String response = new String(bytes);

        System.out.println("Response code : " + con.getResponseCode());
        System.out.println("Response : " + response);
        con.disconnect();
        return response;
    }

    public String sendAPIRequest(String path) throws Exception{

        // read image from local file system and encode
        JSONObject data = new JSONObject();
        data.put("api_key", apiKey);

        // add images
        JSONArray images = new JSONArray();
        String fileData = base64EncodeFromFile(path);
        images.put(fileData);

        data.put("images", images);

        // add modifiers
        // modifiers info: https://github.com/flowerchecker/Plant-id-API/wiki/Modifiers
        JSONArray modifiers = new JSONArray()
                .put("crops_fast")
                .put("similar_images");
        data.put("modifiers", modifiers);

        // add language
        data.put("plant_language", "ko");

        // add plant details
        // more info here: https://github.com/flowerchecker/Plant-id-API/wiki/Plant-details
        JSONArray plantDetails = new JSONArray()
                .put("common_names")
                .put("url")
                .put("name_authority")
                .put("wiki_description")
                .put("taxonomy")
                .put("synonyms");
        data.put("plant_details", plantDetails);

        return sendPostRequest("https://api.plant.id/v2/identify", data);
    }

    public Map<String, Object> getGenusAndProb(String data) throws ParseException, JSONException {
        JSONParser jsonParser= new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(data);
        JSONArray suggestions = (JSONArray) jsonObject.get("suggestions");
        JSONObject candidate = (JSONObject) suggestions.get(0);
        float probability = (float) candidate.get("probability");
        JSONObject plant_details = (JSONObject) candidate.get("plant_details");
        JSONObject structured_name = (JSONObject) plant_details.get("structured_name");
        String genus = (String) structured_name.get("genus");
        String species = (String) structured_name.get("species");

        Map<String, Object> res = new HashMap<>();
        res.put("probability", probability);
        res.put("genus", genus);
        res.put("species", species);

        return res;
    }


    public PlantDetailDto createDto(Plant p){
        PlantDetailDto dto = new PlantDetailDto(p.getPlantId(), p.getName(), p.getEngNm(), p.getFamilyKorNm(),
                p.getFamilyNm(), p.getGenusKorNm(), p.getGenusNm(), p.getPlantSpecsScnm(), p.getImgUrl(),
                p.getShpe(), p.getSpft(), p.getOrplcNm(), p.getSz(), p.getSmlrPlntDesc(), p.getFlwrDesc(),
                p.getLeafDesc(), p.getDstrb(), p.getStemDesc(), p.getFritDesc(), p.getBranchDesc(), p.getWoodDesc(),
                p.getSporeDesc(), p.getRootDesc(), p.getFarmSpftDesc(), p.getGrwEvrntDesc(), p.getUseMthdDesc(),
                p.getCprtCtnt());
        return dto;
    }
}
