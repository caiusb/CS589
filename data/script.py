import os
import json
import numpy

def getImmediateSubdirs(dir):
	 return [name for name in os.listdir(dir)
            if os.path.isdir(os.path.join(dir, name))]

def getJsonForFile(aFilePath):
	return json.loads(open(aFilePath).read())

def getJSONTasksForParticipant(participantDir):
	files = [os.path.join(participantDir, name) for name in os.listdir(participantDir)
			if "events" in name]

	print [getJsonForFile(eventFile) for eventFile in files]


def getTypeIntervalsForTask(taskJSON):
	typeEvents = [event for event in taskJSON
					if event["event"] == "type"]

	timestamps = [event["timeMillis"] for event in typeEvents]

	return numpy.diff(timestamps)

j = getJsonForFile("P1/1393006618765_T03_events")
print getTypeIntervalsForTask(j)
