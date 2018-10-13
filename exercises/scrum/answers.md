## Scrum-related questions

1. Who is *not* a part of the Scrum Team?
- [x] Project Manager
- [ ] Scrum Master
- [ ] Product Owner
- [ ] Development Team

// Project Manager - there is no such role in Scrum. The Scrum Owner facilitates the team in the Scrum process 
but does not manage the team. 

2. Which of the following events occur in Scrum?
- [x] Sprint Planning
- [x] Sprint Retrospective
- [x] Sprint Review
- [x] Daily Scrum


3. What is the recommended range of the team size of a Development Team (not counting the Scrum Master and the Product Owner 
unless they contribute to the delivery of the Increment) ?
- [ ] 2 to 4
- [ ] 5 to 6
- [x] 3 to 9
- [ ] 6 to 12

// 3 to 9. Less than 3 means there won't be much work done during a Sprint. More than 9 increases the communication and coordination cost a lot. See the [Scrum guide](https://www.scrumguides.org/scrum-guide.html#team-dev) for more details.

4. What is the main responsibility of the Product Owner?
- [x] Manage the Product Backlog
- [ ] Assign tasks to the Development Team
- [ ] Explain the Product Backlog Items to the customers and the Development Teams
- [ ] Make demos for the customers

// Manage the Product Backlog that represents the value the product brings to customers.

5. Can another member of the Scrum Team change the priority of a Product Backlog besides the Product Owner?
- [ ] Yes
- [x] No

6. What is an appropriate item in the Product Backlog?
- [x] As a registered user, I can click on the settings button from the navigation pane so I can access the settings screen from everywhere in the app.
- [ ] Login
- [ ] [Bug] The landing page doesn't render well, if the language of my device is not English.
- [x] [Bug] When I click on the home button, the app doesn't redirect me there (tested from multiple locations in the app).

// the second one doesn't describe what the login experience should be 
the bug is not specified properly "doesn't render well" is not concrete. A bug Item has to include details on what is wrong 
with the user experience

7. Who can move an item from the Product Backlog to the Sprint Backlog?
- [ ] Product Owner
- [ ] Scrum Master
- [x] Development Team
- [ ] Customer

// Only the Development Team has the right to move items to the Sprint Backlog. The Product Owner and the Scrum Master can do that, if they are a part of the Development Team, or if the item is directly dependent on them.

8. When can an item be selected for the Sprint Backlog?
- [ ] When the Development Team thinks an item in the Product Backlog is clear.
- [x] When the Development Team estimated they can do it within the allocated time for the Sprint.
- [ ] When the Scrum Master authorizes the Development Team to move an item.
- [ ] When the Product Owner asks for an item to be done during the Sprint.

// The Development Team is the only one who has the right to move items to the Sprint Backlog. However, they have a time 
budget for the Sprint and can take a task only if a task is also defined well and estimated to fit into that time budget.

9. Which of the below are acceptable durations of a Sprint?
- [x] 1 week
- [x] 2 weeks
- [x] 4 weeks
- [ ] 5 weeks

// The Sprint should be less than 1 month. More than 1 month means less certainty about what to be build because there is no Increment. See the [Scrum guide](https://www.scrumguides.org/scrum-guide.html#events-sprint) for more details.

10. What is the role of the Scrum Master?
- [x] To serve the Product Owner by helping them communicate with the Development Team
- [x] To serve the Development Team by coaching them in self-organization and flexibility
- [x] To serve the Development Team by removing impediments.
- [ ] To serve the Organization by delivering high-value products.

// The Scrum Master doesn't deliver high-value products themselves. Their role is to help everyone involved 
in the Scrum process understand the Scrum theory and practices.

11. How long should the Daily Scrum ("standup meeting") last?
- [x] <=15min
- [ ] 1h
- [ ] The Daily Scrum can take as much time as required to explain all the work the Development Team has done but less than 30min.
- [ ] The Daily Scrum is not time-boxed.

// The Daily Scrum is a meeting of not more than 15 min. See the [Scrum guide](https://www.scrumguides.org/scrum-guide.html#events-daily) for more details.

12. What kind of statements should every member of the Development Team make during the Daily Scrum?
- [x] Yesterday, I was blocked because of <<this>> or Yesterday I didn't have any blockers.
- [ ] Yesterday, I worked with Alice.
- [x] Yesterday, I did <<this>>.
- [x] Today I will work on <<this>>.
  
// The format of communicating during the Daily Scrum is " Yesterday, I did this. Yesterday, I had a blocker/didn't have a blocker. Today, I will work on this." All other details should be left for after the meeting. 
  
13. Who should speak during the Daily Scrum?
- [x] Development Team
- [ ] Product Owner
- [ ] Scrum Master
- [ ] Customer
 
// The Daily Scrum meeting is an internal meeting for the Development Team to communicate how they are progressing on their tasks. The Scrum Master ensures that the format is observed but if they are not a part of the Development Team they don't have to participate in the Daily Scrum.

14. What is the goal/What are the goals of the Sprint Review?
- [x] Demo the Increment to receive feedback.
- [x] Adjust the Product Backlog
- [x] Make a presentation about what needs to be done and what has been done.
- [ ] Discuss what went wrong and what went well

// The Sprint Review has to show the Increment to stakeholders and the Product Owner so that they can communicate their expectations. The outcome is a revised Product Backlog that is ready for the Spring Planning meeting. The Backlog can be adjusted further by the Product Owner, if necessary.

15. Who takes part in the Sprint Review?
- [x] Development Team
- [x] Product Owner
- [x] Invited Stakeholders
- [x] Scrum Master

16. What is the maximum duration of the Sprint Review?
- [ ] 1h
- [ ] 30min
- [ ] 1d
- [x] 4h

// The maximum is 4h. The shorter the Sprint, the shorter the Sprint Review

17. Pick the correct descriptions of the Sprint Retrospective.
- [x] The Sprint Retrospective is for the Scrum Team to inspect what worked and what didn't during the Sprint.
- [x] The goal of the Sprint Retrospective is to identify improvements that the Scrum Team can implement in the next Sprint.
- [x] The Scrum Team can update its definition of Done during the Sprint Retrospective.
- [ ] The Sprint Retrospective's results have to communicated with the customers.

## Scenarios

18. If you encounter a bug unrelated to what you’re doing as a task during the Sprint, what do you need to do?
- [ ] Fix the bug immediately.
- [ ] Log the bug and communicate to the Product Owner what you found.
- [ ] Work on the bug after you finish your task.
- [ ] Ask your teammates who’s available to fix the bug

// Log and communicate. The Product Owner has the right to prioritize the Product Backlog items. Maybe the bug is not severe enough to take your time

19. If a team member starts talking about implementation details that prevented them from doing their tasks during the Daily Scrum, what should be done?
- [ ] Ask them to explain further and try to resolve the issue on spot
- [x] Ask them to keep the implementation details for later and communicate after the meeting
- [ ] Ignore their comment and 
- [ ] Everyone should explain their implementation problems during those meetings.

// the Daily Scrum is all about short communication of what each member of the Development Team has achieved and what 
they will do next. Any blockers should be reported as well but all implementation details should be kept for later and communicated only with members who express familiarity or wish to help with the issue. 


## Other software development processes 

20. Which steps characterize the Waterfall development process?
- [ ] Planning, Implementation, Testing, Retrospective
- [ ] System Requirements, Software Requirements, Analysis, Prototype
- [x] Analysis, Program Design, Coding, Testing
- [ ] Analysis, Program Design, Coding, Testing, Demoing 

// All the steps are: System Requirements, Software Requirements, Analysis, Program Design, Coding, Testing, Operations

21. What would be a good use case for Waterfall development?
- [x] The product is mission-critical and requires a high level of specification before any coding.
- [ ] The team doesn't know what to implement, so they work on requirements until they understand what the product they want to build.
- [ ] An app for a class
- [ ] Your hobby project

// in the case of a critical system, for example, a car’s anti-lock braking system (ABS), you might consider a Waterfall 
process to analyze the requirements and define the behavior of the system before starting to code and release potentially
faulty software

22. Which software development process is a sequence of "mini-Waterfalls"?
- [ ] Prototyping
- [ ] Scrum
- [ ] Spiral
- [x] Incremental Waterfall

## Supplemental questions

These were not directly covered in the lecture, but try to use web search to answer them.

23. What are the weaknesses of the incremental software development process?
- [x] The design of the interfaces between components is tricky
- [x] Customers might receive the most important functionality only at the end of the development
- [x] There is no global overview of the project
- [x] Planning and design of only a part of the system are hard without planning the entire system

24. Which software development process can be described as the exact opposite of Waterfall?
- [x] Prototyping
- [ ] Scrum
- [ ] Spiral
- [ ] Incremental
