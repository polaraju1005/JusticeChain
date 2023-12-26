package com.example.justicechain.models

class CaseDetailsData {

    private var caseType:String = ""
    private var judgeName:String = ""
    private var lawyerName:String = ""
    private var clientName:String = ""
    private var caseNum:String = ""
    private var fileNum:String = ""
    private var fileDate:String = ""
    private var phone:String = ""
    private var desc:String = ""

    constructor()

    constructor(
        caseType:String,
        judgeName:String,
        lawyerName:String,
        clientName:String,
        caseNum:String,
        fileNum:String,
        fileDate:String,
        phone:String,
        desc:String
    ){
        this.caseType = caseType
        this.judgeName = judgeName
        this.lawyerName = lawyerName
        this.clientName = clientName
        this.caseNum = caseNum
        this.fileNum =fileNum
        this.fileDate = fileDate
        this.phone = phone
        this.desc = desc
    }
    fun getCaseType(): String? {
        return caseType
    }

    fun setCaseType(caseType: String){
        this.caseType =caseType
    }

    fun getJudgeName():String?{
        return judgeName
    }

    fun setJudgeName(judgeName: String) {
        this.judgeName = judgeName
    }

    fun getLawyerName():String?{
        return lawyerName
    }

    fun setLawyerName(lawyerName: String){
        this.lawyerName = lawyerName
    }

    fun getClientName():String?{
        return clientName
    }

    fun setClientName(clientName: String){
        this.clientName = clientName
    }

    fun getCaseNum():String?{
        return caseNum
    }

    fun setCaseNum(caseType: String){
        this.caseNum = caseNum
    }

    fun getFileNum():String?{
        return fileNum
    }

    fun setFileNum(fileNum: String){
        this.fileNum = fileNum
    }

    fun getFileDate():String?{
        return fileDate
    }

    fun setFileDate(fileDate: String){
        this.fileDate = fileDate
    }

    fun getPhone():String?{
        return phone
    }

    fun setPhone(phone: String){
        this.phone = phone
    }

    fun getDesc():String?{
        return desc
    }

    fun setDesc(desc: String){
        this.desc =desc
    }

}