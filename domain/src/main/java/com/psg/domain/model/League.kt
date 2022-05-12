package com.psg.domain.model

data class League(
    var result: Boolean, // 결과
    var record: Boolean, // 전적
    var miniSeries: Boolean, // 승급전
    var code: Int // 에러 코드
){
    fun setLeague(result: Boolean,record: Boolean,miniSeries: Boolean,code: Int){
        this.result = result
        this.record = record
        this.miniSeries = miniSeries
        this.code = code
    }
}