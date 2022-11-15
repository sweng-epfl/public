# Lectures

Lectures are structured according to the following ["LOAFS"](https://tube.switch.ch/videos/af20b0a8) format:
- **Lead-in**, to get students interested and activate any previous knowledge they have
- **Objectives**, to clearly state what students should be able to achieve after the lecture
- **Active processing of information**, with in-lecture exercises for students to get involved instead of passively listening
- **Formative assessment**, with in-lecture exercises and solutions for students to tell if they have understood the tasks
- **Summary**, to recap what the objectives were and how the lecture covered them

Designing a lecture starts with the objectives: what exactly will students learn?
The [Anderson and Krathwohl taxonomy](https://www.depauw.edu/files/resources/krathwohl.pdf) is a good basis for objectives.

After establishing objectives, the rest of the lecture follows: what exercises will allow students to know if they have achieved the objectives?
What material must be explained to achieve these objectives? This is called "backwards course design".

The contents of lectures should be _actionable_: students should understand what they can do, when they should do it, and why.
This is particularly important for software engineering because real-world examples are way too large for an exercise,
thus students must understand how the material taught in class applies on real projects.


## Slide format

Font:
- Titles: 72pt Calibri
- Headings: 60pt Calibri
- Text: 40pt Calibri Light
- Code: 36pt Consolas, which is the same actual size as the text since Consolas is a "large" font
- Page numbers: 12pt Calibri Light
- Footnotes for sources: 10pt Calibri Light

Colors, chosen to be distinguishable even with common kinds of colorblindness:
- Red #A00000
- Green #00C040
- Blue #0040A0
- Yellow #F0D000

Emphasis:
- Underline key points
- Highlight in yellow #FFFF00 key changes from a previous slide

Code, using the 2nd darkest variations from PowerPoint's color palette given the colors above:
- Blue keywords
- Yellow class names
- Red literals
- Green comments
- 2 spaces for indents

Images:
- Use vector images if possible, especially for icons
- Use images that do not require crediting if at all possible, otherwise add a source on the slide
- For lecture notes, keep in mind they might be viewed in light or dark mode, and thus should look good on both white and black backgrounds

Shapes:
- Use either an outline or a fill, but not both unless there's a good reason
- Do not use emojis, because they look different on different OSes and OS versions

Sections:
- Use one PowerPoint section per logical section
- Start each section with a "section title" slide
- Sections should be focused on a specific objective, usually in the form of a question
- If a section can be summarized as "here is some stuff to know", this means it lacks a clear objective and should be rephrased

Overall:
- For in-lecture exercises, include slide notes with their expected duration
- Minimize the amount of text and especially of text-only slides
- Avoid slide transitions unless there is a good reason
- Slides start with a title slide, lead-in slides, then an "Objectives" slide
- Slides end with a "Summary" slide


## Lecture note format

Important concepts should be `_emphasized like this_`.

For images, use HTML tags to specify a width of 50%, otherwise they are too large; see existing lecture notes for examples.

For in-lecture exercises, use `<details>` blocks with a `<p>` inside so that Markdown works; see existing lecture notes for examples.
