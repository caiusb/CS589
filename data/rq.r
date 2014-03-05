multiplyData <- function(vector, multiplicationFactor){
	return(rep(vector, multiplicationFactor))
}

doRQ1 <- function(){
	svn <-toolData[toolData$commitOrigin == "SVN", ]$understandTime
	git <-toolData[toolData$commitOrigin == "Git", ]$understandTime

	svn <- multiplyData(svn, 6)
	git <- multiplyData(git, 6)

	print(t.test(svn, git, paired=TRUE))
}

ttest <- function(independentVar, dependentVar, paired){

	independentVar <- multiplyData(independentVar, 6)
	dependentVar <- multiplyData(dependentVar, 6)

	print(summary(aov(dependentVar~independentVar)))
}

doRQ2 <- function(toolData, participantData, surveyData){
	toolData <- subset(toolData, participant != "P1")
	participantData <- subset(participantData, participant != "P1")
	surveyData <- subset(surveyData, participant != "P1")

	print("----------------------vcsUse AND time----------------------")
	ttest(surveyData$vcsUse, participantData$understandTime, FALSE)

	print("----------------------vcsPreference AND time----------------------")
	ttest(surveyData$vcsPreference, participantData$understandTime, FALSE)

	print("----------------------vcs experience AND time----------------------")
	ttest(surveyData$vcsExperience, participantData$understandTime, FALSE)

	print("----------------------commitFrequency AND time----------------------")
	ttest(surveyData$commitFrequency, participantData$understandTime, FALSE)

	print("----------------------splitChanges AND time----------------------")
	ttest(surveyData$splitChanges, participantData$understandTime, FALSE)

	print("----------------------javaExperience AND time----------------------")
	ttest(surveyData$javaExperience, participantData$understandTime, FALSE)

	print("----------------------progExperience AND time----------------------")
	ttest(surveyData$progExperience, participantData$understandTime, FALSE)
}

originalData <- read.csv("analysis/results.csv", header=TRUE)
toolData <- read.csv("analysis/toolData.csv", header=TRUE)
participantData <- read.csv("analysis/participantData.csv", header=TRUE)
surveyData <- read.csv("survey.csv", header=TRUE)

doRQ1()

doRQ2(toolData, participantData, surveyData)