package dclab.powerdatabackend.model;

public class Correlation {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column correlation.hash
     *
     * @mbg.generated
     */
    private String hash;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column correlation.result
     *
     * @mbg.generated
     */
    private String result;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column correlation.json
     *
     * @mbg.generated
     */
    private String json;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column correlation.hash
     *
     * @return the value of correlation.hash
     *
     * @mbg.generated
     */
    public String getHash() {
        return hash;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column correlation.hash
     *
     * @param hash the value for correlation.hash
     *
     * @mbg.generated
     */
    public void setHash(String hash) {
        this.hash = hash == null ? null : hash.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column correlation.result
     *
     * @return the value of correlation.result
     *
     * @mbg.generated
     */
    public String getResult() {
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column correlation.result
     *
     * @param result the value for correlation.result
     *
     * @mbg.generated
     */
    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column correlation.json
     *
     * @return the value of correlation.json
     *
     * @mbg.generated
     */
    public String getJson() {
        return json;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column correlation.json
     *
     * @param json the value for correlation.json
     *
     * @mbg.generated
     */
    public void setJson(String json) {
        this.json = json == null ? null : json.trim();
    }
}