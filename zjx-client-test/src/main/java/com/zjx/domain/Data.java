package com.zjx.domain;

/**
 * @author Aaron
 * @date 2020/6/10 6:26 下午
 */
public class Data {

    private Integer flag;

    private Integer userId;
    private Integer playProgress;
    private String rightStatus;
    private String status;
    private Integer score;
    private Integer thumbNo;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPlayProgress() {
        return playProgress;
    }

    public void setPlayProgress(Integer playProgress) {
        this.playProgress = playProgress;
    }

    public String getRightStatus() {
        return rightStatus;
    }

    public void setRightStatus(String rightStatus) {
        this.rightStatus = rightStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getThumbNo() {
        return thumbNo;
    }

    public void setThumbNo(Integer thumbNo) {
        this.thumbNo = thumbNo;
    }

    @Override
    public String toString() {
        return "Data{" +
                "flag=" + flag +
                ", userId=" + userId +
                ", playProgress=" + playProgress +
                ", rightStatus='" + rightStatus + '\'' +
                ", status='" + status + '\'' +
                ", score=" + score +
                ", thumbNo=" + thumbNo +
                '}';
    }
}
