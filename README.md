# NEUCoursePlanner
Run Main.main()

Work for 
"{
 "courses": [
 {
 "subject": "CS",
 "classId": 2500,
 "prereqs": {
 "type": "and",
 "values": []
 }
 },
 {
 "subject": "CS",
 "classId": 1800,
 "prereqs": {
 "type": "and",
 "values": []
 }
 },
 {
 "subject": "CS",
 "classId": 3500,
 "prereqs": {
 "type": "or",
 "values": [
 {
 "subject": "CS",
 "classId": 2510
 },
 {
 "subject": "EECE",
 "classId": 2560
 }
 ]
 }
 },
 {
 "subject": "CS",
 "classId": 2510,
 "prereqs": {
 "type": "and",
 "values": [
 {
 "subject": "CS",
 "classId": 2500
 }
 ]
 }
 }
 ]
 }"
 
 Has issues converting to JSON.
 The map, PLan, is sorted per criteria.