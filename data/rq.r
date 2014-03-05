multiplyData <- function(vector, multiplicationFactor){
	return(rep(vector, multiplicationFactor))
}

doRQ1 <- function(){
	svn <-toolData[toolData$commitOrigin == "SVN", ]$understandTime
	git <-toolData[toolData$commitOrigin == "Git", ]$understandTime

	svn <- multiplyData(svn, 6)
	git <- multiplyData(git, 6)

	t.test(svn, git, paired=TRUE)
}

doRQ2 <- function(){
	
}

originalData <- read.csv("analysis/results.csv", header=TRUE)
toolData <- read.csv("analysis/toolData.csv", header=TRUE)
participantData <- read.csv("analysis/toolData.csv", header=TRUE)
survey <- read.csv("survey.csv", header=TRUE)

doRQ1()