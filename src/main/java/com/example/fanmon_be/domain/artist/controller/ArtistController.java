package com.example.fanmon_be.domain.artist.controller;


import com.example.fanmon_be.domain.artist.entity.Artist;
import com.example.fanmon_be.domain.artist.service.ArtistService;
import com.example.fanmon_be.domain.management.entity.Management;
import com.example.fanmon_be.domain.user.dto.LoginRequest;
import com.example.fanmon_be.domain.user.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/management/artist")
public class ArtistController {
    @Autowired
    private ArtistService artistService;

    //접속한 매니지먼트의 아티스트 LIST
    @GetMapping("/list/{managementuuid}")
    public ResponseEntity<List<Artist>> findByManagementUuid(@PathVariable UUID managementuuid) {
        List<Artist> list = artistService.getArtistsByManagementuuid(managementuuid);
        return ResponseEntity.ok(list);
    }

    //아티스트 생성 CREATE
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Artist> createArtist(
            HttpServletRequest request,
            @ModelAttribute Artist artist,
            @RequestParam("managementuuid") UUID managementuuid) {
        //프론트에서 넘어온 uuid 정보들 포함시키도록
        System.out.println(managementuuid);
        artist.setManagement(new Management(managementuuid));
        String path = request.getServletContext().getRealPath("/resources/artistimg");
        System.out.println("real path : " + path);
        String fname = null;
        MultipartFile uploadfile = artist.getUploadfile();
        fname = uploadfile.getOriginalFilename();
        if (fname != null && !fname.equals("")) {
            try {
                FileOutputStream fos = new FileOutputStream(new File(path+"/"+fname));
                FileCopyUtils.copy(uploadfile.getInputStream(), fos);
                fos.close();
                artist.setFname(fname);
            } catch (Exception e) {
                System.out.println("파일 처리 예외! => " + e.getMessage());
            }
        }
        Artist createdArtist = artistService.create(artist);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArtist);
    }

    //아티스트 수정 UPDATE
    @PutMapping("/{artistuuid}")
    public ResponseEntity<Artist> updateArtist(
            @PathVariable UUID artistuuid,
            HttpServletRequest request,
            @ModelAttribute Artist artist,
            @RequestParam("managementuuid") UUID managementuuid
            ){
        //프론트에서 넘어온 uuid 정보들 포함시키도록
        System.out.println(managementuuid);
        artist.setManagement(new Management(managementuuid));
        //업뎃된 파일 있으면 그걸로 fname set해서 db에 저장
        String oldFname = artist.getFname();
        MultipartFile uploadfile = artist.getUploadfile();
        String fname = null;
        if(uploadfile != null){
            fname = uploadfile.getOriginalFilename();
            artist.setFname(fname);
        }
        Artist updatedArtist = artistService.updateArtist(artistuuid,artist);

        //파일 관리
        if(updatedArtist!=null && fname!=null && !fname.equals("")) {
            String path = request.getServletContext().getRealPath("/resources/artistimg");
            //원래 파일 삭제
            File file = new File(path+"/"+oldFname);
            file.delete();
            try{
                //업뎃된 파일 저장
                byte[] data = uploadfile.getBytes();
                FileOutputStream fos = new FileOutputStream(path+"/"+fname);
                fos.write(data);
                fos.close();
            }catch (Exception e){
                System.out.println("파일 업로드 예외 : "+e.getMessage());
            }
        }
        if(updatedArtist==null){
            System.out.println("파일 업로드 실패");
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedArtist);
    }

    //아티스트 디테일
    @GetMapping("/{artistuuid}")
    public ResponseEntity<Artist> getArtist(@PathVariable UUID artistuuid) {
        System.out.println("(DETAIL)찾는 artistuuid : "+artistuuid);
        Artist artist = artistService.getArtistById(artistuuid);
        if (artist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(artist);
    }

    //아티스트 삭제 DELETE
    @DeleteMapping("/{artistuuid}")
    public ResponseEntity<Artist> deleteArtist(@PathVariable UUID artistuuid) {
        artistService.deleteArtist(artistuuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "artist 로그인")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(artistService.login(request));
    }
}
