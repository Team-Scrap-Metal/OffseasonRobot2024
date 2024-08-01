# OffseasonRobot2024
This code base creates an FRC robot for the Crescendo game year. The first robot of Team Scrap Metal, FRC Team 10256

## Code Etiquette
  Comments on EVERYTHING (Commands, Constants, etc)
  Organize files properly in corresponding folders
  Follow naming conventions (reference section below)
  Create Issues on Github for EVERY branch
  - add a description of what the branch should accomplish
  - 'Assignees': used to indicate who is working on what
  - 'Labels': used to organize issues, add as necessary
  - 'Development': link the branch to the issue

   Beta Numbers
 - first number = repository #
 - second number = # of pushes to Dev
 - third number = issue #
 - fourth number = # of commits
 - fifth number = functionality (1 = working as intended, 0 = works but not as intended, -1 = crashes, -2 = doesn't build)

For example, the first repository that has 2 Pushes to Dev, on the branch associated with issue 7, with 18 commits, which works but not as intended should look like: "1.2.7.18.0"

## Branch Organization

main:

- Competition-ready code
- ONLY receives pushes from Dev
- Do *NOT* make branches off of main

Dev:

- Used for full systems tests
- Only push to main after tested and cleaned
  - No hardcoded values, add them to Constants
  - Explanatory comments
  - Code is fully functional
- Make ALL branches off of *this* branch!!

feat#[Issue#]-[name]:

- Used for new features on the robot
- Used for code changes
  - Changes can be as small or big as necessary
    - Ex: feat#68-mechanism-button-bindings
  - Only contains what the branch is named
    - Ex: No climber code in a shooter branch
- Branch name is camel cased
  - Ex: feat#50-SwerveDrive
-  Push to Dev when code is tested and clean (see "Dev" for "tested and clean" standards)

bugfix#[Issue#]-[name]:

- Used for bugfixing of a feature from Dev
- Branch name is camel cased
  - Ex: feat#50-SwerveDrive

chore#[Issue#]-[name]:

- Used for cleaning code from Dev
- Branch name is camel cased
  - Ex: feat#50-SwerveDrive

## Naming Conventions
- Folder and file names
  - CapitalizeEveryWordWithoutSpaces
  - *unless it's a subsystem in Subsystems, which are all lowercase (ex: "arm", "utbintake")
- Constants
  - ALL_CAPS_WITH_UNDERSCORES
- Functions and variables
  - camelCase (lowercase first word, capitalize first letter of all subsequent words, no spaces)

## Folder/File Organization
- **Commands**
  - **Teleop Commands**
    - Commands used for Teleop
    - Ex: scoring AMP, intaking NOTE from SOURCE, run all Intakes, etc.
  - **Zero Commands**
    - Resets Actuator, Arm, Wrist, Shooter, and Feeder to their "zero" positions
- **Subsystems**
  - Ex: Arm, Drive, Vision, etc.
  - [subsystem]
    - [Subsystem.java]: main class for subsystem, runs commands depending on passed in IO (sim or real), extends SubsystemBase
    - [Subsystem]Constants.java: subsystem-specific constants
    - [Subsystem]IO.java: all expected outputs and standardized functions for all versions of the subsytem
    - [Subsystem]IO[Motor]Sim.java: simulation code for subsystem, implements [Subsystem]IO
    - [Subsystem]IO[Motor].java: real code for subsystem, implements [Subsystem]IO
- **Utils**
  - Other Utitlties that aren't subsystem specific and are needed for all subsystems
  - Ex: PoseEstimator, PathPlanner, ect
- **Other (not in a folder)**
  - Constants
    - Constants shared by all subsystems, related to the robot
    - Ex: controller port numbers, battery voltage, alliance (red/blue), etc.
  - Main
    - Runs Robot
  - Robot
    - Initializes robot, starts logging data, runs periodic/auto/simulation modes
  - Robot Container
    - Creates all subsystems, runs commands, and logs data



## Useful Git Bash Commands
- git add .
  - stages all code to prepare for commit and push
- git commit -m "[Insert Message Here]"
  - saves code locally with a msg of what was done to the code
- git push
  - pushes code to the cloud
- git fetch
  - tells laptop their are new changes
- git pull
  - puts new changes on laptop
  - git pull does git fetch
- git pull origin Dev
  - pulls changes from Dev
- git merge (origin) Dev
  - pulls ALL changes from Dev (no rebases)
- git checkout [branch name]
  - Changes your branch
- git checkout -b [branch name]
  - Creates new branch off of current branch
- git branch
  - Shows all branches that have been pulled on laptop
- git branch --all
  - Shows all branches since last fetch
