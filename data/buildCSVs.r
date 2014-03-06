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

#map val from [A, B] to [a, b]. Hopefully.
mapToInterval <- function(val, A, B, a, b){
	mappedVal <- (val - A) * (b - a) / (B - A) + a
	return(mappedVal)
}

addNormalizedGrades <- function(grades){
	tasks <- unique(grades$taskID)
	participants <- unique(grades$participant)
	
	grades$normalizedGrade <- grades$descriptionGrade

	for(task in tasks){
		taskGrades <- grades[grades$taskID == task, ]$descriptionGrade
		maxGrade <- max(taskGrades)

		for(participant in participants){
			grade <- grades[grades$participant == participant & grades$taskID == task, ]$descriptionGrade

			normalizedGrade <- mapToInterval(grade, 0, maxGrade, 0, 10)

			grades[grades$participant == participant & grades$taskID == task, ]$normalizedGrade <- normalizedGrade
		}
	}

	return(grades)
}

originalData <- read.csv("analysis/results.csv", header=TRUE)
grades <- read.csv("grades.csv", header=TRUE)

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

gradesNormalized <- addNormalizedGrades(grades)

write.csv(toolData, file="analysis/toolData.csv")
write.csv(participantData, file="analysis/participantData.csv")
write.csv(gradesNormalized, file="grades.csv")