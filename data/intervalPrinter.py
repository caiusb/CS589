import os
import json
import numpy
import matplotlib.pyplot as plt

outputRoot = os.path.join("analysis", "typeIntervals")

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

def displayForParticipant(participant):
	percentiles = []

	tasks = getJSONTasksForParticipant(participant)

	for task in tasks:
		typeIntervals = getTypeIntervalsForTask(task)

		#print participant
		#print task[0]["taskID"]
		#print typeIntervals

		if len(typeIntervals) == 0:
			continue

		fileName = os.path.join(outputRoot, participant + "_" + task[0]["taskID"])

		doHistogram(typeIntervals, fileName)

		percentiles.append(numpy.percentile(typeIntervals, 90))

	print numpy.mean(percentiles)

displayForParticipant("P1")
displayForParticipant("P2")
displayForParticipant("P3")
displayForParticipant("P4")
displayForParticipant("P5")



