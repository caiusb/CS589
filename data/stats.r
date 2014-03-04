multiplyData <- function(vector, multiplicationFactor){
	return(rep(vector, multiplicationFactor))
}

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

originalData <- read.csv("analysis/results.csv", header=TRUE)
survey <- read.csv("survey.csv", header=TRUE)

toolData <- data.frame(participant = character(),
						commitOrigin = character(),
						totalTime = numeric(),
						typingTime = numeric(),
						understandTime = numeric(),
						stringsAsFactors=FALSE)

toolData <- buildToolData(toolData, originalData)
write.csv(toolData, file="analysis/toolData.csv")

svn <-toolData[toolData$commitOrigin == "SVN", ]$understandTime
git <-toolData[toolData$commitOrigin == "Git", ]$understandTime

svn <- multiplyData(svn, 6)
git <- multiplyData(git, 6)

t.test(svn, git, paired=TRUE)


