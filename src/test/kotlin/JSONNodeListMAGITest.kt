import fileAccess.fileTypeJSON.nodeListMAGI.FileJSONNodeListMAGIReader
import fileAccess.fileTypeJSON.voteInfoUnit.FileJSONVoteInfoUnitReader
import fileAccess.fileTypeJSON.voteInfoUnit.toJSONVoteInfoUnitName
import java.time.LocalDateTime

fun main() {
    val fileJSONNodeListMAGIReader = FileJSONNodeListMAGIReader()
    if(fileJSONNodeListMAGIReader.readJSONNodeListMAGI()) {
        println("Last update  - ${fileJSONNodeListMAGIReader.lastUpdate}")
        println("Current time - ${LocalDateTime.now()}")
        println("--------------")
        println(fileJSONNodeListMAGIReader.nodeMAGIInfo.forEach { nodeMAGI ->
            println("${nodeMAGI.identifier} > ${nodeMAGI.publicKeyX509EncodedBase64}")

            val voteUnitFileName = nodeMAGI.identifier.toJSONVoteInfoUnitName()
            val fileJSONVoteInfoUnitReader = FileJSONVoteInfoUnitReader()
            if(fileJSONVoteInfoUnitReader.readJSONVoteInfoUnit(voteUnitFileName)) {
                println(fileJSONVoteInfoUnitReader.originID)
                println(fileJSONVoteInfoUnitReader.timestamp)
                println(fileJSONVoteInfoUnitReader.entry.blockNumber)
                println(fileJSONVoteInfoUnitReader.entry.description)
                println(fileJSONVoteInfoUnitReader.entry.question)
                println(fileJSONVoteInfoUnitReader.entry.options.forEach { voteUnit ->
                    println("${voteUnit.optionNumber} > ${voteUnit.optionDefinition}")
                })
                println(fileJSONVoteInfoUnitReader.signature)
                println("--------------")
            }
        })
    }
    println("Fin")
}