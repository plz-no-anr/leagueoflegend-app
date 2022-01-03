package com.psg.leagueoflegend_app.data.model

data class CurrentGameInfo(
    val gameId: Long,
    val gameType: String,
    val gameStartTime: Long,
    val mapId: Long,
    val gameLength: Long,
    val platformId: String,
    val gameMode: String,
    val bannedChampions: List<BannedChampion>,
    val gameQueueConfigId: Long,
    val observers: Observer,
    val participants: List<CurrentGameParticipant> // 팀원 정보
){
    data class BannedChampion(
        val pickTurn: Int,
        val championId: Long,
        val teamId: Long
    )
    data class Observer(
        val encryptionKey: String
    )
    data class CurrentGameParticipant(
        val championId:	Long,
        val perks: Perks, // 룬
        val profileIconId: Long,
        val bot: Boolean, // 봇인지
        val teamId:	Long,
        val summonerName: String,
        val summonerId:	String,
        val spell1Id: Long, // 스펠
        val spell2Id: Long, // 스펠2
        val gameCustomizationObjects: List<GameCustomizationObject>
    ){
        data class Perks(
            val perkIds: List<Long>, // 사용룬
            val perkStyle: Long, // 룬 정보
            val perkSubStyle: Long // 보조룬 정도
        )
        data class GameCustomizationObject(
            val category: String,
            val content: String
        )
    }


}
