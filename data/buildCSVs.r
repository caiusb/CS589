addToToolData <- function(toolData, participant, commitOrigin, totalTime, typingTime, understandTime){
	row <- list(participant,
			commitOrigin,
			totalTime,
			typingTime,
			understandTime)

	toolData[nrow(toolData) + 1, ] <- row

	return(toolData)
}

buildToolData <- function(toolData, originalData){
	participants <- unique(originalData$participant)


	for (participant in participants){
		pData <- originalData[originalData$participant == participant, ]

		svnData <- pData[pData$commitOrigin == "SVN",]
		gitData <- pData[pData$commitOrigin == "Git",]

		svnTotalTime <- mean(svnData$totalTime)
		svnTypingTime <- mean(svnData$typingTime)
		svnUnderstandTime <- mean(svnData$understandTime)

		gitTotalTime <- mean(gitData$totalTime)
		gitTypingTime <- mean(gitData$typingTime)
		gitUnderstandTime <- mean(gitData$understandTime)

		toolData <- addToToolData(toolData,
								participant,
								"SVN",
								svnTotalTime,
								svnTypingTime,
								svnUnderstandTime)

		toolData <- addToToolData(toolData,
								participant,
								"Git",
								gitTotalTime,
								gitTypingTime,
								gitUnderstandTime)

	}

	return(toolData)
}

addToParticipantData <- function(participantData, participant, totalTime, typingTime, understandTime){
	row <- list(participant,
			totalTime,
			typingTime,
			understandTime)

	participantData[nrow(participantData) + 1, ] <- row

	return(participantData)
}

buildParticipantData <- function(participantData, toolData){
	participants <- unique(toolData$participant)


	for (participant in participants){
		pData <- toolData[toolData$participant == participant, ]

		totalTime <- mean(pData$totalTime)
		typingTime <- mean(pData$typingTime)
		understandTime <- mean(pData$understandTime)

		participantData <- addToParticipantData(participantData,
								participant,
								totalTime,
								typingTime,
								understandTime)
	}

	return(participantData)
}

originalData <- read.csv("analysis/results.csv", header=TRUE)

toolData <- data.frame(participant = character(),
						commitOrigin = character(),
						totalTime = numeric(),
						typingTime = numeric(),
						understandTime = numeric(),
						stringsAsFactors=FALSE)

participantData <- data.frame(participant = character(),
						totalTime = numeric(),
						typingTime = numeric(),
						understandTime = numeric(),
						stringsAsFactors=FALSE)

toolData <- buildToolData(toolData, originalData)
participantData <- buildParticipantData(participantData, toolData)

write.csv(toolData, file="analysis/toolData.csv")
write.csv(participantData, file="analysis/participantData.csv")
