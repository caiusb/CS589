data <- read.csv("analysis/results.csv", header=TRUE)

participants <- unique(data$participant)

svn <- vector()
git <- vector()

for (participant in participants){
	pData <- data[data$participant == participant, ]

	svnData <- pData[pData$commitOrigin == "SVN",]
	gitData <- pData[pData$commitOrigin == "Git",]

	svn <<- c(svn, mean(svnData$understandTime))
	git <<- c(git, mean(gitData$understandTime))
}

svn <- rep(svn, 6)
git <- rep(git, 6)

t.test(svn, git, paired=TRUE)