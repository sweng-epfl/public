const io = require('socket.io')(8000)

function Message(author, text) {
    this.author = author
    this.date = new Date()
    this.text = text
}

io.on('connection', (socket) => {
    io.emit('push_message', new Message('server', 'New client connected'))

    socket.on('send_message', (username, message) => {
        io.emit('push_message', new Message(username, message))
    })

    socket.on('disconnect', () => {
        io.emit('push_message', new Message('server', 'Client disconnected'))
    })
})