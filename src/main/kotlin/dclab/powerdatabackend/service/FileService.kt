package dclab.powerdatabackend.service

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import dclab.powerdatabackend.domain.FileInfo
import dclab.powerdatabackend.mapper.FileMapper
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.URLEncoder
import java.util.*
import javax.servlet.http.HttpServletResponse


@Service
class FileService {

    @Autowired
    private var fileMapper: FileMapper? = null

    val fileList: Any
        get() {
            val fileI = fileMapper!!.fileList
            val result = HashMap<String, Any>()
            result["files"] = fileI
            result["status"] = "SUCCESS"
            return result
        }

    fun setFileMapper(fileMapper: FileMapper) {
        this.fileMapper = fileMapper
    }


    @Throws(Exception::class)
    fun uploadFile(file: MultipartFile): Any? {
        var path = File(ResourceUtils.getURL("classpath:").path)
        if (!path.exists()) path = File("")
        println("path:" + path.absolutePath)
        val upload = File(path.absolutePath, "static/files/upload/")
        if (!upload.exists()) upload.mkdirs()
        println("upload url:" + upload.absolutePath)
        try {
            val fos = FileOutputStream(upload.absolutePath + File.separator + file.originalFilename)
            fos.write(file.bytes)
            fos.flush()
            fos.close()

            var process: Process? = null
            val FileName = file.originalFilename

            val command1 = "python excel_to_mysql.py \"$FileName\""
            println("command1:$command1")
            process = Runtime.getRuntime().exec(command1, null, File(path.absolutePath))
            process!!.waitFor()

        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        val fi = FileInfo()
        fi.fileName = (file.originalFilename)
        fi.location = (upload.absolutePath + File.separator + file.originalFilename)
        fileMapper!!.InsertFile(fi)
        val fileI = fileMapper!!.fileList
        val result = HashMap<String, Any>()
        result["files"] = fileI
        result["status"] = "SUCCESS"
        return result
    }

    @Throws(IOException::class)
    fun downloadFile(fileID: Int, response: HttpServletResponse) {
        val f = fileMapper!!.getFileByID(fileID)
        val file = File(f.location)
        val filename = f.fileName
        response.setHeader("content-type", "application/octet-stream")
        response.contentType = "application/force-download"
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"))

        response.setContentLength(file.length().toInt())
        val iStream = FileInputStream(file)
        val oStream = response.outputStream
        IOUtils.copy(iStream, oStream)
        response.flushBuffer()

    }

    //    public Object getTableTitle(int fileID){
    //        FileInfo f = fileMapper.getFileByID(fileID);
    //        File file = new File(f.getLocation());
    //
    //    }
    @Throws(IOException::class, InvalidFormatException::class)
    fun getBookTitle(fileID: Int): Any {
        val fileInfo = fileMapper!!.getFileByID(fileID)
        val location = fileInfo.location
        val file = File(location)
        val stream = FileInputStream(file)
        val result = HashMap<String, ArrayList<String>>()
        val workbook = XSSFWorkbook(stream)
        for (i in 0 until workbook.getNumberOfSheets()) {
            val colMaxNum = workbook.getSheetAt(i).getRow(workbook.getSheetAt(i).getFirstRowNum()).getLastCellNum()
            val temp = ArrayList<String>()
            for (j in 0 until colMaxNum) {
                temp.add(workbook.getSheetAt(i).getRow(0).getCell(j).getStringCellValue())
            }
            result[workbook.getSheetName(i)] = temp
        }
        return result
    }

    @Throws(IOException::class)
    fun getSheet(fileID: Int, sheetName: String): Any? {
        val fileInfo = fileMapper!!.getFileByID(fileID)
        val location = fileInfo.location
        val file = File(location)
        val stream = FileInputStream(file)
        val workbook = XSSFWorkbook(stream)
        val sheet = workbook.getSheet(sheetName)
        val result = HashMap<String, Any>()
        val time = ArrayList<String>()
        result["time"] = time
        return null
    }
}
