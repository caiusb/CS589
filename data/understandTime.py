from __future__ import division
import os
import json
import numpy
import matplotlib.pyplot as plt

outputFile = open(os.path.join("analysis", "results.csv"), "a")

UNDERSTAND_THRESHOLD = 1350

def getImmediateSubdirs(dir):
	 return [name for name in os.listdir(dir)
            if os.path.isdir(os.path.join(dir, name))]

def getJsonForFile(aFilePath):
	return json.loads(open(aFilePath).read())

def getJSONTasksForParticipant(participantDir):
	files = [os.path.join(participantDir, participantFile) for participantFile in os.listdir(participantDir)
			if "events" in participantFile]

	return [getJsonForFile(eventFile) for eventFile in files]


def getTypeIntervalsForTask(taskJSON):
	typeEvents = [event for event in taskJSON
					if event["event"] == "type"]

	timestamps = [event["timeMillis"] for event in typeEvents]

	return numpy.diff(timestamps)

def doHistogram(numbers, filePath):
	plt.hist(numbers, bins=40)

	plt.savefig(filePath + ".jpeg")

	plt.close()

def getStartTime(taskJSON):
	startEvent = [event for event in taskJSON
				  if event["event"] == "taskStart"]

	assert len(startEvent) == 1

	return startEvent[0]["timeMillis"]

def getEndTime(taskJSON):
	endEvent = [event for event in taskJSON
				  if event["event"] == "taskEnd"]

	assert len(endEvent) == 1

	return endEvent[0]["timeMillis"]

def write(participant, taskJSON, totalTime, typingTime, understandTime):
	taskID = taskJSON[0]["taskID"]
	commitOrigin = taskJSON[0]["commitOrigin"]

	outputFile.write(participant + "," + taskID + "," + commitOrigin + "," + str(totalTime) + "," + str(typingTime) + "," + str(understandTime) + "\n")
	

def getTimes(participant, task):
		typeIntervals = getTypeIntervalsForTask(task)

		if len(typeIntervals) == 0:
			print(participant + ":" + task[0]["taskID"] + " has zero intervals")

		shortTypeIntervals = [interval for interval in typeIntervals
								if interval <= UNDERSTAND_THRESHOLD]

		typingTime = numpy.sum(shortTypeIntervals)
		totalTime = getEndTime(task) - getStartTime(task)

		understandTime = totalTime - typingTime

		return (totalTime, typingTime, understandTime)



def collapseTimeForParticipant(participant):
	tasks = getJSONTasksForParticipant(participant)

	for task in tasks:
		taskID = task[0]["taskID"]
		
		if taskID == "demo" or taskID == "practice":
			continue

		(totalTime, typingTime, understandTime) = getTimes(participant, task)

		write(participant, task, totalTime, typingTime, understandTime)

outputFile.write("participant,taskID,commitOrigin,totalTime,typingTime,understandTime\n")

collapseTimeForParticipant("P1")
collapseTimeForParticipant("P2")
collapseTimeForParticipant("P3")
collapseTimeForParticipant("P4")
collapseTimeForParticipant("P5")

outputFile.close()

