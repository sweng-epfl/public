# Observer Design Pattern

## Question 1
Which of the following examples are likely to implement the Observer Design Pattern or the callback/listener? Select all that apply.
- [x] Push notifications
- [x] android.widget.TextView and android.text.TextWatcher
- [ ] java.util.concurrent.ThreadPoolExecutor.execute(java.lang.Runnable command)
- [x] Collaborative Office-like tools

**Explanation**
Push notifications are sent when a change in the state of an app is relevant to its clients. Clients subscribe to the changes they are interested in.
One can attach multiple TextWatchers to the same TextView, and they are all called whenever the text changes.
java.util.concurrent.ThreadPoolExecutor.execute is used to execute the Runnable command piece of code in the future, not to react to some events.
Collaborative Office-like tools can implement the Observer design pattern to keep a document in sync across multiple devices. The devices can subscribe to a central repository that sends events at every change of the document state.
