package dclab.powerdatabackend.model;

public class File {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sjtudb.file.filename
     *
     * @mbg.generated
     */
    private String filename;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sjtudb.file.locations
     *
     * @mbg.generated
     */
    private String locations;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sjtudb.file.types
     *
     * @mbg.generated
     */
    private String types;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sjtudb.file.creator
     *
     * @mbg.generated
     */
    private String creator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sjtudb.file.createtime
     *
     * @mbg.generated
     */
    private String createtime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sjtudb.file.size
     *
     * @mbg.generated
     */
    private String size;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sjtudb.file.filename
     *
     * @return the value of sjtudb.file.filename
     *
     * @mbg.generated
     */
    public String getFilename() {
        return filename;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sjtudb.file.filename
     *
     * @param filename the value for sjtudb.file.filename
     *
     * @mbg.generated
     */
    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sjtudb.file.locations
     *
     * @return the value of sjtudb.file.locations
     *
     * @mbg.generated
     */
    public String getLocations() {
        return locations;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sjtudb.file.locations
     *
     * @param locations the value for sjtudb.file.locations
     *
     * @mbg.generated
     */
    public void setLocations(String locations) {
        this.locations = locations == null ? null : locations.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sjtudb.file.types
     *
     * @return the value of sjtudb.file.types
     *
     * @mbg.generated
     */
    public String getTypes() {
        return types;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sjtudb.file.types
     *
     * @param types the value for sjtudb.file.types
     *
     * @mbg.generated
     */
    public void setTypes(String types) {
        this.types = types == null ? null : types.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sjtudb.file.creator
     *
     * @return the value of sjtudb.file.creator
     *
     * @mbg.generated
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sjtudb.file.creator
     *
     * @param creator the value for sjtudb.file.creator
     *
     * @mbg.generated
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sjtudb.file.createtime
     *
     * @return the value of sjtudb.file.createtime
     *
     * @mbg.generated
     */
    public String getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sjtudb.file.createtime
     *
     * @param createtime the value for sjtudb.file.createtime
     *
     * @mbg.generated
     */
    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sjtudb.file.size
     *
     * @return the value of sjtudb.file.size
     *
     * @mbg.generated
     */
    public String getSize() {
        return size;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sjtudb.file.size
     *
     * @param size the value for sjtudb.file.size
     *
     * @mbg.generated
     */
    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }
}