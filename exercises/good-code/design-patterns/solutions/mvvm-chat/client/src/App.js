import React from 'react';
import openSocket from 'socket.io-client';
import './App.css';

const socket = openSocket('http://localhost:8000')

function App() {
  return (
    <Chat />
  )
}

class Chat extends React.Component {
  constructor(props) {
    super(props)

    this.state = {
      // current messages in chat
      messages: [],
      // text input watcher for username
      currentUsername: "",
      // text input watcher for textarea
      currentInput: ""
    }

    // subscribe to socket "push_message" events and update state
    socket.on('push_message', (newMsg) => this.setState({
      messages: [...this.state.messages, newMsg]
    }))

    // bind this context to methods
    this.onTextAreaChange = this.onTextAreaChange.bind(this);
    this.onUsernameChange = this.onUsernameChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  // handler for username input changes
  // see https://reactjs.org/docs/forms.html
  onUsernameChange(event) {
    this.setState({
      currentUsername: event.target.value
    })
  }

  // handler for textarea changes
  // see https://reactjs.org/docs/forms.html
  onTextAreaChange(event) {
    this.setState({
      currentInput: event.target.value
    })
  }

  // handler for submit button
  onSubmit(event) {
    // send message to server
    socket.emit('send_message',
      this.state.currentUsername,
      this.state.currentInput)
    // reset textarea
    this.setState({
      currentInput: ""
    })
    event.preventDefault()
  }

  render() {
    return (
      <div className="App">
        <div className="msg-list">
          {
            // display message list from state
            this.state.messages.map((m, i) =>
              <div className="message" key={i}>
                <p className="author">{m.author}</p>
                <p className="date">{m.date}</p>
                <p className="text">{m.text}</p>
              </div>
            )
          }
        </div>
        <form onSubmit={this.onSubmit}>
          <input type="text"
            placeholder="Username"
            value={this.state.currentUsername}
            onChange={this.onUsernameChange} />
          <textarea
            placeholder="Your message"
            value={this.state.currentInput}
            onChange={this.onTextAreaChange} />
          <input type="submit" value="Send" />
        </form>
      </div>
    )
  }
}

export default App;
