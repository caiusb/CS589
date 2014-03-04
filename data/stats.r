multiplyData <- function(vector, multiplicationFactor){
	return(rep(vector, multiplicationFactor))
}

addToFrame <- function(smallData, participant, commitOrigin, totalTime, typingTime, understandTime){
	row <- list(participant,
			commitOrigin,
			totalTime,
			typingTime,
			understandTime)

	smallData[nrow(smallData) + 1, ] <- row

	return(smallData)
}

buildSmallData <- function(smallData, originalData){
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

		smallData <- addToFrame(smallData,
								participant,
								"SVN",
								svnTotalTime,
								svnTypingTime,
								svnUnderstandTime)

		smallData <- addToFrame(smallData,
								participant,
								"Git",
								gitTotalTime,
								gitTypingTime,
								gitUnderstandTime)

	}

	return(smallData)
}

originalData <- read.csv("analysis/results.csv", header=TRUE)
survey <- read.csv("survey.csv", header=TRUE)

smallData <- data.frame(participant = character(),
						commitOrigin = character(),
						totalTime = numeric(),
						typingTime = numeric(),
						understandTime = numeric(),
						stringsAsFactors=FALSE)

smallData <- buildSmallData(smallData, originalData)

svn <-smallData[smallData$commitOrigin == "SVN", ]$understandTime
git <-smallData[smallData$commitOrigin == "Git", ]$understandTime

svn <- multiplyData(svn, 6)
git <- multiplyData(git, 6)

t.test(svn, git, paired=TRUE)
