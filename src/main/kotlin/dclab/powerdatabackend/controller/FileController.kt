package dclab.powerdatabackend.controller

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import dclab.powerdatabackend.dao.FileMapper
import dclab.powerdatabackend.model.File
import dclab.powerdatabackend.service.FileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.HashMap
import javax.servlet.http.HttpServletResponse


@CrossOrigin
@RestController
@RequestMapping("/api")
class FileController {
    @Autowired
    private var fileService: FileService? = null
    @Autowired
    lateinit var fileMapper:FileMapper
        @RequestMapping(value = ["/getFileList"], method = [RequestMethod.POST])
        @Throws(Exception::class)
        fun getFileList():List<File>{
            return fileMapper.selectAll()
        }
//        get() = fileService!!.fileList

    fun setFileService(fileService: FileService) {
        this.fileService = fileService
    }

    @RequestMapping(value = ["/uploadFile"], method = [RequestMethod.POST])
    @Throws(Exception::class)
    fun uploadFile(@RequestParam(value = "file") dataFile: MultipartFile): Any? {
        println("进入后台")
        return fileService!!.uploadFile(dataFile)
    }

    @RequestMapping( value= ["/downloadFile/{fileID}"], method = [RequestMethod.GET])
    @Throws(Exception::class)
    fun downloadFile(@PathVariable("fileID") fileID: Int, response: HttpServletResponse) {
        fileService!!.downloadFile(fileID, response)
    }

    @RequestMapping(value = ["/getBookTitle"], method = [RequestMethod.GET])
    @Throws(IOException::class, InvalidFormatException::class)
    fun getBookTitle(fileID: String): Any {
        val fileIDInt = Integer.parseInt(fileID)
        return fileService!!.getBookTitle(fileIDInt)
    }
}