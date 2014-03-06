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

	t <- multiplyData(participantData$understandTime, 7)
	s <- surveyData

	progExperience <- multiplyData(s$progExperience, 7)
	javaExperience <- multiplyData(s$javaExperience, 7)
	projectType <- multiplyData(s$projectType, 7)
	vcsPreference <- multiplyData(s$vcsPreference, 7)
	vcsUse <- multiplyData(s$vcsUse, 7)
	vcsExperience <- multiplyData(s$vcsExperience, 7)
	commitFrequency <- multiplyData(s$commitFrequency, 7)
	splitChanges <- multiplyData(s$splitChanges, 7)

	formula <- lm(t ~ #progExperience + javaExperience + (progExperience * javaExperience))
					#projectType *
					vcsPreference + vcsUse * vcsExperience
					#commitFrequency *
					#splitChanges
				)

	(anova(formula))

#	print("----------------------vcsUse AND time----------------------")
#	ttest(surveyData$vcsUse, participantData$understandTime, FALSE)

#	print("----------------------vcsPreference AND time----------------------")
#	ttest(surveyData$vcsPreference, participantData$understandTime, FALSE)

#	print("----------------------vcs experience AND time----------------------")
#	ttest(surveyData$vcsExperience, participantData$understandTime, FALSE)

#	print("----------------------commitFrequency AND time----------------------")
#	ttest(surveyData$commitFrequency, participantData$understandTime, FALSE)

#	print("----------------------splitChanges AND time----------------------")
#	ttest(surveyData$splitChanges, participantData$understandTime, FALSE)

#	print("----------------------javaExperience AND time----------------------")
#	ttest(surveyData$javaExperience, participantData$understandTime, FALSE)

#	print("----------------------progExperience AND time----------------------")
#	ttest(surveyData$progExperience, participantData$understandTime, FALSE)
}

doPlots <- function(){
	pdf(file='analysis/typingTime.pdf', height=6, width=6, onefile=TRUE, family='Helvetica', paper='letter', pointsize=12)
	options(scipen=999)

	boxplot(originalData$typingTime, xlab="typingTime", ylab="milliseconds")

	svnTime <- originalData[originalData$commitOrigin == "SVN", ]$understandTime
	gitTime <- originalData[originalData$commitOrigin == "Git", ]$understandTime

	boxplot(svnTime, gitTime, names=c("svn understand times", "git understand times"))

	dev.off()  

}

originalData <- read.csv("analysis/results.csv", header=TRUE)
toolData <- read.csv("analysis/toolData.csv", header=TRUE)
participantData <- read.csv("analysis/participantData.csv", header=TRUE)
surveyData <- read.csv("survey.csv", header=TRUE)

#doRQ1()

doRQ2(toolData, participantData, surveyData)

#doPlots()