package dclab.powerdatabackend.controller;

import dclab.powerdatabackend.util.SaxExcelOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class SaxExcel {
    @Autowired
    private DataSource dataSource;

    @PostMapping(value = "/datas", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public String addBlacklist(@RequestParam("filename") MultipartFile multipartFile, HttpServletRequest request) {
        //判断上传内容是否符合要求
        String fileName = multipartFile.getOriginalFilename();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            return " ";
        }

        String file = saveFile(multipartFile, request);
        int result = 0;
        try {
            result = new SaxExcelOp().addBlackLists(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return " ";
    }

    private String saveFile(MultipartFile multipartFile, HttpServletRequest request) {
        String path;
        String fileName = multipartFile.getOriginalFilename();
        // 判断文件类型
        String realPath = request.getSession().getServletContext().getRealPath("/");
        String trueFileName = fileName;
        // 设置存放Excel文件的路径
        path = realPath + trueFileName;
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        try {
            multipartFile.transferTo(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

}
