package dclab.powerdatabackend.mapper

import dclab.powerdatabackend.domain.FileInfo
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface FileMapper {

    @get:
    Select("SELECT * FROM `file`")
    val fileList: List<FileInfo>

    @Insert("INSERT INTO `file`(fileName,location) VALUES (#{fileName},#{location})")
    fun InsertFile(file: FileInfo): Int

    @Select("SELECT * from `file` where fileID = #{fileID}")
    fun getFileByID(fileID: Int): FileInfo
}