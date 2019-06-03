package io.github.forezp.distributedlimitcore.entity;

/**
 * Created by forezp on 2019/5/1.
 */
public class LimitResult {

    private String url;

    private String idenfier;

    private ResultType resultType;

    @Override
    public String toString() {
        return "LimitResult{" +
                "url='" + url + '\'' +
                ", idenfier='" + idenfier + '\'' +
                ", resultType=" + resultType +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdenfier() {
        return idenfier;
    }

    public void setIdenfier(String idenfier) {
        this.idenfier = idenfier;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public enum ResultType{
        SUCCESS,FAIL
    }
}
