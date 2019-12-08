# Solutions

## Managing a software project

Why do large software projects tend to miss their planned budgets and deadlines more often than small projects?

- [ ] There usually aren't enough people working on the project
- [ ] There are not enough managers in the project
- [x] The management task is more difficult and prone to wrong decisions as the project gets bigger 
- [ ] Teams fail to account for the fact that debugging takes 50% of the development cycle

> As projects get bigger, managing the development team becomes more complex, and this causes it to miss deadlines and implement the wrong functionality.

Which tools can help you better manage a software project?

- [x] The GitHub issue tracker 
- [x] A code review tool, even as basic as GitHub's comment feature 
- [x] The Emma code coverage measurement tool 
- [x] Git commit messages 
- [x] CheckStyle 

> All of these are useful. Use the issue tracker for making a plan (Video 2: 2:20), for doing code reviews (Video 3, 0:30), measuring testing efficiency (Video 4, 1:50), documenting your code (Video 3, 4:30), and adopting a coding standard (Video 3, 3:30)

## Waterfall

For which of the following scenarios is the Waterfall model most appropriate?

- [ ] While working with the Junior Entreprise EPFL, a talented web developer has to work on a new project with a client who wants a website for his company. This is the client's first website and they are not familiar with web technology. However, the client is enthusiastic and has lots of different ideas about how to organize the website.
- [x] The Volare Space Robotics Challenge was a contest organized by the ASI (Agenzia Spaziale Italiana) and ESA (European Space Agency). The goal was to make high-school students design robots that unload cargo from ESA's Automated Transfer Vehicle, while avoiding objects. 
- [ ] An EPFL graduate has been employed by a start-up as a software engineer, to work with a team of 10 people (electrical engineers, computer scientists and mechanical engineers). The start-up is developing a new robot that should revolutionize industrial manufacturing. The product is based on an entire suite of technical innovations in AI, motor control, computer vision and materials.
- [ ] The SBB/CFF wants an external software company to develop a brand new app for smartphones. The app should replace most of the services offered by SBB customer service assistants at rail stations. All initial requirements for the app have been written down. However, there are some features that are not covered by the current requirements, but SBB might like to add to a future version of the application.

> An automated robot that operates in space is a very complex product. The requirements of the project are proposed early on and are stable. Since space exploration is governed by many laws, the project is subjected to different stages of approval at each step. The students are inexperienced. All these properties make the Waterfall model suitable for the project.
      The waterfall model is not suitable for the website project, since the requirements are not clear upfront. The client does not know the possibilities offered by web technologies, therefore cannot produce a detailed statement of what they want.
      The waterfall model is not suitable for the start-up project, because the waterfall model does not adapt well to changes; the start-up works with new, immature technologies that are unpredictable and can lead to changes in the requirements.
      The waterfall model is not suitable for the SBB app project, because the requirements can change later on.

## Prototyping

For which of the following scenarios is the Prototyping model most appropriate?

- [x] While working with the Junior Entreprise EPFL, a talented web developer has to work on a new project with a client who wants a website for his company. This is the client's first website and they are not familiar with web technology. However, the client is enthusiastic and has lots of different ideas about how to organize the website. 
- [ ] The Volare Space Robotics Challenge was a contest organized by the ASI (Agenzia Spaziale Italiana) and ESA (European Space Agency). The goal was to make high-school students design robots that unload cargo from ESA's Automated Transfer Vehicle, while avoiding objects.
- [ ] An EPFL graduate has been employed by a start-up as a software engineer, to work with a team of 10 people (electrical engineers, computer scientists and mechanical engineers). The start-up is developing a new robot that should revolutionize industrial manufacturing. The product is based on an entire suite of technical innovations in AI, motor control, computer vision and materials.
- [ ] The SBB/CFF wants an external software company to develop a brand new app for smartphones. The app should replace most of the services offered by SBB customer service assistants at rail stations. All initial requirements for the app have been written down. However, there are some features that are not covered by the current requirements, but SBB might like to add to a future version of the application.

> The Prototyping model for the website project. The client is enthusiastic about web technology, but does not know how different alternatives would work for their website. The developers can use the prototype model to quickly present new UI designs, based on different technologies. The backend of the design does not need to be fully implemented; stubs can be use to provide mock functionality. Once the client chooses the web technology to use, the developers can fully implement the website.
      The automated robot project is not suitable for prototyping. The product is expensive to build; throwaway mockups would be an expensive waste of money. Prototypes are also difficult to evaluate, as they are meant for a very harsh environment that is hard to simulate on Earth.
      The start-up project is not suitable, since there is not yet a client demanding the product. Like for the space robot, the product of the start-up is also difficult and expensive to prototype, while function-less mockups have little value.
      The SBB app project is not suitable, because the requirements are known by the client. The client (SBB) is knowledgeable in the requested functionality, since the app will emulate services already provided by human customer service assistants. The UI of the app can be prototyped, but it is likely that the most important aspect of the application is the functionality, rather than the design.

## Incremental

For which of the following scenarios is the Incremental model most appropriate?

- [ ] While working with the Junior Entreprise EPFL, a talented web developer has to work on a new project with a client who wants a website for his company. This is the client's first website and they are not familiar with web technology. However, the client is enthusiastic and has lots of different ideas about how to organize the website.
- [ ] The Volare Space Robotics Challenge was a contest organized by the ASI (Agenzia Spaziale Italiana) and ESA (European Space Agency). The goal was to make high-school students design robots that unload cargo from ESA's Automated Transfer Vehicle, while avoiding objects.
- [ ] An EPFL graduate has been employed by a start-up as a software engineer, to work with a team of 10 people (electrical engineers, computer scientists and mechanical engineers). The start-up is developing a new robot that should revolutionize industrial manufacturing. The product is based on an entire suite of technical innovations in AI, motor control, computer vision and materials.
- [x] The SBB/CFF wants an external software company to develop a brand new app for smartphones. The app should replace most of the services offered by SBB customer service assistants at rail stations. All initial requirements for the app have been written down; the requirements are split into "must-have" features, which are strictly necessary to implement, and "nice-to-have" features, which are desirable, but not imperative. 

> The incremental model is most suitable for the SBB application. The different features of the app can be developed in parallel, with different priorities (higher priority for the must-have features). Thus, the must-have features can be released early, while the nice-to-have features can be delayed for a future release.
      The website project is not suitable for the incremental model, as the requirements are very unclear. Unlike the prototype model, where the intermediate prototypes are meant to be discarded, in the incremental model each mini-project follows the waterfall model. If each alternative web technology would be implemented as a mini-project, the developer would need to gather detailed requirements from the client for each alternative --- this is a bad idea, since the client is not familiar with the technologies.
      The incremental model is not suitable for the space robot project, because the project has very well-defined requirements from the beginning. The requirements cannot be changed, as the robot needs to interact with existing technology. Thus, the space robot project requires thinking of the big picture in advance.
      The start-up is another potential candidate for the incremental model; however, it is not as suitable as the SBB application. The start-up project uses new, immature technologies, which might lead to unexpected situations that require redesign.

## Spiral

For which of the following scenarios is the Spiral model most appropriate?

- [ ] While working with the Junior Entreprise EPFL, a talented web developer has to work on a new project with a client who wants a website for his company. This is the client's first website and they are not familiar with web technology. However, the client is enthusiastic and has lots of different ideas about how to organize the website.
- [ ] The Volare Space Robotics Challenge was a contest organized by the ASI (Agenzia Spaziale Italiana) and ESA (European Space Agency). The goal was to make high-school students design robots that unload cargo from ESA's Automated Transfer Vehicle, while avoiding objects.
- [x] An EPFL graduate has been employed by a start-up as a software engineer, to work with a team of 10 people (electrical engineers, computer scientists and mechanical engineers). The start-up is developing a new robot that should revolutionize industrial manufacturing. The product is based on an entire suite of technical innovations in AI, motor control, computer vision and materials. 
- [ ] The SBB/CFF wants an external software company to develop a brand new app for smartphones. The app should replace most of the services offered by SBB customer service assistants at rail stations. All initial requirements for the app have been written down; the requirements are split into "must-have" features, which are strictly necessary to implement, and "nice-to-have" features, which are desirable, but not imperative.

> The spiral model is most appropriate for the start-up project. The project is complex, using various state-of-the-art technology. This brings an inherent risk --- new technology is untested and error prone. The spiral model enforces periodic evaluation of risks. If certain technology is unfeasible, the team can switch to a different technology, or re-design their product.
      The spiral model is unsuitable for the website project. The client should be able to experiment with different technologies, but it is not necessary to constantly assess risks, because it is time consuming.
      The spiral model is not suitable for the space robot project, because the project has very well-defined requirements from the beginning. All risks are evaluated before the start of the project.
      The SBB app is also appropriate for the spiral model. Developers can assess the risks of adding nice-to-have features to the app. However, if the project has strict deadlines, the spiral model can add too much administrative overhead to the project. Thus, the start-up project is a better match for the spiral model.

## Scrum

If you don't know what the Liskov Substitution Principle is, who do you go talk to?

- [ ] Product owner
- [ ] Scrum master
- [x] Guru (also known as manager)

> The guru is in charge of the technical aspects of a project.

Which of the following should not happen during the Sprint?

- [x] Have a daily scrum meeting with the entire team, and discuss about the progress of the tasks 
- [ ] Go talk to the Scrum Master when having impediments
- [x] Talk with the Product Owner about removing tasks from the Backlog 

> There should be no discussion during the daily scrum meeting.
      The Backlog should not change during the Sprint, it should be discussed during the Sprint Planning Meeting and Sprint Review.

## More about Scrum

Which meeting is held for the sake of the product?

- [ ] daily scrum
- [x] sprint review 
- [ ] retrospective

> 

What is the limit on the duration of the daily scrum meeting?

- [ ] 5 minutes
- [x] 15 minutes 
- [ ] 1 hour

> 

What information provides burnout chart?

- [ ] Adjustements necessary to meet the deadline.
- [x] An estimation of the amount of work left for the sprint. 
- [ ] Customer satisfaction.
- [x] The average team daily performance. 

> 

What is included in every bar in the burnout chart?

- [x] the parts of user stories that remain to be done 
- [ ] the completed features
- [ ] the project manager's daily estimate of the remaining work

> 


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
- [x] 3 to 9
- [ ] 5 to 10
- [ ] 5 to 6

// 3 to 9. Less than 3 means there won't be much work done during a Sprint. More than 9 increases the communication
and coordination cost a lot.

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

9. What are acceptable durations of a Sprint?
- [x] 5 days
- [x] 7 days
- [x] 20 days
- [x] 15 days

// The Sprint should be less than 1 month. More than 1 month means less certainty about what to be build because there is no Increment.

10. What is the role of the Scrum Master?
- [x] To serve the Product Owner by helping them communicate with the Development Team
- [x] To serve the Development Team by coaching them in self-organization and flexibility
- [x] To serve the Development Team by removing impediments.
- [ ] To serve the Organization by delivering high-value products.

// The Scrum Master doesn't deliver high-value products themselves. Their role is to help everyone involved 
in the Scrum process understand the Scrum theory and practices.

11. What is the duration of the Daily Scrum?
- [x] 15min
- [ ] 1h
- [ ] The Daily Scrum can take as much time as required to explain all the work the Development Team has done but less than 30min.
- [ ] The Daily Scrum is not time-boxed.

// The Daily Scrum is a meeting of not more than 15min.

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

20. Which steps characterize are a part of the waterfall development?
- [ ] Planning, Implementation, Testing, Retrospective
- [ ] System Requirements, Software Requirements, Analysis, Prototype
- [x] Analysis, Program Design, Coding, Testing
- [ ] Analysis, Program Design, Coding, Testing, Demoing 

// All the steps are: System Requirements, Software Requirements, Analysis, Program Design, Coding, Testing, Operations


21. What would be a case of using waterfall development?
- [x] The product is mission-critical and requires a high level of specification before any coding.
- [ ] The team doesn't know what to implement, so they work on requirements until they understand what the product they want to build.
- [ ] An app for a class
- [ ] Your hobby project

// in the case of a critical system, for example, a car’s anti-lock braking system (ABS), you might consider a waterfall 
process to analyze the requirements and define the behavior of the system before starting to code and release potentially
faulty software

22. Which software development process can be described as the exact opposite of waterfall?
- [x] Prototyping
- [ ] Scrum
- [ ] Spiral
- [ ] Incremental

// Prototyping is the most extreme case of unstructured process. Its main points are to prototype and show to the customers 
what the system would look like without any of the steps preceding "Coding"

## Supplemental questions

23. What are the weaknesses of the incremental software development process?
- [x] The design of the interfaces between components is tricky
- [x] Customers might receive the most important functionality only at the end of the development
- [x] There is no global overview of the project
- [x] Planning and design of only a part of the system are hard without planning the entire system

24. Which software development process is a sequence of "mini-waterfalls"?
- [ ] Prototyping
- [ ] Scrum
- [ ] Spiral
- [x] Incremental
