## Drones

[[_TOC_]]

---

:scroll: **START**


### Introduction

There is a major new technology that is destined to be a disruptive force in the field of transportation: **the drone**. Just as the mobile phone allowed developing countries to leapfrog older technologies for personal communication, the drone has the potential to leapfrog traditional transportation infrastructure.

Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult access.

---

### Task description

We have a fleet of **10 drones**. A drone is capable of carrying devices, other than cameras, and capable of delivering small loads. For our use case **the load is medications**.

A **Drone** has:
- serial number (100 characters max);
- model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
- weight limit (500gr max);
- battery capacity (percentage);
- state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).

Each **Medication** has: 
- name (allowed only letters, numbers, ‘-‘, ‘_’);
- weight;
- code (allowed only upper case letters, underscore and numbers);
- image (picture of the medication case).

Develop a service via REST API that allows clients to communicate with the drones (i.e. **dispatch controller**). The specific communicaiton with the drone is outside the scope of this task. 

The service should allow:
- registering a drone;
- loading a drone with medication items;
- checking loaded medication items for a given drone; 
- checking available drones for loading;
- check drone battery level for a given drone;

> Feel free to make assumptions for the design approach. 

---

### Requirements

While implementing your solution **please take care of the following requirements**: 

#### Functional requirements

- There is no need for UI;
- Prevent the drone from being loaded with more weight that it can carry;
- Prevent the drone from being in LOADING state if the battery level is **below 25%**;
- Introduce a periodic task to check drones battery levels and create history/audit event log for this.

---

#### Non-functional requirements

- Input/output data must be in JSON format;
- Your project must be buildable and runnable;
- Your project must have a README file with build/run/test instructions (use DB that can be run locally, e.g. in-memory, via container);
- Required data must be preloaded in the database.
- JUnit tests are optional but advisable (if you have time);
- Advice: Show us how you work through your commit history.

---

### Launch application instruction:

1. Need to install before launch:
- Docker
- Docker-compose
- Git
- Maven
- Bash (optional)
2. Create project directory and enter into it
####
3. Execute command:
- git clone https://oauth:glpat-ypd13WZnEhZzw3kWredc@gitlab.com/musala-coding-tasks-solutions/spartak-smirnov.git
####
4. Enter into spartak-smirnov directory
####
5. Execute command:
- git checkout dev
####
6. Execute command:
- bash start.sh dev DB_USERNAME=drone DB_PASSWORD=drone DB_NAME=postgres DB_PORT=5439 APP_PORT=8081
####
7. There will be created two docker containers: drone-db and drone-app, which will be available on port 8081 (make sure that it is free, or mark another port in start line)
####
8. Go to browser and enter go to:
- http://localhost:8081/swagger-ui/#/
   for check available enpoints
####
9. The log file, called drone_docker.log, will be created in current directory

###Current properties for application:
1. drone.max.count=10 (limit of drones in database)
####
2. drone.battery.capacity.limit=25 (limit for loading drone by battery capacity)
####
3. drone.quartz.cron=0 0/1 * ? * * * (every 1 minute the scheduler will check the drone battery capacity, and write info to logFile)

All properties you can change in application-docker.properties file, which is located:
- ../src/main/resources



:scroll: **END** 
