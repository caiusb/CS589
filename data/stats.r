multiplyData <- function(vector, multiplicationFactor){
	return(rep(vector, multiplicationFactor))
}

originalData <- read.csv("analysis/results.csv", header=TRUE)
survey <- read.csv("survey.csv", header=TRUE)












participants <- unique(originalData$participant)

svn <- vector()
git <- vector()

for (participant in participants){
	pData <- originalData[originalData$participant == participant, ]

	svnData <- pData[pData$commitOrigin == "SVN",]
	gitData <- pData[pData$commitOrigin == "Git",]

	svn <<- c(svn, mean(svnData$understandTime))
	git <<- c(git, mean(gitData$understandTime))
}

svn <- multiplyData(svn, 6)
git <- multiplyData(git, 6)

t.test(svn, git, paired=TRUE)