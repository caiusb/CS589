multiplyData <- function(vector, multiplicationFactor){
	return(rep(vector, multiplicationFactor))
}

multiplyDataFrame <- function(dataFrame, times){
	return(do.call("rbind", replicate(times, dataFrame, simplify = FALSE)))
}

doRQ1 <- function(){

	cat("\n\n")
	print("=============== RQ1 ===============")
	cat("\n\n")

	svn <-toolData[toolData$commitOrigin == "SVN", ]$understandTime
	git <-toolData[toolData$commitOrigin == "Git", ]$understandTime

	doAnova(toolData$understandTime, toolData$commitOrigin)
	print(t.test(svn, git, paired=TRUE))


	#svn understand time vs Git understand time

	svnTime <- originalData[originalData$commitOrigin == "SVN", ]$understandTime
	gitTime <- originalData[originalData$commitOrigin == "Git", ]$understandTime

	pdf(file='analysis/typingTime.pdf', onefile=TRUE, family='Helvetica', pointsize=12)
	
	boxplot(svnTime, gitTime, names=c("SVN Understand Times", "Git Understand Times"))

	dev.off()
}

doAnova <- function(dependentVar, independentVar){
	formula <- lm(dependentVar~independentVar)

	print(summary(aov(formula)))
}

doCommonRQ23 <- function(participantData, participantDataColumn, surveyData){
	dependentVar <- participantData[[participantDataColumn]]
	s <- surveyData

	print(paste("----------------------", "vcsUse", "AND", participantDataColumn, "----------------------"))
	doAnova(dependentVar, s$vcsUse)

	print(paste("----------------------", "vcsPreference", "AND", participantDataColumn, "----------------------"))
	doAnova(dependentVar, s$vcsPreference)

	print(paste("----------------------", "vcs", "experience AND", participantDataColumn, "----------------------"))
	doAnova(dependentVar, s$vcsExperience)

	print(paste("----------------------", "commitFrequency", "AND", participantDataColumn, "----------------------"))
	doAnova(dependentVar, s$commitFrequency)

	print(paste("----------------------", "splitChanges", "AND", participantDataColumn, "----------------------"))
	doAnova(dependentVar, s$splitChanges)

	print(paste("----------------------", "javaExperience", "AND", participantDataColumn, "----------------------"))
	doAnova(dependentVar, s$javaExperience)

	print(paste("----------------------", "progExperience", "AND", participantDataColumn, "----------------------"))
	doAnova(dependentVar, s$progExperience)
}

doRQ2 <- function(participantData, surveyData){
	cat("\n\n")
	print("=============== RQ2 ===============")
	cat("\n\n")

	participantData <- subset(participantData, participant != "P1")
	surveyData <- subset(surveyData, participant != "P1")

	participantData <- multiplyDataFrame(participantData, 7)
	surveyData <- multiplyDataFrame(surveyData, 7)

	doCommonRQ23(participantData, "understandTime", surveyData)

	simpleBoxPlot(participantData$understandTime, "Average Participant Understand Time", "Minutes", "analysis/understandTimeRQ2.pdf")
}

doRQ3 <- function(participantData, surveyData){
	cat("\n\n")
	print("=============== RQ3 ===============")
	cat("\n\n")

	participantData <- multiplyDataFrame(participantData, 6)
	surveyData <- multiplyDataFrame(surveyData, 6)

	doCommonRQ23(participantData, "normalizedGrade", surveyData)

	grade <- participantData$normalizedGrade
	s <- surveyData

	formula <- lm(grade ~ s$vcsPreference + s$vcsUse + (s$vcsPreference * s$vcsUse))
	summary(aov(formula))

	print(t.test(grade~s$vcsUse))
	print(t.test(grade~s$vcsPreference))
}

doRQ4 <- function(grades){

	cat("\n\n")
	print("=============== RQ4 ===============")
	cat("\n\n")

	svnGrades <- vector()
	gitGrades <- vector()

	svnData <- grades[grades$commitOrigin == "SVN", ]
	gitData <- grades[grades$commitOrigin == "Git", ]

	participants <- unique(grades$participant)

	for(participant in participants){
		participantSVNGrades <- svnData[svnData$participant == participant, ]$normalizedGrade
		svnGrades <- c(svnGrades, mean(participantSVNGrades))

		participantGitGrades <- gitData[gitData$participant == participant, ]$normalizedGrade
		gitGrades <- c(gitGrades, mean(participantGitGrades))
	} 

	svnGrades <- multiplyData(svnGrades, 5)
	gitGrades <- multiplyData(gitGrades, 5)

	print(t.test(svnGrades, gitGrades, paired=TRUE))

	#----------------------------------

	pdf(file='analysis/grades.pdf', onefile=TRUE, family='Helvetica', pointsize=12)

	grades <- multiplyDataFrame(grades, 6)

	boxplot(grades$normalizedGrade, xlab="Combined Tasks", ylab="Grades")


	T01Grades <- grades[grades$taskID == "T01", ]$normalizedGrade
	T02Grades <- grades[grades$taskID == "T02", ]$normalizedGrade
	T03Grades <- grades[grades$taskID == "T03", ]$normalizedGrade
	T04Grades <- grades[grades$taskID == "T04", ]$normalizedGrade
	T05Grades <- grades[grades$taskID == "T05", ]$normalizedGrade
	T06Grades <- grades[grades$taskID == "T06", ]$normalizedGrade

	print(T06Grades)

	colors <- c(rep("red", 3), rep("green", 3))
	names <- c("T01", "T02", "T03", "T04", "T05", "T06")

	boxplot(T01Grades, T02Grades, T03Grades, T04Grades, T05Grades, T06Grades, names=names, col=colors, xlab="Individual Tasks", ylab="Grades")

	dev.off()  
}

simpleBoxPlot <- function(data, xlab, ylab, fileName){
	pdf(file=fileName, onefile=TRUE, family='Helvetica', pointsize=12)

	boxplot(data, xlab=xlab, ylab=ylab)

	dev.off()
}

doPlots <- function(){
	#typing time

	simpleBoxPlot(originalData$typingTime, "analysis/typingTime.pdf", "Average Participant Typing Time", "Minutes")


}

options(scipen=999)

originalData <- read.csv("analysis/results.csv", header=TRUE)
toolData <- read.csv("analysis/toolData.csv", header=TRUE)
participantData <- read.csv("analysis/participantData.csv", header=TRUE)
surveyData <- read.csv("surveyNumbers.csv", header=TRUE)
grades <- read.csv("grades.csv", header=TRUE)

toolData <- multiplyDataFrame(toolData, 6)

doRQ1()

doRQ2(participantData, surveyData)
doRQ3(participantData, surveyData)


doRQ4(grades)

doPlots()