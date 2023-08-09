package com.system.badminton_management_system.Service.Impl;


import com.system.badminton_management_system.Pojo.BadmintonPojo;
import com.system.badminton_management_system.Repo.BadmintonRepo;
import com.system.badminton_management_system.Service.BadmintonService;

import com.system.badminton_management_system.entity.Badminton;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BadmintonServiceImpl implements BadmintonService {

    private final BadmintonRepo badmintonRepo;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/images/";


    @Override
    public BadmintonPojo savebadminton(BadmintonPojo badmintonPojo) throws IOException {
        Badminton badminton = new Badminton();
        if (badmintonPojo.getFid()!= null){
            badminton.setBadminton_Id(badmintonPojo.getFid());
        }
        badminton.setBadmintonname(badmintonPojo.getFname());
        badminton.setBadmintonprice(badmintonPojo.getFprice());
        badminton.setBadmintoncontact(badmintonPojo.getFcontact());
        badminton.setBadmintonlocation(badmintonPojo.getFlocation());
        badminton.setDescription(badmintonPojo.getDescription());



        if(badmintonPojo.getImage1()!=null){
//            System.out.println(UPLOAD_DIRECTORY);
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, badmintonPojo.getImage1().getOriginalFilename());
            fileNames.append(badmintonPojo.getImage1().getOriginalFilename());
            Files.write(fileNameAndPath, badmintonPojo.getImage1().getBytes());

            badminton.setBadmintonimage1(badmintonPojo.getImage1().getOriginalFilename());
        }
        if(badmintonPojo.getImage2()!=null){
//            System.out.println(UPLOAD_DIRECTORY);
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, badmintonPojo.getImage2().getOriginalFilename());
            fileNames.append(badmintonPojo.getImage2().getOriginalFilename());
            Files.write(fileNameAndPath, badmintonPojo.getImage2().getBytes());

            badminton.setBadmintonimage2(badmintonPojo.getImage2().getOriginalFilename());
        }
        if(badmintonPojo.getImage()!=null){
//            System.out.println(UPLOAD_DIRECTORY);
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, badmintonPojo.getImage().getOriginalFilename());
            fileNames.append(badmintonPojo.getImage().getOriginalFilename());
            Files.write(fileNameAndPath, badmintonPojo.getImage().getBytes());

            badminton.setBadmintonimage(badmintonPojo.getImage().getOriginalFilename());
        }
        badmintonRepo.save(badminton);
        return new BadmintonPojo(badminton);
    }

    @Override
    public Badminton fetchById(Integer id) {
        Badminton badminton = badmintonRepo.findById(id).orElseThrow(()-> new RuntimeException("Couldnot find"));
        badminton = Badminton.builder()
                .badminton_Id(badminton.getBadminton_Id())
                .imageBase64(getImageBase64(badminton.getBadmintonimage()))
                .image1Base64(getImageBase64(badminton.getBadmintonimage1()))
                .image2Base64(getImageBase64(badminton.getBadmintonimage2()))
                .badmintonname(badminton.getBadmintonname())
                .badmintoncontact(badminton.getBadmintoncontact())
                .badmintonprice(badminton.getBadmintonprice())
                . badmintonlocation(badminton.getBadmintonlocation())
                .Description(badminton.getDescription())
                .build();
        return badminton;
    }

    public String getImageBase64(String fileName) {
        String filePath = System.getProperty("user.dir") + "/images/";
        File file = new File(filePath + fileName);
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return Base64.getEncoder().encodeToString(bytes);
    }

    @Override
    public List<Badminton> fetchAll() {
        return badmintonRepo.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        badmintonRepo.deleteById(id);
    }

}
