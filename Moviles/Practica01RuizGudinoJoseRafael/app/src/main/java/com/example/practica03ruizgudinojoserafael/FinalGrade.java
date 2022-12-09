package com.example.practica03ruizgudinojoserafael;

public class FinalGrade {
    private float score01;
    private float score02;
    private float score03;
    private float grade;
    private String weightingType;

    FinalGrade(){
        score01 = -1;
        score02 = -1;
        score03 = -1;
        grade = -1;
        weightingType = "C";
    }


    public FinalGrade(float score01, float score02, float score03,String weightingType) {
        this.score01 = score01;
        this.score02 = score02;
        this.score03 = score03;
        this.weightingType = weightingType;
    }

    public float getScore01() {
        return score01;
    }

    public void setScore01(float score01) {
        this.score01 = score01;
    }

    public float getScore02() {
        return score02;
    }

    public void setScore02(float score02) {
        this.score02 = score02;
    }

    public float getScore03() {
        return score03;
    }

    public void setScore03(float score03) {
        this.score03 = score03;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getWeightingType() {
        return weightingType;
    }

    public void setWeightingType(String weightingType) {
        this.weightingType = weightingType;
    }
}
