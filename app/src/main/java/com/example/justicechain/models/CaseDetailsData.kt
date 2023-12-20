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

    fun setJudgeName(judgeName: String) {
        this.judgeName = judgeName
    }

}