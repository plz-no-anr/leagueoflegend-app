package com.psg.domain.model

data class League(
    val result: Boolean, // 결과
    val record: Boolean, // 전적
    val miniSeries: Boolean, // 승급전
    val code: Int // 에러 코드
)