package com.atguigu.controller;

import com.atguigu.base.BaseController;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.HouseImageService;
import com.atguigu.util.QiniuUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @Author luoyin
 * @Date 15:48 2022/8/24
 **/
@Controller
@RequestMapping("/houseImage")
public class HouseImageController extends BaseController {

    private static final String PAGE_UPLOAD = "house/upload";
    private static final String SHOW_ACTION = "redirect:/house/";

    @Reference
    private HouseImageService houseImageService;
    @GetMapping("/uploadShow/{houseId}/{type}")
    @PreAuthorize("hasAnyAuthority('house.editImage')")
    public String uploadShow(@PathVariable Long houseId , @PathVariable Integer type, Model model){

        model.addAttribute("houseId",houseId);
        model.addAttribute("type",type);
        return PAGE_UPLOAD;
    }

    @ResponseBody
    @PostMapping("/upload/{houseId}/{type}")
    @PreAuthorize("hasAnyAuthority('house.editImage')")
    public Result upload(@PathVariable Long houseId , @PathVariable Integer type,@RequestParam("file") MultipartFile[] files) throws IOException {

        if(files.length > 0){
            for (MultipartFile file : files) {
                String sufName = file.getOriginalFilename().split("\\.")[1];
                String fixName = UUID.randomUUID().toString().replace("-","");
                String fileName = fixName + "."+sufName;
                QiniuUtils.upload2Qiniu(file.getBytes(),fileName);
                HouseImage houseImage = new HouseImage();
                houseImage.setHouseId(houseId);
                houseImage.setImageName(fileName);
                houseImage.setType(type);
                houseImage.setImageUrl("http://rh3t0txl3.hn-bkt.clouddn.com/"+fileName);
                houseImageService.insert(houseImage);

            }
        }
        return Result.ok();
    }

    @GetMapping("/delete/{houseId}/{id}")
    @PreAuthorize("hasAnyAuthority('house.editImage')")
    public String delete(@PathVariable Long houseId , @PathVariable Long id){
        HouseImage houseImage = houseImageService.getById(id);
        QiniuUtils.deleteFileFromQiniu(houseImage.getImageName());
        houseImageService.delete(id);
        return SHOW_ACTION +houseId;
    }
}
