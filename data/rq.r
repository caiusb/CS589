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

	pdf(file='analysis/RQ1_SVNGitTimes.pdf', onefile=TRUE, family='Helvetica', pointsize=12)
	
	boxplot(svnTime, gitTime, names=c("SVN Understand Times", "Git Understand Times"))

	dev.off()

	pdf(file='analysis/RQ1_TaskTimes.pdf', onefile=TRUE, family='Helvetica', pointsize=12)

	#understand times per tasks
	T01Times <- originalData[originalData$taskID == "T01", ]$understandTime
	T02Times <- originalData[originalData$taskID == "T02", ]$understandTime
	T03Times <- originalData[originalData$taskID == "T03", ]$understandTime
	T04Times <- originalData[originalData$taskID == "T04", ]$understandTime
	T05Times <- originalData[originalData$taskID == "T05", ]$understandTime
	T06Times <- originalData[originalData$taskID == "T06", ]$understandTime

	colors <- c(rep("red", 3), rep("green", 3))
	names <- c("T01", "T02", "T03", "T04", "T05", "T06")

	boxplot(T01Times, T02Times, T03Times, T04Times, T05Times, T06Times, names=names, col=colors, xlab="Individual Task times", ylab="Minutes")

	dev.off()

	#participant svn git times

	pdf(file='analysis/RQ1_ParticipantTimes.pdf', onefile=TRUE, family='Helvetica', pointsize=12)

	P01SVNTimes <- originalData[originalData$participant == "P1" & originalData$commitOrigin == "SVN", ]$understandTime
	P02SVNTimes <- originalData[originalData$participant == "P2" & originalData$commitOrigin == "SVN", ]$understandTime
	P03SVNTimes <- originalData[originalData$participant == "P3" & originalData$commitOrigin == "SVN", ]$understandTime
	P04SVNTimes <- originalData[originalData$participant == "P4" & originalData$commitOrigin == "SVN", ]$understandTime
	P05SVNTimes <- originalData[originalData$participant == "P5" & originalData$commitOrigin == "SVN", ]$understandTime

	P01GitTimes <- originalData[originalData$participant == "P1" & originalData$commitOrigin == "Git", ]$understandTime
	P02GitTimes <- originalData[originalData$participant == "P2" & originalData$commitOrigin == "Git", ]$understandTime
	P03GitTimes <- originalData[originalData$participant == "P3" & originalData$commitOrigin == "Git", ]$understandTime
	P04GitTimes <- originalData[originalData$participant == "P4" & originalData$commitOrigin == "Git", ]$understandTime
	P05GitTimes <- originalData[originalData$participant == "P5" & originalData$commitOrigin == "Git", ]$understandTime

	colors <- c("red", "green")
	names <- list()

	for (i in seq(1,5)){
		names <- c(names, sprintf("P%d: SVN", i))
		names <- c(names, sprintf("P%d: Git", i))
	}

	for (name in names){
		print(sprintf("%s\n", name))
	}

	boxplot(P01SVNTimes, P01GitTimes, 
			P02SVNTimes, P02GitTimes,
			P03SVNTimes, P03GitTimes,
			P04SVNTimes, P04GitTimes,
			P05SVNTimes, P05GitTimes,
			names=names,
			col=colors,
			xlab="Participant Times",
			ylab="Minutes",
			las=2)

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

	simpleBoxPlot(participantData$understandTime, "Average Participant Understand Time", "Minutes", "analysis/RQ2_understandTime.pdf")
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

	svnGrades <- multiplyData(svnGrades, 6)
	gitGrades <- multiplyData(gitGrades, 6)

	print(t.test(svnGrades, gitGrades, paired=TRUE))

	#----------------------------------

	grades <- multiplyDataFrame(grades, 6)

	pdf(file='analysis/RQ4_AllGrades.pdf', onefile=TRUE, family='Helvetica', pointsize=12)
	boxplot(grades$normalizedGrade, xlab="Combined Tasks", ylab="Grades")
	dev.off()

	pdf(file='analysis/RQ4_AllGrades.pdf', onefile=TRUE, family='Helvetica', pointsize=12)

	T01Grades <- grades[grades$taskID == "T01", ]$normalizedGrade
	T02Grades <- grades[grades$taskID == "T02", ]$normalizedGrade
	T03Grades <- grades[grades$taskID == "T03", ]$normalizedGrade
	T04Grades <- grades[grades$taskID == "T04", ]$normalizedGrade
	T05Grades <- grades[grades$taskID == "T05", ]$normalizedGrade
	T06Grades <- grades[grades$taskID == "T06", ]$normalizedGrade

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