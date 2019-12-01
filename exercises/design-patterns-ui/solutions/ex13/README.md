# Real-time chat

To run this real-time chat, first run the server with (you will need NodeJS installed):

```
cd server
npm install
npm start
```

Leave it running in your terminal. Open a new terminal here and run the client:

```
cd client
npm install
npm start
```

and also leave it running. It should open your web browser to the application page, you can start using the chat.

To test the app with other people, connect all your machines on the same local network (for instance by sharing your phone connection through wifi). Run a server on a machine and get its local IP address (for instance with `ifconfig` / `ipconfig`).

On all client machines, change the server address call in `client/src/App.js`:

```js
const socket = openSocket('http://localhost:8000')
```

Replace `localhost` with the previous IP (that will look something like `192.168.1.41`).

You can now run all the clients and chat together.