multiplyData <- function(vector, multiplicationFactor){
	return(rep(vector, multiplicationFactor))
}

multiplyDataFrame <- function(dataFrame, times){
	return(do.call("rbind", replicate(times, dataFrame, simplify = FALSE)))
}

doRQ1 <- function(){
	svn <-toolData[toolData$commitOrigin == "SVN", ]$understandTime
	git <-toolData[toolData$commitOrigin == "Git", ]$understandTime



	#svn <- multiplyData(svn, 6)
	#git <- multiplyData(git, 6)

	#toolData <- multiplyDataFrame(toolData, 6)

	anova(toolData$understandTime, toolData$commitOrigin)
	print(t.test(svn, git, paired=TRUE))
}

anova <- function(independentVar, dependentVar){
	print(summary(aov(dependentVar~independentVar)))
}

doRQ2 <- function(toolData, participantData, surveyData){
	toolData <- subset(toolData, participant != "P1")
	participantData <- subset(participantData, participant != "P1")
	surveyData <- subset(surveyData, participant != "P1")

	participantData <- multiplyDataFrame(participantData, 7)
	surveyData <- multiplyDataFrame(surveyData, 7)

	t <- participantData$understandTime
	s <- surveyData

#	formula <- lm(t ~ #progExperience + javaExperience + (progExperience * javaExperience))
#					#projectType *
#					vcsPreference + vcsUse + (vcsUse * vcsExperience)
#					#commitFrequency *
#					#splitChanges
#				)
#
#	(anova(formula))


	print("----------------------vcsUse AND time----------------------")
	anova(s$vcsUse, t)

	print("----------------------vcsPreference AND time----------------------")
	anova(s$vcsPreference, t)

	print("----------------------vcs experience AND time----------------------")
	anova(s$vcsExperience, t)

	print("----------------------commitFrequency AND time----------------------")
	anova(s$commitFrequency, t)

	print("----------------------splitChanges AND time----------------------")
	anova(s$splitChanges, t)

	print("----------------------javaExperience AND time----------------------")
	anova(s$javaExperience, t)

	print("----------------------progExperience AND time----------------------")
	anova(s$progExperience, t)
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

toolData <- multiplyDataFrame(toolData, 6)

#doRQ1()

doRQ2(toolData, participantData, surveyData)

#doPlots()